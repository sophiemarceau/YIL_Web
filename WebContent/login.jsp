<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    
<head>
        <title>YoueLink Admin</title><meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-responsive.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/matrix-login.css" />
        <link href="<%=request.getContextPath()%>/font-awesome/css/font-awesome.css" rel="stylesheet" />
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>

    </head>
    <body>
        <div id="loginbox">            
            <form id="loginform" class="form-vertical" action="dologin">
				 <div class="control-group normal_text"> <h3><img src="img/logo.png" alt="Logo" /></h3></div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lg"><i class="icon-user"></i></span><input onkeydown="return SubmitKeyClick(this,event)" id="text_u" type="text" placeholder="Username" />
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_ly"><i class="icon-lock"></i></span><input onkeydown="return SubmitKeyClick(this,event)" id="text_p" type="password" placeholder="Password" />
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <span class="pull-right"><a onClick="return onLogon(this)" href="#" class="btn btn-success" id="bt_login" /> Login</a></span>
                </div>
            </form>
            <form id="recoverform" action="#" class="form-vertical">
				<p class="normal_text">Enter your e-mail address below and we will send you instructions how to recover a password.</p>
				
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lo"><i class="icon-envelope"></i></span><input type="text" placeholder="E-mail address" />
                        </div>
                    </div>
               
                <div class="form-actions">
                    <span class="pull-left"><a href="#" class="flip-link btn btn-success" id="to-login">&laquo; Back to login</a></span>
                    <span class="pull-right"><a class="btn btn-info"/>Reecover</a></span>
                </div>
            </form>
        </div>
        
        <script src="js/jquery.min.js"></script>  
        <script src="js/matrix.login.js"></script> 
    </body>
    
    <script>
    $(function ()
   	{
   		$.ajaxSetup({cache:false });	    	
    });
    
    function SubmitKeyClick(obj, evt) {
        evt = (evt) ? evt : ((window.event) ? window.event : "");
        keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
        if (keyCode == 13) {
            $("#bt_login").click();
            return false;
        }
    }
    
    function onLogon(sender)
    {
    	var u = $("#text_u").val();
    	var p = $("#text_p").val();
    	
    	if(u=="" || p=="")
    	{
    		alert("请输入用户名或密码");
    		return false;
    	}
    	
    	var url = "doLogin";
    	var data = "userName="+u+"&password="+p;
    	$.post(url,data,function(data)
    	{
    		if(data == "1")
    		{
    			location = "mainPage";
    		}
    		else
    		{
    			alert("登录失败，用户名或密码错误");
    		}
    	});
    	
    	return false;
    }
    </script>

</html>
