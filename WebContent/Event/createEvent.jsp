<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">

	<div class="accordion" >
          <div class="accordion-group widget-box">
            <div class="accordion-heading">
              <div class="widget-title"> <a id="form_head" href="#collapseGThree" data-toggle="collapse" data-parent="#collapse-group"> <span class="icon"><i class="icon-eye-open"></i></span>
                <h5>创建活动</h5>
                </a> </div>
            </div>
            <div class=" accordion-body" id="collapseGThree">
              <div class="widget-content">
              
              
              
	            <form class="form-horizontal" action="#" method="get">
		            <div class="control-group">
		              <label class="control-label">活动主题 :</label>
		              <div class="controls">
		                <input id="txt_title" class="span11" type="text" placeholder="输入活动主题">
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">活动地点 :</label>
		              <div class="controls">
		                <input id="txt_add" class="span11" type="text" placeholder="输入活动地点">
		              </div>
		            </div>
		            
		             <div class="control-group">
		              <label class="control-label">经度 :</label>
		              <div class="controls">
		                <input id="txt_long" class="span11" type="text" placeholder="">
		              </div>
		            </div>
		            
		             <div class="control-group">
		              <label class="control-label">纬度 :</label>
		              <div class="controls">
		                <input id="txt_la" class="span11" type="text" placeholder="">
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">坐标工具 :</label>
		              <div class="controls">
		                <a href="http://api.map.baidu.com/lbsapi/getpoint/index.html" target="_blank">坐标拾取器</a>
		              </div>
		            </div>
		            
		            
		           <div class="control-group">
		              <label class="control-label">活动日期:</label>
		              <div class="controls">
		                <div class="input-append date datepicker" data-date="2015-01-01">
		                  <input id="txt_date" class="span11" type="text" value="" data-date-format="yyyy-mm-dd">
		                  <span class="add-on"><i class="icon-th"></i></span> </div>
		              </div>
		           </div>
		           
		           <div class="control-group">
		              <label class="control-label">活动时间:</label>
		              <div class="controls">
		              	<div class="span3">
			              	时：
			              	<select id="sel_Hour">
			              	<c:forEach var="i" step="1" begin="0" end="23">
			                	<option value="${i}">${i}</option>
			                </c:forEach>
			                </select>
		              	</div>
		              <div class="span3">
		              		分：
			              	<select id="sel_Min">
			              	<c:forEach var="i" step="1" begin="0" end="59">
			                	<option value="${i}">${i}</option>
			                </c:forEach>
			                </select>
		              </div>
		                
		               
		              </div>
		           </div>
		            
		            
		            <div class="control-group">
		              <label class="control-label" for="checkboxes">性别限制:</label>
		              <div class="controls">
		                <div class="btn-group" data-toggle="buttons-radio">
		                  <button onclick="onGender(0)" class="btn btn-primary active" type="button">不限</button>
		                  <button onclick="onGender(1)" class="btn btn-primary" type="button">男</button>
		                  <button onclick="onGender(2)" class="btn btn-primary" type="button">女</button>
		                </div>
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">用户等级限制:</label>
		              <div class="controls">
		                <select id="sel_UserLevel">
		                <option value="3">黑卡</option>
		                <option value="2">铂金卡</option>
		                <option value="1" selected>普通</option>
		                </select>
		              </div>
		            </div>
		            
		             <div class="control-group">
		              <label class="control-label">花费类型:</label>
		              <div class="controls">
		                <select id="sel_PayType">
		                  <option value="1">请客</option>
		                  <option value="2">AA制</option>
		                </select>
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">是否置顶:</label>
		              <div class="controls">
		                <select id="sel_OnTop">
		                  <option value="0">不置顶</option>
		                  <option value="1">置顶</option>
		                </select>
		              </div>
		            </div>
		         
		         	<div class="control-group">
		              <label class="control-label">活动描述:</label>
		              <div class="controls">
		                <input id="txt_des" class="span11" type="text" placeholder="">
		              </div>
		            </div>
		            
		            
		            <div class="form-actions">
              			<button id="bt_submit" onclick="onSubmit(this)" class="btn btn-success" type="button">创建</button>
            		</div>
	          	</form>
              
              </div>
            </div>
          </div>
    </div>

      
	
	 
</div>

<jsp:include page="../framefoot.jsp" flush="true" />

<script>

	var gender = 0;
	
	$(function()
	{	
		$.ajaxSetup({cache:false });
		
		$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
	});
	

	function onSubmit(sender)
	{
		var title 		= $("#txt_title").val();
		var add 		= $("#txt_add").val();
		var txt_long 	= $("#txt_long").val();
		var txt_la 		= $("#txt_la").val();
		var txt_date	= $("#txt_date").val();
		var txt_hour	= $("#sel_Hour").val();
		var txt_min		= $("#sel_Min").val();
		var userLevel   = $("#sel_UserLevel").val();
		var payType   	= $("#sel_PayType").val();
		var onTop   	= $("#sel_OnTop").val();
		var des			= $("#txt_des").val();
		
		if(title=="")
		{
			alert("请填写活动主题");
			return;
		}
		
		if(!isVaild(title))
		{
			alert("活动主题只能使用中文，英文和数字组成");
			return;
		}
		
		if(!isVaild(des))
		{
			alert("活动描述只能使用中文，英文和数字组成");
			return;
		}
		
		if(add=="")
		{
			alert("请填写活动地址");
			return;
		}
		
		if(txt_date=="")
		{
			alert("请选择活动日期");
			return;
		}
		
		if(txt_long=="")
			txt_long=0;
		
		if(txt_la=="")
			txt_la=0;
		
		$(sender).attr("disabled","disabled");
		
		var url = "doCreateEvent";
		var data = "title="+title+"&add="+add+"&lon="+txt_long+"&gender="+gender+"&la="+txt_la+"&data="+txt_date+"&hour="+txt_hour+"&min="+txt_min+"&userLevel="+userLevel+"&payType="+payType+"&onTop="+onTop+"&des="+des;
		$.post(url,data,function(ret)
		{
			if(ret == 0)
			{
				alert("创建活动成功！");
			}
			else
			{
				alert("创建活动失败！");
			}
			
			$("#bt_submit").removeAttr("disabled");
			location.reload();
		});
	}
	
	function onGender(g)
	{
		gender = g;
	}
	
	function isVaild(str)
	{
		var badChar = " "+"　";//半角与全角空格
		badChar += "`~!@#$%^&()-_=+]\\|:;\"\\'<,>?/";//不包含*或.的英文符号
		if(""==str){
			return false;
		}
		
		for(var i=0;i<str.length;i++ )
		{
			var c = str.charAt(i);//字符串str中的字符
			if(badChar.indexOf(c) > -1)
			{
				return false;
			}
		}
		
		return true;
	}
</script>