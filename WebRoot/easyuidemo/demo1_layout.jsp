<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用 layout 插件，完成页面布局 </title>
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
	<div data-options="region:'west',title:'系统菜单',split:true" style="width: 150px">西部</div>
	<div data-options="region:'center'">中部</div>
<%--	<div data-options="region:'east'" style="width: 100px">东部</div>--%>
	<div data-options="region:'south'" style="height: 80px">南部</div>
</body>
</html>