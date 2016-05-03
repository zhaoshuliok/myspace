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
	<!-- 案例二：：HTML定义表格，加载远程json数据 -->
	<!-- 第一步： 添加  class="easyui-datagrid" -->
	<!--  pagination:true表示显示分页，rownumbers:true显示行号,toolbar:'#menu'显示按钮-->
	<table class="easyui-datagrid" 
		data-options="url:'data2.json',pagination:true, rownumbers:true,toolbar:'#menu'">
		<!-- 第三步： 使用<thead>标识 表头，<tbody> 标识表格数据  -->
		<thead>
		<!-- 第二步：表头每列指定 field属性 -->
			<tr>
				<th data-options="field:'id',checkbox:true">编号</th>
				<th data-options="field:'name',width:200">名称</th>
				<th data-options="field:'price'">价格</th>
			</tr>
		</thead>	
	</table>
	
	<div id="menu">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
	</div>
</body>
</html>