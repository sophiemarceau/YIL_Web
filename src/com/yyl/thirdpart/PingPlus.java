package com.yyl.thirdpart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;

public class PingPlus 
{
	/**
	 * pingpp 管理平台对应的 API key
	 */
	public static String apiKey = "sk_live_F0OuiRi44YyIZkASB2YNueww";
	/**
	 * pingpp 管理平台对应的应用 ID
	 */
	public static String appId = "app_mDS44CiTOu9Gn14q";
	
	public PingPlus() 
	{
		Pingpp.apiKey = apiKey;
	}
	

	/**
	 * 创建Charge
	 * @return
	 */
	public Charge CreateCharge(String order_num,int amount) 
	{
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", amount);
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "YoueLink");
        chargeMap.put("body", "Body");
        chargeMap.put("order_no", order_num);
        chargeMap.put("channel", "alipay");
        chargeMap.put("client_ip", "127.0.0.1");
        Map<String, String> app = new HashMap<String, String>();
        app.put("id",appId);
        chargeMap.put("app", app);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            //System.out.println(charge);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }
}
