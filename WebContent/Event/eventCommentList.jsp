<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">

	<div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-briefcase"></i> </span>
            <h5>活动评论列表</h5>
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
		
		loadEventList("",1);
		
		var fromPage = ${fromPage};
		if(fromPage == 1)
			$("#breadcrumb span").html("<a href=\"<%=request.getContextPath()%>/Event/eventList\" title=\"所有活动\" class=\"tip-bottom\"><i class=\"icon icon-th\"></i> 所有活动</a>");
		else
			$("#breadcrumb span").html("<a href=\"<%=request.getContextPath()%>/Event/eventReportList\" title=\"举报的活动\" class=\"tip-bottom\"><i class=\"icon icon-th\"></i> 举报的活动</a>");
	});
	

	var currpage 	= 1;
	
	function loadEventList(method,page)
	{
		currpage = page;
		
		var url = "loadEventCommentList?eid=${eid}" + "&method=" + method + "&page=" +currpage;
		$("#dv_ulist").load(url);
	}
	
	function onDelete(sender,id)
	{
		if(!window.confirm('你确定要删除吗？'))
		{
			return false;
		}
		
		var url = "deleteComment?etid="+id;
		$.get(url,function(data)
		{
			if(data == 0)
			{
				location.reload();
			}
			else
			{
				alert("删除失败");
			}
		});
	}
	
</script>