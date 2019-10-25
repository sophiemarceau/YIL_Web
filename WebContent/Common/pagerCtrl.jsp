<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!--
 <nav>
		  <ul class="pagination" style="margin-top:-10px">
		    <li><a onclick="${paperFunc}('sub',${dataSource.currentPage}${paperFuncParam})" href="#">&laquo;</a></li>
			    
			    <c:if test="${dataSource.currentPage>6 }">
			    	<li> <a onclick="${paperFunc}('',1${paperFuncParam})" href="#">1...</a></li>
			    </c:if>
			    
			    <c:forEach var="item" varStatus="status" begin="0" end="${(dataSource.pageDisplayCount-1)<0?0:(dataSource.pageDisplayCount-1)}">
			    	<c:if test="${(dataSource.firstPageNumber+status.index)<=dataSource.pageCount }">
			    	<c:choose>  
					    <c:when test="${dataSource.currentPage == (dataSource.firstPageNumber+status.index)}">
					    	<li class="active">
					    </c:when>
					     <c:otherwise>
					     	<li>
					     </c:otherwise>
					</c:choose> 
					
			    		<a onclick="${paperFunc}('',${dataSource.firstPageNumber+status.index}${paperFuncParam})" href="#"><c:out value="${dataSource.firstPageNumber+status.index}" /></a>
			    	</li>
			    	</c:if>
			    </c:forEach>
			    <c:if test="${(dataSource.pageCount-dataSource.currentPage)>=5 && dataSource.pageCount>10 }">
			    	<li> <a onclick="${paperFunc}('',${dataSource.pageCount}${paperFuncParam})" href="#">...${dataSource.pageCount}</a></li>
			    </c:if>
		    <li><a onclick="${paperFunc}('add',${dataSource.currentPage}${paperFuncParam})" href="#">&raquo;</a></li>
		  </ul>
		</nav>
-->
<div class="dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers" id="DataTables_Table_0_paginate">
	<a tabindex="0" onclick="${paperFunc}('',1${paperFuncParam})" class="first ui-corner-tl ui-corner-bl fg-button ui-button ui-state-default ${dataSource.currentPage==1?'ui-state-disabled':'' }" id="DataTables_Table_0_first">首页</a>
	<a tabindex="0" onclick="${paperFunc}('sub',${dataSource.currentPage}${paperFuncParam})" class="previous fg-button ui-button ui-state-default ${dataSource.currentPage==1?'ui-state-disabled':'' }" id="DataTables_Table_0_previous">上一页</a>
	<span>
		 <c:forEach var="item" varStatus="status" begin="0" end="${(dataSource.pageDisplayCount-1)<0?0:(dataSource.pageDisplayCount-1)}">
			    	<c:if test="${(dataSource.firstPageNumber+status.index)<=dataSource.pageCount }">
			    		<a class="fg-button ui-button ui-state-default ${dataSource.currentPage == (dataSource.firstPageNumber+status.index)?'ui-state-disabled':''}" onclick="${paperFunc}('',${dataSource.firstPageNumber+status.index}${paperFuncParam})" href="#"><c:out value="${dataSource.firstPageNumber+status.index}" /></a>
			    	</c:if>
		</c:forEach>
			    
	</span>
	<a tabindex="0" onclick="${paperFunc}('add',${dataSource.currentPage}${paperFuncParam})" class="next fg-button ui-button ui-state-default ${dataSource.currentPage==dataSource.pageCount?'ui-state-disabled':'' }" id="DataTables_Table_0_next">下一页</a>
	<a tabindex="0" onclick="${paperFunc}('',${dataSource.pageCount}${paperFuncParam})" class="last ui-corner-tr ui-corner-br fg-button ui-button ui-state-default ${dataSource.currentPage==dataSource.pageCount?'ui-state-disabled':'' }" id="DataTables_Table_0_last">尾页</a>
</div>
