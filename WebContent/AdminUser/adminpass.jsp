<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">
      
		<div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>修改密码</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="#" method="get" class="form-horizontal">
            
            <div class="control-group">
              <label class="control-label">旧的密码</label>
              <div class="controls">
                <input id="txt_password_old" type="password" class="span11" placeholder="6-20位英文和字母">
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">新的密码</label>
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
              <button type="button" id="bt_submit" class="btn btn-success">提交</button>
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
			var txt_password_old 	= $("#txt_password_old").val();
			var txt_password 		= $("#txt_password").val();
			var txt_repassword 		= $("#txt_repassword").val();
			
			if(txt_password_old=="")
			{
				alert("请输入旧的密码");
				return;
			}
			
			if(txt_password=="")
			{
				alert("请输入新的密码");
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
			
			var url 	= "doChangeUserPass";
			var data 	= "oldPass="+txt_password_old+"&newPass="+txt_password;
			$.post(url,data,function(data)
			{
				if(data=="0")
				{
					alert("修改密码成功");
				}
				else
				{
					alert("旧的密码输入不正确");
				}

				$("#bt_submit").removeAttr("disabled");
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