<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../framepage.jsp" flush="true" />

<div class="row-fluid">

	<div class="accordion" id="collapse-group">
          <div class="accordion-group widget-box">
            <div class="accordion-heading">
              <div class="widget-title"> <a id="form_head" href="#collapseGThree" data-toggle="collapse" data-parent="#collapse-group"> <span class="icon"><i class="icon-eye-open"></i></span>
                <h5>活动搜索</h5>
                </a> </div>
            </div>
            <div class="collapse accordion-body" id="collapseGThree">
              <div class="widget-content">
              
	            <form class="form-horizontal" action="#" method="get">
		            <div class="control-group">
		              <label class="control-label">活动主题 :</label>
		              <div class="controls">
		                <input id="txt_title" class="span11" type="text" placeholder="输入活动的主题">
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">活动地点 :</label>
		              <div class="controls">
		                <input id="txt_address" class="span11" type="text" placeholder="输入活动地点名称">
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label" for="checkboxes">花费类型:</label>
		              <div class="controls">
		                <div class="btn-group" data-toggle="buttons-radio">
		                  <button onclick="onPayFor(0)" class="btn btn-primary active" type="button">不限</button>
		                  <button onclick="onPayFor(1)" class="btn btn-primary" type="button">请客</button>
		                  <button onclick="onPayFor(2)" class="btn btn-primary" type="button">AA制</button>
		                </div>
		              </div>
		            </div>
		            
		           <div class="control-group">
		              <label class="control-label">活动时间:</label>
		              <div class="controls">
		                <div class="input-append date datepicker" data-date="2015-01-01">
		                  <input id="txt_date" class="span11" type="text" value="" data-date-format="yyyy-mm-dd">
		                  <span class="add-on"><i class="icon-th"></i></span> </div>
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
            <h5>活动列表</h5>
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
		
		$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
		
		loadEventList("",1);
	});
	
	var paytype 	= 0;
	var title 		= "";
	var address 	= "";
	var date		= "";
	var currpage 	= 1;
	
	
	function loadEventList(method,page)
	{
		currpage = page;
		
		var url = "loadeventlist?title="+encodeURIComponent(encodeURIComponent(title))+"&address="+encodeURIComponent(encodeURIComponent(address))+"&date="+date+"&paytype="+paytype+"&method="+method+"&page="+currpage;
		$("#dv_ulist").load(url);
	}
	
	function onPayFor(g)
	{
		paytype = g;
	}
	
	function onQuery(sender)
	{
		$("#form_head").click();
		
		title 		= $("#txt_title").val();
		address 	= $("#txt_address").val();
		date		= $("#txt_date").val();
		
		loadEventList("",1);
	}
	
	function onShowMember(sender,data)
	{
		var datas = data.split(",");
		$("#event_title").text(datas[1]);
		
		var g = datas[4];
		var gender_str = "";

		switch(parseInt(g))
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
		
		var pay_str = datas[6]==1?"请客":"AA制";
		var ontop_str = datas[8]==1?"置顶":"不置顶";
		var userLevel = datas[5];
		var levelName = "";
		switch(parseInt(userLevel))
		{
			case 1:
				levelName="普通";
				break;
			case 2:
				levelName="铂金卡";
				break;
			case 3:
				levelName="黑卡";
				break;
		}
		
		$("#tb_detail tbody tr:eq(0) td:eq(1)").text(datas[2]);
		$("#tb_detail tbody tr:eq(1) td:eq(1)").text(datas[3]);
		$("#tb_detail tbody tr:eq(2) td:eq(1)").text(gender_str);
		$("#tb_detail tbody tr:eq(3) td:eq(1)").text(levelName);
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
		window.location = "EventCommentList?eid="+eid+"&fromPage=1";
	}
</script>