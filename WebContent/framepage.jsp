<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Matrix Admin</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/fullcalendar.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/matrix-style.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/matrix-media.css" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/colorpicker.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/datepicker.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/uniform.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/select2.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-wysihtml5.css" />


<link href="<%=request.getContextPath()%>/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.gritter.css" />
<!--  
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
-->
</head>
<body>

<!--Header-part-->
<div id="header">
  <h1><a href="#">YoueLink Admin Web</a></h1>
</div>
<!--close-Header-part--> 


<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li  class="dropdown" id="profile-messages" ><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>  <span class="text">欢迎, ${User.nickName }</span><b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a href="<%=request.getContextPath()%>/Admin/ChangeUserPass"><i class="icon-user"></i> 修改密码</a></li>
        <li class="divider"></li>
        <li><a href="<%=request.getContextPath()%>/logoff"><i class="icon-key"></i>安全退出</a></li>
      </ul>
    </li>
   
  </ul>
</div>
<!--close-top-Header-menu-->


<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 主菜单</a>
  <ul>
    <li class="${menu=='home'?'active':''}"><a href="<%=request.getContextPath()%>/mainPage"><i class="icon icon-home"></i> <span>总览</span></a> </li>
    <li class="${menu=='user'?'active':''}"> <a href="<%=request.getContextPath()%>/Users/UserList"><i class="icon icon-signal"></i> <span>用户管理</span></a> </li>
    <li class="submenu ${menu=='event1'?'active':''} ${menu=='event2'?'active':''} ${menu=='event3'?'active':''}"><a href="#"><i class="icon icon-th"></i> <span>活动管理</span></a>
    	<ul>
        <li class="${menu=='event1'?'active':''}"><a href="<%=request.getContextPath()%>/Event/createEvent">创建活动</a></li>
        <li class="${menu=='event2'?'active':''}"><a href="<%=request.getContextPath()%>/Event/eventList">所有活动</a></li>
        <li class="${menu=='event3'?'active':''}"><a href="<%=request.getContextPath()%>/Event/eventReportList">举报的活动</a></li>
      	</ul>
    </li>
    
    <li class="submenu ${menu=='admin1'?'active':''} ${menu=='admin2'?'active':''}"><a href="#"><i class="icon icon-th"></i> <span>管理员管理</span></a>
		<ul>
        <li class="${menu=='admin1'?'active':''}"><a href="<%=request.getContextPath()%>/Admin/addAdminUser">新增管理员</a></li>
        <li class="${menu=='admin2'?'active':''}"><a href="<%=request.getContextPath()%>/Admin/adminUserList">管理员列表</a></li>
      </ul>
	</li>
  </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">
<!--breadcrumbs-->
  <div id="content-header">
    <div id="breadcrumb"> 
    	<a href="<%=request.getContextPath()%>/mainPage" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
    	<span></span>
    </div>
  </div>
<!--End-breadcrumbs-->

<div class="container-fluid">
  
