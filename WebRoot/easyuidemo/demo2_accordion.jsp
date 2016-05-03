<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用 accordion 插件，完成系统菜单分级 </title>
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
</head>
<!-- 1、 对body 应用页面布局 -->
<body class="easyui-layout">
	<!-- 2、通过 data-options 的 region属性，指定div哪个部分 -->
	<div data-options="region:'north',title:'国家电力设备资源管理系统'" style="height: 100px">北部</div>
	<div data-options="region:'west',title:'系统菜单'" style="width: 150px">
		<!-- 多功能菜单  折叠效果-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个div 就是一个菜单 -->
			<div data-options="title:'基本菜单',iconCls:'icon-save'">菜单一</div>
			<div data-options="title:'管理员菜单'">菜单二</div>
			<div data-options="title:'控制面板'">菜单三</div>
		</div>
	</div>
	<div data-options="region:'center'">中部</div>
	<div data-options="region:'south'" style="height: 80px">南部</div>
</body>
</html>