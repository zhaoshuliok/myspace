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
<script type="text/javascript">
	$(function(){
		// 通过js代码 定义数据表格
		$("#grid").datagrid({
			// 定义表头
			columns : [[ // 二维数组，每个数组就是一行，可以定义复杂表头
				{
					title: '编号',
					field : 'id',
					width : 100,
					align : 'center',
					checkbox :true
				},
				{
					title: '名称',
					field : 'name',
					width : 200,
					align : 'center'
				},
				{
					title: '价格',
					field : 'price',
					width : 200,
					align: 'center'
				}
			]],
			url : 'data.json',
			pagination : true,
			rownumbers : true,
			toolbar: [ // 可以定义很多按钮
				 {
					id: "save",
					text: "保存",
					iconCls : 'icon-save',
					handler : function(){
						alert("保存");
					}
				 },
				 {
					id: "delete",
					text: "删除",
					iconCls : 'icon-remove',
					handler : function(){
						alert("删除");
					}
				  }
			]
		});
	});
</script>	
</head>
<body>
	<!-- 案例三： 使用JS定义数据表格 -->
	<table id="grid"></table>
</body>
</html>