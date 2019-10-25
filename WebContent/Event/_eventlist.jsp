<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<table class="table table-bordered table-invoice-full">
                  <thead>
                    <tr>
                      <th style="width:64px"></th>	
                      <th>主题</th>
                      <th style="max-width:100px">地点</th>
                      <th>花费</th>
                      <th>时间</th>
                      <th>成员数</th>
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
            			<td style="word-wrap:break-word;line-height:64px; text-align: center !important">${l.Title }</td>
            			<td style="word-break:break-all;line-height:64px; text-align: center !important">${l.EventAddress }</td>
            			<td style="line-height:64px; text-align: center !important">
            				<c:if test="${l.PayType==1}">请客</c:if>
            				<c:if test="${l.PayType==2}">AA制</c:if>
            			</td>
            			<td style="line-height:64px; text-align: center !important">${l.EventDate }</td>
            			<td style="line-height:64px; text-align: center !important">${l.MemberCount }</td>
            			
            			<td style="line-height:64px; text-align: center !important">
            			<a href="#" onClick="return onShowMember(this,'${l.EID},${l.Title},${l.EventDate},${l.EventAddress},${l.Gender},${l.UserLevel},${l.PayType },${l.Coins },${l.OnTop},${l.Description}')">详细</a> | 
            			<a href="#" onClick="onShowTopic(this,${l.EID})">讨论</a> | 
            			<c:if test="${l.IsEnable==1}">
            				<a href="#" onClick="return onCloseEvent(this,${l.EID})">关闭活动</a> |
            			</c:if>
            			<c:if test="${l.IsEnable==0}">
            				<a href="#" onClick="return onResumeEvent(this,${l.EID})">恢复活动</a> |
            			</c:if>
            			
            			<c:if test="${l.OnTop==1}">
            				<a href="#" onClick="return onTopEvent(this,${l.EID},0)">取消置顶</a>
            			</c:if>
            			<c:if test="${l.OnTop==0}">
            				<a href="#" onClick="return onTopEvent(this,${l.EID},1)">置顶</a>
            			</c:if>
            			
            			</td>
            		</tr>
            	</c:forEach>
            	</tbody>
     </table>
     
     	<c:set value="${list}" var="dataSource" scope="session"/>
        <c:set value="loadEventList" var="paperFunc" scope="session"/>
        <c:set value="" var="paperFuncParam" scope="session"/>
        <c:import url="../Common/pagerCtrl.jsp"/>