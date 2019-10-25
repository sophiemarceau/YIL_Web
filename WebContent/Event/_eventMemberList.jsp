<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<table class="table table-bordered table-invoice-full">
                  <thead>
                    <tr>
                      <th style="width:64px"></th>	
                      <th>昵称</th>
                      <th>地区</th>
                      <th>积分</th>
                    </tr>
                  </thead>
            	<tbody>
            	<c:forEach var="l" items="${list}">
            		<tr>
            			<td><img src="../upload/userpic/${l.UserPic}" style="width:64px;height:64px" /></td>
            			<td style="line-height:64px; text-align: center !important">${l.NickName }</td>
            			<td style="line-height:64px; text-align: center !important">${l.AreaName }</td>
            			<td style="line-height:64px; text-align: center !important">${l.MyCoin }</td>
            		</tr>
            	</c:forEach>
            	</tbody>
     </table>
     