package com.yyl.apicontroller;

import java.io.BufferedReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.easemob.server.example.httpclient.utils.HTTPClientUtils;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.yyl.modal.ApiResult;
import com.yyl.modal.TokenInfo;
import com.yyl.service.OrderService;
import com.yyl.thirdpart.PingPlus;

@RequestMapping("/Api/Order")
@Controller
public class OrderApiController extends BaseApiController {

	@Autowired
	OrderService orderService;
	
	/**
	 * Ping++获取Charge
	 * @param request
	 * @param pId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/CreateCharge",method=RequestMethod.POST)
	public Charge CreateCharge(HttpServletRequest request,int pId)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return null;
		}
		
		Map pInfo = orderService.GetPurchaseInfo(pId);
		if(pInfo == null)
		{
			return null;
		}
		
		double amount = Double.parseDouble(pInfo.get("Price").toString());
		int oid = orderService.CreateOrder(pId, "android", ti.uid);

		PingPlus pp = new PingPlus();
		Charge charge = pp.CreateCharge(String.valueOf(oid),(int)(amount*100));
		
		return charge;
	}
	
	/**
	 * 获取商品列表
	 * @param request
	 * @param pId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/GetPurchaseList",method=RequestMethod.GET)
	public ApiResult GetPurchaseList(HttpServletRequest request)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return null;
		}
				
		ApiResult ar = new ApiResult();

		try 
		{
			List plist = orderService.LoadPurchaseList();
			ar.Data = plist;
			ar.Result = true;
		} 
		catch (Exception ex) {

		}

		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/CreateOrder",method=RequestMethod.POST)
	public ApiResult CreateOrder(HttpServletRequest request,int pid,String src)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return null;
		}
				
		ApiResult ar = new ApiResult();

		try 
		{
			int oid = orderService.CreateOrder(pid, src, ti.uid);
			ar.Data = oid;
			ar.Result = true;
		} 
		catch (Exception ex) {

		}

		return ar;
	}
	
	/*
	验证收据的过程：

	1. 从transaction的transactionReceipt属性中得到收据的数据，并以base64方式编码。
	2. 创建JSON对象，字典格式，单键值对，键名为"receipt-data"， 值为上一步编码后的数据。效果为:
	{
	    "receipt-data"    : "(编码后的数据)"
	}

	3. 发送HTTP POST的请求，将数据发送到App Store，其地址为:
	https://buy.itunes.apple.com/verifyReceipt
	https://sandbox.itunes.apple.com/verifyReceipt

	4. App Store的返回值也是一个JSON格式的对象，包含两个键值对， status和receipt:
	{
	    "status"    : 0,
	    "receipt"    : { … }
	}

	如果status的值为0， 就说明该receipt为有效的。 否则就是无效的。
	 */
	/**
	 * 验证订单
	 * @param request
	 * @param oid
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/VerifyTrade",method=RequestMethod.POST)
	public ApiResult VerifyTrade(HttpServletRequest request,int oid,String data)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return null;
		}
				
		ApiResult ar = new ApiResult();

		try 
		{
			String body = "{\"receipt-data\":\""+data+"\"}";
			ObjectNode jsObj = HTTPClientUtils.sendHTTPRequest(new URL("https://sandbox.itunes.apple.com/verifyReceipt"), null, body, "POST");
			int status = Integer.parseInt(jsObj.get("status").toString());
			if(status == 0)
			{
				orderService.CompleteOrder(oid);
				ar.Result = true;
			}
			else 
			{
				ar.Infomation = "交易验证失败 Code:"+status;
			}
		} 
		catch (Exception ex) {

		}

		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/ChargeSuccess",method=RequestMethod.POST)
	public String ChargeSuccess(HttpServletRequest request) 
	{
		String ret = "";
		
		try
		{
			request.setCharacterEncoding("UTF8");
	        // 获得http body 内容
	        BufferedReader reader = request.getReader();
	        StringBuffer buffer = new StringBuffer();
	        String string;
	        while ((string = reader.readLine()) != null) {
	            buffer.append(string);
	        }
	        reader.close();
	        // 解析异步通知数据
	        Event event = Webhooks.eventParse(buffer.toString());
	        Map data = event.getData();
	        Map object = (Map)data.get("object");
	        boolean paid = Boolean.parseBoolean(object.get("paid").toString());
	        if(paid)
	        {
	        	 String order_no = object.get("order_no").toString();
	        	// int amount = Integer.parseInt(object.get("amount").toString());
	        	 orderService.CompleteOrder(Integer.parseInt(order_no));
	        	 ret = "OK";
	        }
	        else {
				ret = "NoPaid";
			}
		}
		catch(Exception ex)
		{
			ret = ex.toString();
		}
        
		return ret;
	}
	
}
