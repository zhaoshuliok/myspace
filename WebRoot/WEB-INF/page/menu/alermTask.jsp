<%@ page language="java" pageEncoding="UTF-8"%>

<script
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">


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
		openWindow('${pageContext.request.contextPath }/workflow/elecWorkflowAction_viewCurrentImage.do?taskId='+id,'800','550');
	}

	function doTask() {
		//$('#addTaskWindow').window("open");
		var rows = $("#grid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			//选中一行 
			$.messager.alert("警告","一次只能选择一行数据拾取任务","warning");
			return ;
		}
		//任务ID
		var id = rows[0].id;
		//alert(id);
		location.href = "${pageContext.request.contextPath}/workflow/elecWorkflowAction_claimTask.do?taskId="+id;
	}


	
	
	
	
	//工具栏
	var toolbar = [ {
		id : 'button-task',
		text : '拾取任务',
		iconCls : 'icon-ok',
		handler : doTask
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
		// 设备购置计划信息表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList : [ 2, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			//url : "${pageContext.request.contextPath}/json/listGroupTask.json",
			url : "${pageContext.request.contextPath}/workflow/elecWorkflowAction_listGroupTask.do",
			idField : 'id',
			columns : columns
		});

		
	});
	

	
</script>
<div style="padding:5px;">
	<table id="grid" class="easyui-datagrid"
		style="height:auto;overflow:auto">
		<thead>
			<tr>
				<th data-options="field:'name'">任务名称</th>
				<th data-options="field:'createTime'">创建时间</th>
				<th data-options="field:'assignee'">办理人</th>
				<th data-options="field:'processDefinitionId'">流程定义ID</th>
				<th data-options="field:'processInstanceId'">流程实例ID</th>
				<th data-options="field:'executionId'">执行对象ID</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>