<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部署管理</title>
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
	//自定义 easyui form 校验器 ，验证上传的文件是zip格式
	$.extend($.fn.validatebox.defaults.rules, {
		filevalidate : {
			validator : function(value, param) {
				var regexp = /^.*\.zip$/;
				return regexp.test(value);
			},
			message : '部署流程定义需要指定zip的格式'
		}
	});
	
	//删除
	function doDelete(){
		// 获取页面中 选择所有数据id
		var rows = $("#deploymentGrid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能删除一行数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var id = rows[0].id;
		//alert(ids);
		// 发送给服务器 
		location.href = "${pageContext.request.contextPath}/workflow/elecWorkflowAction_delDeployment.do?deploymentId="+id;
	}
	
	//查看流程图
	function doViewPhoto(){
		var rows = $("#processDefinitionGrid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能选择查看一行数据","warning");
			return ;
		}
		//部署对象ID
		var id = rows[0].deploymentId;
		//资源图片名称
		var name = rows[0].diagramResourceName;
		openWindow('${pageContext.request.contextPath }/workflow/elecWorkflowAction_viewImage.do?deploymentId='+id+'&imageName='+name+'','800','350');
	}
	
	//部署流程定义
	function doImport(){
		// 部署流程定义
		$('#addDeploymentWindow').window("open");
		/* $('#addDeploymentWindow').window({
	        title: '部署流程定义',
	        width: 1600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 800,
	        resizable:false
	    }); */
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入部署流程图',
		iconCls : 'icon-redo',
		handler : doImport
	}];
	
	//工具栏
	var pdtoolbar = [{
		id : 'button-view',
		text : '查看流程图',
		iconCls : 'icon-search',
		handler : doViewPhoto
	}];
	// 部署对象：定义列
	var columns = [ [ {
		field : 'id',
		title : '部署ID',
		width : 120,
		align : 'center',
		checkbox : true
	},{
		field : 'name',
		title : '部署流程名称',
		width : 120,
		align : 'center'
	}, {
		field : 'deploymentTime',
		title : '部署时间',
		width : 120,
		align : 'center'
	}
	] ];
	
	// 流程定义：定义列
	var pdcolumns = [ [ {
		field : 'id',
		title : '流程定义ID',
		width : 120,
		align : 'center',
		checkbox : true
	},{
		field : 'name',
		title : '流程定义名称',
		width : 120,
		align : 'center'
	}, {
		field : 'key',
		title : '流程定义的key',
		width : 120,
		align : 'center'
	}, {
		field : 'version',
		title : '流程定义的版本',
		width : 120,
		align : 'center'
	}, {
		field : 'resourceName',
		title : '流程定义的规则文件名称',
		width : 160,
		align : 'center'
	}, {
		field : 'diagramResourceName',
		title : '流程定义的规则图片名称',
		width : 160,
		align : 'center'
	}, {
		field : 'deploymentId',
		title : '部署对象ID',
		width : 120,
		align : 'center'
	}
	] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 部署对象列表表格
		$('#deploymentGrid').datagrid( {
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			//pageList: [30,50,100],
			//pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/workflow/elecWorkflowAction_findDeploymentList.do",
			//url : "${pageContext.request.contextPath}/json/deployment.json",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 流程定义列表表格
		$('#processDefinitionGrid').datagrid( {
			iconCls : 'icon-forward',
			//fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			//pageList: [30,50,100],
			//pagination : true,
			toolbar : pdtoolbar,
			url : "${pageContext.request.contextPath}/workflow/elecWorkflowAction_findProcessDefinitionList.do",
			//url : "${pageContext.request.contextPath}/json/processDefinition.json",
			idField : 'id',
			columns : pdcolumns,
			onDblClickRow : doDblClickRow
		});
		
		//部署流程定义窗口
		$('#addDeploymentWindow').window({
		        title: '部署流程定义',
		        width: 800,
		        modal: true,
		        shadow: true,
		        closed: true,
		        height: 400,
		        resizable:false
		}); 
		
		// 为保存按钮，添加点击事件
		$("#save").click(function() {
			// 手动校验
			/*
			var filename = $("input[filename='filename']").val();
			if(filename.trim()==""){
				$.messager.alert("警告","文件名不能为空","warning");
			}
			 */

			// 提交form 
			if ($("#deploymentForm").form('validate')) {
				// 当form 所有字段 都通过校验 提交form表单
				$("#deploymentForm").submit();
			}
		});
		
	});

	function doDblClickRow(){
		alert("双击表格数据...");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="deploymentGrid"></table>
    	<br>
    	<table id="processDefinitionGrid"></table>
	</div>
	
	<!-- 部署流程定义 -->
	<div class="easyui-window" title="部署流程定义" id="addDeploymentWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >部署流程定义</a>
			</div>
		</div>
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="deploymentForm" action="${pageContext.request.contextPath }/workflow/elecWorkflowAction_newdeploy.do"  enctype="multipart/form-data" method="POST">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">部署流程定义</td>
					</tr>
					<tr>
						<td>流程名称</td>
						<td><input type="text" name="filename" style="width: 200px;" class="easyui-validatebox" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>流程文件</td>
						<td>
							<input type="file" name="file" style="width: 200px;" class="easyui-validatebox" data-options="required:true,validType:'filevalidate'" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>