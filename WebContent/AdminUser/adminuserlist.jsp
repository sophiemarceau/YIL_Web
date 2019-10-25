<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">
      
	<div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-briefcase"></i> </span>
            <h5>管理员列表</h5>
          </div>
          <div class="widget-content">
          	<table class="table table-bordered table-invoice-full">
                  <thead>
                    <tr>
                      <th class="head0">登录名</th>
                      <th class="head1">名称</th>
                      <th class="head0 right">操作</th>
                    </tr>
                  </thead>
            	<tbody>
            	<c:forEach var="l" items="${list}">
            		<tr>
            			<td style="text-align: center !important">${l.UserName }</td>
            			<td style="text-align: center !important">${l.NickName }</td>
            			<td style="text-align: center !important">
            				<a onclick="onDelete(${l.UID})" class="tip" href="#" data-original-title="删除">删除</a>
            			</td>
            		</tr>
            	</c:forEach>
            	</tbody>
            </table>
          </div>
    </div>
	 
</div>

<jsp:include page="../framefoot.jsp" flush="true" />

<script>
	function onDelete(uid)
	{
		if(!window.confirm('你确定要删除吗？'))
		{
			return false;
		}
		
		var url = "<%=request.getContextPath()%>/Admin/doDeleteAdminUser";
		$.post(url,"uid="+uid,function(data)
		{
			location.reload();
		});
		
	}
</script>