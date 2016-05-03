<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/icon.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/Style.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
	//查看当前流程图
	function doView() {
		//$('#addTaskWindow').window("open");
		var rows = $("#grid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			//选中一行 
			$.messager.alert("警告","一次只能选择一行查看当前流程图","warning");
			return ;
		}
		//任务ID
		var id = rows[0].id;
		//alert(id);
		openWindow('${pageContext.request.contextPath }/workflow/elecWorkflowAction_viewCurrentImage.do?taskId='+id,'800','550');
	}

	//办理任务
	function doTask() {
		//$('#addTaskWindow').window("open");
		var rows = $("#grid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			//选中一行 
			$.messager.alert("警告","一次只能选择一行数据办理任务","warning");
			return ;
		}
		//任务ID
		var id = rows[0].id;
		//alert(id);
		location.href = "${pageContext.request.contextPath}/workflow/elecWorkflowAction_audit.do?taskId="+id;
		//location.href = "${pageContext.request.contextPath}/workflow/taskForm.jsp?taskId="+id;
	}
	
	//回退组任务
	function doGroupTask() {
		//$('#addTaskWindow').window("open");
		var rows = $("#grid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			//选中一行 
			$.messager.alert("警告","一次只能选择一行数据回退到组任务","warning");
			return ;
		}
		//任务ID
		var id = rows[0].id;
		//alert(id);
		location.href = "${pageContext.request.contextPath}/workflow/elecWorkflowAction_returnGroupTask.do?taskId="+id;
	}


	
	
	
	
	//工具栏
	var toolbar = [ {
		id : 'button-task',
		text : '办理任务',
		iconCls : 'icon-ok',
		handler : doTask
	}, 
	{
		id : 'button-task',
		text : '回退组任务',
		iconCls : 'icon-back',
		handler : doGroupTask
	}, 
	{
		id : 'button-view',
		text : '查看当前流程图',
		iconCls : 'icon-search',
		handler : doView
	} ];
	
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true
	}, {
		field : 'name',
		title : '任务名称',
		width : 120,
		align : 'center'
	},{
		field : 'createTime',
		title : '创建时间',
		width : 120,
		align : 'center'
	},{
		field : 'assignee',
		title : '办理人',
		width : 120,
		align : 'center'
	},{
		field : 'processDefinitionId',
		title : '流程定义ID',
		width : 120,
		align : 'center'
	},{
		field : 'processInstanceId',
		title : '流程实例ID',
		width : 120,
		align : 'center'
	},{
		field : 'executionId',
		title : '执行对象ID',
		width : 120,
		align : 'center'
	},{
		field : 'devName',
		title : '设备购置名称',
		width : 120,
		align : 'center'
	},{
		field : 'quality',
		title : '数量',
		width : 120,
		align : 'center'
	},{
		field : 'useUnit',
		title : '使用单位',
		width : 120,
		align : 'center'
	},{
		field : 'devExpense',
		title : '金额',
		width : 120,
		align : 'center'
	},{
		field : 'planDate',
		title : '计划时间',
		width : 120,
		align : 'center'
	}] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});
		

		// 个人任务信息表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList : [ 2, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/workflow/elecWorkflowAction_listTask.do",
			//url : "${pageContext.request.contextPath}/json/listTask.json",
			idField : 'id',
			columns : columns
		});

		
	});
	

	
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	

	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	
	
</body>
</html>
