<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">

	<div class="accordion" id="collapse-group">
          <div class="accordion-group widget-box">
            <div class="accordion-heading">
              <div class="widget-title"> <a id="form_head" href="#collapseGThree" data-toggle="collapse" data-parent="#collapse-group"> <span class="icon"><i class="icon-eye-open"></i></span>
                <h5>用户搜索</h5>
                </a> </div>
            </div>
            <div class="collapse accordion-body" id="collapseGThree">
              <div class="widget-content">
              
	            <form class="form-horizontal" action="#" method="get">
		            <div class="control-group">
		              <label class="control-label">手机号 :</label>
		              <div class="controls">
		                <input id="txt_phone" class="span11" type="text" placeholder="输入手机号码" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">昵称 :</label>
		              <div class="controls">
		                <input id="txt_nickname" class="span11" type="text" placeholder="输入用户昵称">
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label" for="checkboxes">性别:</label>
		              <div class="controls">
		                <div class="btn-group" data-toggle="buttons-radio">
		                  <button onclick="onGender(0)" class="btn btn-primary active" type="button">不限</button>
		                  <button onclick="onGender(1)" class="btn btn-primary" type="button">男</button>
		                  <button onclick="onGender(2)" class="btn btn-primary" type="button">女</button>
		                </div>
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">地区:</label>
		              <div class="controls">
		                <select id="sel_Area">
		                <option value="0">不限</option>
		                <c:forEach var="al" items="${AreaList}">
		                	<option value="${al.ID}">${al.Name }</option>
		                </c:forEach>
		                </select>
		              </div>
		            </div>
		            
		             <div class="control-group">
		              <label class="control-label">职业:</label>
		              <div class="controls">
		                <select id="sel_Job">
		                <option value="0">不限</option>
		                  <c:forEach var="al" items="${JobList}">
		                	<option value="${al.ID}">${al.Name }</option>
		                </c:forEach>
		                </select>
		              </div>
		            </div>
		            
		           <div class="control-group">
		              <label class="control-label">月薪:</label>
		              <div class="controls">
		                <select id="sel_Salary">
		                <option value="0">不限</option>
		                 <c:forEach var="al" items="${SalaryList}">
		                	<option value="${al.ID}">${al.Name }</option>
		                </c:forEach>
		                </select>
		              </div>
		            </div>
		            
		            <div class="form-actions">
              			<button onclick="onQuery(this)" class="btn btn-success" type="button">搜索</button>
            		</div>
	          	</form>
              
              </div>
            </div>
          </div>
    </div>

      
	<div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-briefcase"></i> </span>
            <h5>用户成员列表</h5>
          </div>
          <div class="widget-content" id="dv_ulist">
          	
          </div>
    </div>
	 
</div>

<jsp:include page="../framefoot.jsp" flush="true" />

<script>
	
	$(function()
	{	
		$.ajaxSetup({cache:false });
		
		loadUsersList("",1);
	});
	
	var gender 		= 0;
	var phoneNumber = "";
	var nickName 	= "";
	var areaId 		= 0;
	var jobId 		= 0;
	var salaryId 	= 0;
	var currpage 	= 1;
	
	
	function loadUsersList(method,page)
	{
		currpage = page;
		
		var url = "loaduserlist?phoneNumber="+phoneNumber+"&nickName="+encodeURIComponent(encodeURIComponent(nickName))+"&Gender="+gender+"&AreaID="+areaId+"&JobID="+jobId+"&SalaryID="+salaryId+"&method="+method+"&page="+currpage;
		$("#dv_ulist").load(url);
	}
	
	function onGender(g)
	{
		gender = g;
	}
	
	function onQuery(sender)
	{
		$("#form_head").click();
		
		phoneNumber = $("#txt_phone").val();
		nickName 	= $("#txt_nickname").val();
		areaId 		= $("#sel_Area").val();
		jobId 		= $("#sel_Job").val();
		salaryId 	= $("#sel_Salary").val();
		
		loadUsersList("",1);
	}
	
</script>