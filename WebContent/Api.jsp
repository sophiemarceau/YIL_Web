<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Matrix Admin</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="css/fullcalendar.css" />
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="css/jquery.gritter.css" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
</head>
<body>
<div id="content" style="margin-top:30px;">
<div class="container-fluid">
<div class="widget-box">
        <div class="widget-title bg_lg"><span class="icon"><i class="icon-signal"></i></span>
          <h5>Web Api 说明</h5>
        </div>
        <div class="widget-content">
          <div class="row-fluid">
            <table class="table table-bordered table-striped">
            <thead>
            <th></th>
            </thead>
            <tbody>
            <tr>
            <td>
            WebAPI<br/>
返回结构统一为<br/>
{"Result":"true/false","Infomation":"","Data":""}<br/>
API Base URL: http://123.57.217.223/youelink/Api/<br/>
登录验证 ：将登录获得的token 放入http头的 Authentication 属性中
            </td>
            </tr>
            </tbody>
            </table>
            
            
          </div>
        </div>
      </div>
      

<div class="row-fluid">
	<div class="widget-box">

			<div class="widget-title">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#tab1">用户相关</a></li>
					<li class=""><a data-toggle="tab" href="#tab2">资源相关</a></li>
					<li class=""><a data-toggle="tab" href="#tab3">活动相关</a></li>
					<li class=""><a data-toggle="tab" href="#tab4">通讯录／好友</a></li>
					<li class=""><a data-toggle="tab" href="#tab5">用户设置</a></li>
					<li class=""><a data-toggle="tab" href="#tab6">支付部分</a></li>
				</ul>
			</div>
			
			<div class="widget-content tab-content">
            <div id="tab1" class="tab-pane active">
            	<table class="table table-bordered table-striped">
           
            <tbody>
            <tr>
            	<td>
					获取验证码<br/>
					[URL]: /Account/createVerifyCode<br/>
					[Method]: POST<br/>
					[Args]: phoneNumber<br/>
            	</td>
            </tr>
            
             <tr>
            	<td>
            		用户注册<br/>
					[URL]: /Account/regist2<br/>
					[Method]: POST<br/>
					[Args]:  phoneNumber=[手机号码]&
							 code=[验证码]&
							 password=[密码]&
							<br/>
            	</td>
            </tr>
            
             <tr>
            	<td>
            		用户登录<br/>
					[URL]: /Account/logonuser<br/>
					[Method]: POST<br/>
					[Args]:  phoneNumber=[手机号码]&password=[密码]<br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		忘记密码<br/>
					[URL]: /Account/forgetPassword<br/>
					[Method]: POST<br/>
					[Args]:  phoneNumber=[手机号码]<br/>
            	</td>
            </tr>
            
             <tr>
            	<td>
            		上传用户头像<br/>
            		[Auth]:token
					[URL]: /Account/UploadMyIcon<br/>
					[Method]: POST<br/>
					[Args]: uid=[用户ID]<br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		用户资料更新<br/>
            		[Auth]:token
					[URL]: /Account/updateUserInfo<br/>
					[Method]: POST<br/>
					[Args]: userPass=[密码]&
							 nickName=[昵称]&
							 gender=[性别]&
							 brithday=[生日]&
							 signText=[个性签名]&
							 salaryId=[月薪ID]&
							 areaId=[地区ID]&
							 jobId=[职位ID]&
							 userPic=[用户头像文件名]&
							 email=[邮件地址]&
							 hobbies=[兴趣爱好]
							 <br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		获取目标用户资料 - 根据系统UID<br/>
            		[Auth]:token
					[URL]: /Account/GetUserInfoByID<br/>
					[Method]: GET<br/>
					[Args]: uid=[UID]<br/>
            	</td>
            </tr>
            
             <tr>
            	<td>
            		获取目标用户资料 - 根据环信用户名<br/>
            		[Auth]:token
					[URL]: /Account/GetUserInfoByHXUser<br/>
					[Method]: GET<br/>
					[Args]: userName=[hxUser]<br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		用户搜索<br/>
            		[Auth]:token <br/>
					[URL]: /Account/SearchUsers<br/>
					[Method]: GET<br/>
					[Args]: phoneNumber=[手机号]&nickName=[昵称]&Gender=[性别 1/2 ]&AreaID=[地区ID]&JobID=[职业ID]&age=[年龄（数字）]&page=[当前页数，从1开始]<br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		定时更新用户位置<br/>
            		[Auth]:token <br/>
					[URL]: /Account/LocatedUser<br/>
					[Method]: POST<br/>
					[Args]: latitude=[纬度]&longitude=[经度]<br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		附近的人列表<br/>
            		[Auth]:token <br/>
					[URL]: /Account/NearUserList<br/>
					[Method]: GET<br/>
					[Args]: latitude=[纬度]&longitude=[经度]&page=[页数]<br/>
            	</td>
            </tr>
            
             <tr>
            	<td>
            		获取我的友币数量<br/>
            		[Auth]:token <br/>
					[URL]: /UserSetting/GetMyCoins<br/>
					[Method]: GET<br/>
					[Args]: <br/>
            	</td>
            </tr>
            
            </tbody>
            </table>
             
            </div>
            <div id="tab2" class="tab-pane">
            	<table class="table table-bordered table-striped">
	            
	            <tbody>
	             <tr>
	            	<td>
	            		服务器配置信息<br/>
						[URL]: /Resource/Configure<br/>
						[Method]: GET<br/>
						[Args]: <br/>
						[Return]:<br/>
						ServeVersion=当前服务器端版本 客户端版本如果小于此版本则需要更新
						Heartbeat=轮询定位提交时间 (分钟)<br/>
						UserPageCount=用户搜索的默认分页数量 <br/>
						FirendPageCount=好友列表的默认分页数量 <br/>
						EventPageCount=活动搜索的默认分页数量 <br/>
						TopicPageCount=活动评论搜索的默认分页数量 <br/>
	            	</td>
            	</tr>
            
	            	 <tr>
            	<td>
            		获取地区列表<br/>
					[URL]: /Resource/AreaList<br/>
					[Method]: GET<br/>
					[Args]: <br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		获取月薪列表<br/>
					[URL]: /Resource/SalaryList<br/>
					[Method]: GET<br/>
					[Args]: <br/>
            	</td>
            </tr>
            
            <tr>
            	<td>
            		获取职业列表<br/>
					[URL]: /Resource/JobList<br/>
					[Method]: GET<br/>
					[Args]: <br/>
            	</td>
            </tr>
	            </tbody>
	            </table>
             
            </div>
           
            <div id="tab3" class="tab-pane">
            	<table class="table table-bordered table-striped">
	            <tbody>
	            	 <tr>
		            	<td>
		            		创建活动<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/CreateEvent<br/>
							[Method]: POST<br/>
							[Args]:  title=[活动题目]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &gender=[性别要求 男=1 女=2 不限=0]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &userLevel=[用户等级要求  黑卡=4 金卡=3 银卡=2 普通=1]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &dateTime=[活动日期时间 2015-2-2 12:33:11]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &payType=[收费类型  请客=1 AA=2]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &address=[活动地址]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &latitude=[纬度]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &longitude=[经度]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &coins=[支付的友币数量]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &des=[活动具体文字描述]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &memberLimit=[人数限制]<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &needAccept=[是否需要发起人验证]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
            			<td>
            				通用对方参加活动<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/AcceptJoinEvent<br/>
							[Method]: POST<br/>
							[Args]:  eid=[活动ID]&uid=[对方的UID]<br/>
            			</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		获取活动信息<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/EventInfo<br/>
							[Method]: GET<br/>
							[Args]:  eid=[活动ID]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		参加活动<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/JoinEvent<br/>
							[Method]: POST<br/>
							[Args]:  eid=[活动ID]&act=[0=加入活动 1=退出活动]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		举报活动<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/ReportEvent<br/>
							[Method]: POST<br/>
							[Args]:  eid=[活动ID]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		查看活动<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/ViewEvent<br/>
							[Method]: GET<br/>
							[Args]:  eid=[活动ID]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		签到活动<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/SignInEvent<br/>
							[Method]: POST<br/>
							[Args]:  eid=[活动ID]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		活动成员列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/EventMemberList<br/>
							[Method]: GET<br/>
							[Args]:  eid=[活动ID]&page=[页号]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		活动列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/EventList<br/>
							[Method]: GET<br/>
							[Args]: type=[列表类型  1=热门 2=附近 3=好友 4=我的活动 5=我参加的活动]&
									page=[页数]&
									latitude=[纬度 当类型=2时需要]&
									longitude=[经度 当类型=2时需要]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		活动列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/EventListByUID<br/>
							[Method]: GET<br/>
							[Args]: type=[列表类型  1=发起的活动 2=参加的活动]&
									page=[页数]&uid=[UID]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		当前活动排行  用于计算我发起的活动预计排行数<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/EventTopList<br/>
							[Method]: GET<br/>
							[Args]: <br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		活动搜索<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/EventSearch<br/>
							[Method]: GET<br/>
							[Args]:  page=[页数]&
								     title=[题目]&
									 Date=[日期 2015-2-12]&
									 address=[地点名称]&
									 payType=[支付类型 请客=1 AA=2]&
									 userLevel=[用户等级]&
									 gender=[性别 1=男  2=女 0=空]&
									 <br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		添加活动评论<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/AddEventTopic<br/>
							[Method]: POST<br/>
							[Args]: content=[内容]&eid=[活动ID]<br/>
		            	</td>
            		</tr>
            		
            		<tr>
		            	<td>
		            		活动评论列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Events/EventTopicList<br/>
							[Method]: GET<br/>
							[Args]: page=[页数]&eid=[活动ID]<br/>
		            	</td>
            		</tr>
            		
            	</tbody>
            	</table>
            </div>
           
           <div id="tab4" class="tab-pane">
            	<table class="table table-bordered table-striped">
	            <tbody>
	            	<tr>
		            	<td>
		            		同步手机通讯录的用户<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/SyncContactUsers<br/>
							[Method]: GET<br/>
							[Args]: phoneNumbers=[手机号列表 用逗号分割多个 如 13910232123,15022335102,....]<br/>
		            	</td>
		            </tr>
		            
		            <tr>
		            	<td>
		            		获取好友列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/BuddyList<br/>
							[Method]: GET<br/>
							[Args]: 无<br/>
		            	</td>
		            </tr>
		            
		            <tr>
		            	<td>
		            		添加好友<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/AddBuddy<br/>
							[Method]: POST<br/>
							[Args]: buddyId=[对方的ID]&buddyHxId=[对方的环信用户名]<br/>
		            	</td>
		            </tr>
		            
		            <tr>
		            	<td>
		            		获取请求加入好友列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/BuddyRequestList<br/>
							[Method]: GET<br/>
							[Args]:<br/>
		            	</td>
		            </tr>
		            
		            <tr>
		            	<td>
		            		获取请求加入好友的数量<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/BuddyRequestListCount<br/>
							[Method]: GET<br/>
							[Args]:<br/>
		            	</td>
		            </tr>
		            
		             <tr>
		            	<td>
		            		通过添加好友请求<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/AllowAddBuddy<br/>
							[Method]: POST<br/>
							[Args]: BR_ID=[好友请求ID]<br/>
		            	</td>
		            </tr>
		            
		              <tr>
		            	<td>
		            		拒绝加为好友请求<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/RejectAddBuddy<br/>
							[Method]: POST<br/>
							[Args]: BR_ID=[好友请求ID]<br/>
		            	</td>
		            </tr>
		            
		             <tr>
		            	<td>
		            		移除好友<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/RemoveBuddy<br/>
							[Method]: POST<br/>
							[Args]: buddyId=[对方的ID]&buddyHxId=[对方的环信用户名]<br/>
		            	</td>
		            </tr>
		            
		             <tr>
		            	<td>
		            		根据环信聊天组ID获取用户列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Contact/UserListFromChatGroup<br/>
							[Method]: GET<br/>
							[Args]: chatGroup=[环信聊天组ID]<br/>
		            	</td>
		            </tr>
		            
	            </tbody>
	            </table>
	       </div>
           
            <div id="tab5" class="tab-pane">
            	<table class="table table-bordered table-striped">
	            <tbody>
	            	<tr>
		            	<td>
		            		用户每日签到<br/>
		            		[Auth]:token <br/>
							[URL]: /UserSetting/SignInDay<br/>
							[Method]: POST<br/>
							[Args]: <br/>
		            	</td>
		            </tr>
		            
	            	<tr>
		            	<td>
		            		好友验证设定<br/>
		            		[Auth]:token <br/>
							[URL]: /UserSetting/BuddyVerify<br/>
							[Method]: POST<br/>
							[Args]: verify=[0-不需要验证   &nbsp; 1-需要验证]<br/>
		            	</td>
		            </tr>
		            
		            <tr>
		            	<td>
		            		获取二维码字符串<br/>
		            		[Auth]:token <br/>
							[URL]: /UserSetting/MyCode<br/>
							[Method]: GET<br/>
							[Args]: <br/>
		            	</td>
		            </tr>
		            
		            </tbody>
		            </table>
		    </div>
		    
		    <div id="tab6" class="tab-pane">
            	<table class="table table-bordered table-striped">
	            <tbody>
	            	<tr>
		            	<td>
		            		获取Ping++ Charge<br/>
		            		[Auth]:token <br/>
							[URL]: /Order/CreateCharge<br/>
							[Method]: POST<br/>
							[Args]:pId=[商品的ID] <br/>
		            	</td>
		            </tr>
		            
		            <tr>
		            	<td>
		            		获取商品列表<br/>
		            		[Auth]:token <br/>
							[URL]: /Order/GetPurchaseList<br/>
							[Method]: GET<br/>
							[Args]: <br/>
		            	</td>
		            </tr>
		            
		             <tr>
		            	<td>
		            		生成订单信息<br/>
		            		[Auth]:token <br/>
							[URL]: /Order/CreateOrder<br/>
							[Method]: POST<br/>
							[Args]:pid=[商品的ID]&src=[字符串 iOS/Android]<br/>
		            	</td>
		            </tr>
		            
		            <tr>
		            	<td>
		            		验证交易并完成订单<br/>
		            		[Auth]:token <br/>
							[URL]: /Order/VerifyTrade<br/>
							[Method]: POST<br/>
							[Args]:oid=[订单ID]&data=[交易凭据字符串]<br/>
		            	</td>
		            </tr>
		            
		            </tbody>
		            </table>
		    </div>
		    
          </div>
					
	</div>
</div>

</div>
</div>

<script src="js/jquery.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/jquery.ui.custom.js"></script> 
<script src="js/matrix.js"></script>

</body>
</html>