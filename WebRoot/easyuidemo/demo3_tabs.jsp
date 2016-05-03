<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用 tabs 插件，完成系统选项卡主面板 </title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/jquery.easyui.min.js"></script>
<!-- 导入easyui类库 css -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/icon.css">
<script type="text/javascript">
	$(function(){
		// 对菜单添加 click事件
		$(".mymenu").click(function(){
			// 执行 选项卡控件 add 方法
			$("#aa").tabs('add',{
				title: $(this).html(),
				closable:true,
				tools:[{ 

					iconCls:'icon-mini-refresh', 

					handler : function(){ 

						alert('refresh'); 

					} 
				}] 

			});
		});
	});
</script>	
</head>
<!-- 1、 对body 应用页面布局 -->
<body class="easyui-layout">
	<!-- 2、通过 data-options 的 region属性，指定div哪个部分 -->
	<div data-options="region:'north',title:'国家电力设备资源管理系统'" style="height: 100px">北部</div>
	<div data-options="region:'west',title:'系统菜单'" style="width: 150px">
		<!-- 多功能菜单  折叠效果-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个div 就是一个菜单 -->
			<div data-options="title:'基本菜单'">
				<a class="mymenu">菜单一</a>
			</div>
			<div data-options="title:'管理员菜单'">
				<a class="mymenu">菜单二</a>
			</div>
			<div data-options="title:'控制面板'">菜单三</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 提供选项卡菜单 -->
		<div id="aa" class="easyui-tabs" data-options="fit:true">
			<!-- 每个div 都是一个选项卡 -->
			<div data-options="title:'选项卡一',closable:true">选项卡一</div> 
			<div data-options="title:'选项卡二'">选项卡二</div>
			<div data-options="title:'选项卡三',selected:true">选项卡三</div>
			<div data-options="title:'选项卡四'">选项卡四</div>
		</div>
	</div>
	<div data-options="region:'south'" style="height: 80px">南部</div>
</body>
</html>