<%@ page language="java" pageEncoding="UTF-8"%>


<HTML>
<HEAD>
<TITLE>国家电力监测中心首页</TITLE>
<LINK href="/css/Font.css" type="text/css" rel="stylesheet">
<STYLE>BODY {
	SCROLLBAR-ARROW-COLOR: #ffffff; SCROLLBAR-BASE-COLOR: #dee3f7
}
</STYLE>
<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
<script
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/icon.css">
	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/jquery.easyui.min.js"></script>
<!-- 导入ztree类库 -->
<script language="JavaScript" src="${pageContext.request.contextPath }/script/jquery.ztree.all-3.5.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/menu.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zTreeStyle/zTreeStyle.css" type="text/css">



<script type="text/javascript">
	// 初始化ztree菜单
	$(function() {
		var setting = {
			data : {
				simpleData : { // 简单数据 
					enable : true,
					idKey: "id",
	                pIdKey: "pid"
				}
			},
			callback : {
				onClick : onClick
			}
		};
		
		// 基本功能菜单加载
		$.ajax({
			//url : '${pageContext.request.contextPath}/json/menu.json',
			url : '${pageContext.request.contextPath}/system/elecMenuAction_showMenu.do',
			type : 'POST',
			dataType : 'text',
			data : {"generatemenu":"basic"},//传递参数
			success : function(data) {
				//alert(data);
				var zNodes = eval("(" + data + ")");
				//alert(zNodes);
				$.fn.zTree.init($("#treeMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 系统管理菜单加载
		$.ajax({
			//url : '${pageContext.request.contextPath}/json/admin.json',
			url : '${pageContext.request.contextPath}/system/elecMenuAction_showMenu.do',
			type : 'POST',
			dataType : 'text',
			data : {"generatemenu":"system"},
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#adminMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 流程管理菜单加载
		$.ajax({
			//url : '${pageContext.request.contextPath}/json/workflow.json',
			url : '${pageContext.request.contextPath}/system/elecMenuAction_showMenu.do',
			type : 'POST',
			dataType : 'text',
			data : {"generatemenu":"workflow"},
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#workflowMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 页面加载后 右下角 弹出窗口
		/**************/
		window.setTimeout(function(){//定时器
			$.messager.show({
				title:"消息提示",
				msg:'欢迎登录，传智武松！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
				timeout:5000//停留5秒
			});
		},3000);//3秒后显示
		/*************/
		
		// 修改密码取消按钮 -- 关闭修改密码窗口 
		$("#btnCancel").click(function(){
			$('#editPwdWindow').window('close');
		});
		
		// 修改密码确定按钮 -- 判断两次密码是否一致，如果一致 ajax请求
		$("#btnEp").click(function(){
			var logonPwd = $("#logonPwd").val();
			var logonPwdRepeat = $("#logonPwdRepeat").val();
			// 判断是否一致
			if($.trim(logonPwd)==""){
				// 密码为空
				$.messager.alert("警告","密码不能为空！","warning");
				return ;
			}
			if($.trim(logonPwd)!=$.trim(logonPwdRepeat)){
				// 两次密码 输入不一致
				$.messager.alert("警告","两次密码输入不一致","warning");
				return ;
			}
			// ajax 提交请求
			$.post("${pageContext.request.contextPath}/system/elecUserAction_editPassword.do", {"logonPwd":logonPwd}, function(data){
				// 回调函数
				if(data.result == "success"){
					// 成功
					$.messager.alert("信息", data.msg , "info");
				}else{
					// 失败
					$.messager.alert("失败", data.msg , "error");
				}
				// 关闭修改密码窗口
				$('#editPwdWindow').window('close');
			});
		});
	});

	//使用ztree树型菜单的选择，是否加载选项卡
	function onClick(event, treeId, treeNode, clickFlag) {
		// 判断树菜单节点是否含有 page属性
		if (treeNode.page!=undefined && treeNode.page!= "") {
			if ($("#tabs").tabs('exists', treeNode.name)) {// 判断tab是否存在
				$('#tabs').tabs('select', treeNode.name); // 切换tab
			} else {
				// 开启一个新的tab页面
				var content = '<div style="width:100%;height:100%;overflow:hidden;">'
						+ '<iframe src="'
						+ treeNode.page
						+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

				$('#tabs').tabs('add', {
					title : treeNode.name,
					content : content,
					closable : true
				});
			}
		}
	}

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	 /**
	 <link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'
				+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		$.messager.confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
			if (isConfirm) {
				//清空Session
				location.href = '${pageContext.request.contextPath }/system/elecMenuAction_logout.do';
			}
		});
	}
	// 返回首页
	function loadingFun() {
		window.location.reload();//刷新当前页面
	}
	// 修改密码
	function editPassword() {
		$('#editPwdWindow').window('open');
	}
	// 版权信息/联系管理员
	function showAbout(){
		$.messager.alert("国家电网公司设备资源管理系统 v1.0","设计: 传智.武松<br/> 管理员邮箱: wusong@itcast.cn <br/> QQ: 12345678");
	}
</script>

</HEAD>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false" style="height:58;padding:0px;">
			<div>
				<img src="${pageContext.request.contextPath }/images/title.jpg"
					border="0">
			</div>
			<div id="sessionInfoDiv"
				style="position: absolute;right: 75px;top:10px;">
				[<strong>${session.globle_user.userName }</strong>]，
				欢迎你！您使用[<strong>${pageContext.request.remoteAddr }</strong>]IP登录！<br>
				<FONT color=#000000>
            	<b>
            		<SCRIPT language=JavaScript>
						<!--
						tmpDate = new Date();
						date = tmpDate.getDate();
						month= tmpDate.getMonth() + 1 ;
						year= tmpDate.getFullYear();
						document.write(year);
						document.write("年");
						document.write(month);
						document.write("月");
						document.write(date);
						document.write("日 ");

						myArray=new Array(6);
						myArray[0]="星期日"
						myArray[1]="星期一"
						myArray[2]="星期二"
						myArray[3]="星期三"
						myArray[4]="星期四"
						myArray[5]="星期五"
						myArray[6]="星期六"
						weekday=tmpDate.getDay();
						if (weekday==0 | weekday==6)
						{
						document.write(myArray[weekday])
						}
						else
						{document.write(myArray[weekday])
						};
						// -->
				</SCRIPT>
            </b>
            </FONT>
			</div>
			<div style="position: absolute; right: 5px; bottom: 10px; ">
				<a href="javascript:void(0);" class="easyui-menubutton"
					data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
				<a href="javascript:void(0);" class="easyui-menubutton"
					data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
			</div>
			<div id="layout_north_pfMenu" style="width: 120px; display: none;">
				<div onclick="changeTheme('default');">default</div>
				<div onclick="changeTheme('gray');">gray</div>
				<div onclick="changeTheme('black');">black</div>
				<div onclick="changeTheme('bootstrap');">bootstrap</div>
				<div onclick="changeTheme('metro');">metro</div>
			</div>
			<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
				<div data-options="iconCls:'icon-edit'" onclick="editPassword();">修改密码</div>
				<div data-options="iconCls:'icon-help'" onclick="showAbout();">联系管理员</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-reload'" onclick="loadingFun();">返回首页</div>
				<div data-options="iconCls:'icon-back'" onclick="logoutFun();">退出系统</div>
			</div>
		</div>
		<div data-options="region:'west',split:true,title:'菜单导航'"
			style="width:200px">
			<div class="easyui-accordion" fit="true" border="false">
				<div title="基本功能" data-options="iconCls:'icon-mini-add'" style="overflow:auto">
					<ul id="treeMenu" class="ztree"></ul>
				</div>
				<div title="系统管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
					<ul id="adminMenu" class="ztree"></ul>
				</div>
				<div title="流程管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
					<ul id="workflowMenu" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="tabs" fit="true" class="easyui-tabs" border="false">
				<div title="消息中心" id="subWarp"
					style="width:100%;height:100%;overflow:hidden">
					<iframe src="${pageContext.request.contextPath }/system/elecMenuAction_loading.do"
						style="width:100%;height:100%;border:0;"></iframe>
					<%--				这里显示站点运行情况、设备运行情况和代办事宜等功能--%>
				</div>
			</div>
		</div>
		<div data-options="region:'south',border:false"
			style="height:50px;padding:10px;background:url('${pageContext.request.contextPath }/images/bottom_new.jpg') no-repeat right;">
			<table style="width: 100%;">
				<tbody>
					<tr>
						<td style="width: 300px;">
							<div style="color: #999; font-size: 8pt;">
								传智播客 | Powered by <a href="http://www.itcast.cn/">itcast.cn</a>
							</div>
						</td>
						<td style="width: *;" class="co1"><span id="online"
							style="background: url(${pageContext.request.contextPath }/images/online.png) no-repeat left;padding-left:18px;margin-left:3px;font-size:8pt;color:#005590;">在线人数:1</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<!--修改密码窗口-->
	    <div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
	        maximizable="false" icon="icon-save"  style="width: 300px; height: 160px; padding: 5px;
	        background: #fafafa">
	        <div class="easyui-layout" fit="true">
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                <table cellpadding=3>
	                    <tr>
	                        <td>新密码：</td>
	                        <td><input id="logonPwd" type="Password" /></td>
	                    </tr>
	                    <tr>
	                        <td>确认密码：</td>
	                        <td><input id="logonPwdRepeat" type="Password" /></td>
	                    </tr>
	                </table>
	            </div>
	            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
	                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
	                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
	            </div>
	        </div>
	    </div>
	</body>
</HTML>
