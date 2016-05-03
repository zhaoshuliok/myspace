<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid控件使用</title>
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
<body>
	<!-- 案例一 ：HTML静态数据，应用数据表格 -->
	<!-- 第一步： 添加  class="easyui-datagrid" -->
	<table class="easyui-datagrid">
		<!-- 第三步： 使用<thead>标识 表头，<tbody> 标识表格数据  -->
		<thead>
		<!-- 第二步：表头每列指定 field属性 -->
			<tr>
				<th data-options="field:'id',checkbox:true">编号</th>
				<th data-options="field:'name',width:200">名称</th>
				<th data-options="field:'price'">价格</th>
			</tr>
		</thead>	
		<tbody>
			<tr>
				<td>100</td>
				<td>手机</td>
				<td>1999</td>
			</tr>
			<tr>
				<td>200</td>
				<td>电脑</td>
				<td>3999</td>
			</tr>
		</tbody>
	</table>
</body>
</html>