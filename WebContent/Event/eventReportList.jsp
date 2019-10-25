<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">

	<div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-briefcase"></i> </span>
            <h5>被举报活动列表</h5>
          </div>
          <div class="widget-content" id="dv_ulist">
          	
          </div>
    </div>
	 
</div>

<div id="myModal" class="modal hide">
              <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button">×</button>
                <h3 id="event_title">活动详情</h3>
              </div>
              <div class="modal-body">
                <div>
                <p><strong>基本信息</strong></p>
                	<table class="table table-bordered table-invoice-full" id="tb_detail">
                		<thead>
                		<tr>
                			<th>项目</th>
                			<th>内容</th>
                		</tr>
                		</thead>
                		<tbody>
                		<tr>
                			<td>时间</td>
                			<td></td>
                		</tr>
                		<tr>
                			<td>地点</td>
                			<td></td>
                		</tr>
                		<tr>
                			<td>性别限定</td>
                			<td></td>
                		</tr>
                		<tr>
                			<td>会员等级限定</td>
                			<td></td>
                		</tr>
                		<tr>
                			<td>花费类型</td>
                			<td></td>
                		</tr>
                		<tr>
                			<td>友币数量</td>
                			<td></td>
                		</tr>
                		<tr>
                			<td>是否置顶</td>
                			<td></td>
                		</tr>
                		<tr>
                			<td>描述</td>
                			<td></td>
                		</tr>
                		</tbody>
                	</table>
                	<p><strong>已加入成员列表</strong></p>
                	<div id="dv_memeberlist"></div>
                	
                </div>
              </div>
    </div>
    
<jsp:include page="../framefoot.jsp" flush="true" />

<script>
	
	$(function()
	{	
		$.ajaxSetup({cache:false });
		
		loadEventList("",1);
		
	});
	

	var currpage 	= 1;
	
	function loadEventList(method,page)
	{
		currpage = page;
		
		var url = "loadReportEventList?method=" + method + "&page=" +currpage;
		$("#dv_ulist").load(url);
	}
	
	function onShowMember(sender,data)
	{
		var datas = data.split(",");
		$("#event_title").text(datas[1]);
		
		var g = datas[4];
		var gender_str = "";
		
		switch(g)
		{
		case 1:
			gender_str="男";
			break;
		case 2:
			gender_str="女";
			break;
		default:
			gender_str="不限";
			break;
		}
		
		var pay_str = datas[6]==1?"自费":"AA制";
		var ontop_str = datas[8]==1?"置顶":"不置顶";
		
		$("#tb_detail tbody tr:eq(0) td:eq(1)").text(datas[2]);
		$("#tb_detail tbody tr:eq(1) td:eq(1)").text(datas[3]);
		$("#tb_detail tbody tr:eq(2) td:eq(1)").text(gender_str);
		$("#tb_detail tbody tr:eq(3) td:eq(1)").text(datas[5]);
		$("#tb_detail tbody tr:eq(4) td:eq(1)").text(pay_str);
		$("#tb_detail tbody tr:eq(5) td:eq(1)").text(datas[7]);
		$("#tb_detail tbody tr:eq(6) td:eq(1)").text(ontop_str);
		$("#tb_detail tbody tr:eq(7) td:eq(1)").text(datas[9]);
		
		$("#dv_memeberlist").html("");
		$("#dv_memeberlist").load("loadEventMemberList?eid="+datas[0]);
		
		$("#myModal").modal('show');
		
		return false;
	}
	
	function onResumeEvent(sender,eid)
	{
		var url = "resumeEvent?eid="+eid;
		$.get(url,function(data)
		{
			loadEventList("",currpage);
		});
		
		return false;
	}
	
	function onCloseEvent(sender,eid)
	{
		var url = "closeEvent?eid="+eid;
		$.get(url,function(data)
		{
			loadEventList("",currpage);
		});
		
		return false;
	}
	
	function onTopEvent(sender,eid,type)
	{
		var url = "topEvent?eid="+eid+"&type="+type;
		$.get(url,function(data)
		{
			loadEventList("",currpage);
		});
		
		return false;
	}
	
	function onShowTopic(sender,eid)
	{
		window.location = "EventCommentList?eid="+eid+"&fromPage=2";
	}
</script>