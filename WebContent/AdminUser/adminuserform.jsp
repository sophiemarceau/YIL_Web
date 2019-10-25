<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">
      
		<div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>管理员信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="#" method="get" class="form-horizontal">
            <div class="control-group">
              <label class="control-label">登录账户名 :</label>
              <div class="controls">
                <input id="txt_username" type="text" class="span11" placeholder="4-20位 英文和字母">
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">中文姓名 :</label>
              <div class="controls">
                <input id="txt_nickname" type="text" class="span11" placeholder="中文姓名">
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">登录密码</label>
              <div class="controls">
                <input id="txt_password" type="password" class="span11" placeholder="6-20位英文和字母">
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">确认密码 :</label>
              <div class="controls">
                <input id="txt_repassword" type="password" class="span11" placeholder="再次输入密码">
              </div>
            </div>
            
           
            <div class="form-actions">
              <button type="button" id="bt_submit" class="btn btn-success">保存</button>
            </div>
          </form>
        </div>
      </div>
	 
</div>

<jsp:include page="../framefoot.jsp" flush="true" />

<script>
$(document).ready(function() 
	{
		$.ajaxSetup({cache:false });
		
		$("#bt_submit").click(function()
		{
			var txt_username 	= $("#txt_username").val();
			var txt_nickname 	= $("#txt_nickname").val();
			var txt_password 	= $("#txt_password").val();
			var txt_repassword 	= $("#txt_repassword").val();
			if(txt_username=="")
			{
				alert("输入登录账户名称");
				return;
			}
			
			if(txt_nickname=="")
			{
				alert("输入姓名");
				return;
			}
			
			if(txt_password=="")
			{
				alert("输入登录密码");
				return;
			}
			
			if(txt_password.length<6 || txt_password.length>20)
			{
				alert("密码需要6-20位的英文或数字");
				return;
			}
			
			if(!validateStrAndNo(txt_password))
			{
				alert("密码只能由英文和数字组成");
				return;
			}
			
			if(txt_password!=txt_repassword)
			{
				alert("确认密码不正确");
				return;
			}
			
			$(this).attr("disabled","disabled");
			
			var url 	= "doAddUser";
			var data 	= "username="+txt_username+"&nickname="+txt_nickname+"&pass="+txt_password;
			$.post(url,data,function(data)
			{
				var ar = eval(data);
				alert(ar.Infomation);
				$("#bt_submit").removeAttr("disabled");
				location.reload();
			});
			
		});
	});
	
function validateStrAndNo(str) 
{	
	var reg = {letter:"^[A-Za-z]+$",num:"^([+-]?)\\d*\\.?\\d+$"};	
	if((new RegExp(reg.letter)).test(str) || (new RegExp(reg.num)).test(str)) 
	{		
		return true;	
	}
	else
	{		
		return false;	
	}
}

</script>