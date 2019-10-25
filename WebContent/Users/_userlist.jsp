<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<table class="table table-bordered table-invoice-full">
                  <thead>
                    <tr>
                      <th style="width:64px"></th>	
                      <th>手机号</th>
                      <th>昵称</th>
                      <th>性别</th>
                      <th>地区</th>
                      <th>职业</th>
                      <th>月薪</th>
                      <th>年龄</th>
                      <th>积分</th>
                <!--       <th>操作</th> -->
                    </tr>
                  </thead>
            	<tbody>
            	<c:forEach var="l" items="${list.dataList}">
            		<tr>
            			<td><img src="../upload/userpic/${l.UserPic}" style="width:64px;height:64px" /></td>
            			<td style="line-height:64px; text-align: center !important">${l.PhoneNumber }</td>
            			<td style="line-height:64px; text-align: center !important">${l.NickName }</td>
            			<td style="line-height:64px; text-align: center !important">
            				<c:if test="${l.Gender==1}">男</c:if>
            				<c:if test="${l.Gender==2}">女</c:if>
            			</td>
            			<td style="line-height:64px; text-align: center !important">${l.AreaName }</td>
            			<td style="line-height:64px; text-align: center !important">${l.JobName }</td>
            			<td style="line-height:64px; text-align: center !important">${l.SalaryName }</td>
            			<td style="line-height:64px; text-align: center !important">${l.Age }</td>
            			<td style="line-height:64px; text-align: center !important">${l.MyCoin }</td>
            			
            		<!--  	<td></td> -->
            		</tr>
            	</c:forEach>
            	</tbody>
     </table>
     
     	<c:set value="${list}" var="dataSource" scope="session"/>
        <c:set value="loadUsersList" var="paperFunc" scope="session"/>
        <c:set value="" var="paperFuncParam" scope="session"/>
        <c:import url="../Common/pagerCtrl.jsp"/>