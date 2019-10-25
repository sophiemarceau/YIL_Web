<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<table class="table table-bordered table-invoice-full">
                  <thead>
                    <tr>
                      <th style="width:64px"></th>	
                      <th></th>
                      <th>操作</th>
                    </tr>
                  </thead>
            	<tbody>
            	<c:forEach var="l" items="${list.dataList}">
            		<tr>
            			<td style="text-align: center !important">
            				<img src="../upload/userpic/${l.UserPic}" style="width:64px;height:64px" /><br/>
            				<span>${l.NickName }</span>
            			</td>
            			<td>
            			${l.CreateTime}<br/>
            			${l.body }
            			</td>
            			
            			<td style="line-height:64px; text-align: center !important">
            				<a href="#" onClick="return onDelete(this,${l.ET_ID})">删除</a>
            			</td>
            		</tr>
            	</c:forEach>
            	</tbody>
     </table>
     
     	<c:set value="${list}" var="dataSource" scope="session"/>
        <c:set value="loadEventList" var="paperFunc" scope="session"/>
        <c:set value="" var="paperFuncParam" scope="session"/>
        <c:import url="../Common/pagerCtrl.jsp"/>