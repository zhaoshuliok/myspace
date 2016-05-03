<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/Style.css">
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/icon.css">

<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>


<script type="text/javascript">
	//将form 转换为json
	/**
	$.fn是指jquery的命名空间，加上fn上的方法及属性，会对jquery实例每一个有效。
		如扩展$.fn.serializeJson(),即$.fn.serializeJson()是对jquery扩展了一个serializeJson方法,
		那么后面你的每一个jquery实例都可以引用这个方法了.
		那么可以这样子使用：$("#div").serializeJson(); 
	*/
	$.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray(); 
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
	}; 

	// 自定义 easyui form 校验器 ，可以使用正则表达式
	$.extend($.fn.validatebox.defaults.rules, {
		telephone : {
			validator : function(value, param) {
				var regexp = /^1[3|4|5|8]\d{9}$/ ;
				return regexp.test(value);
			},
			message : '规格型号必须为11位数字'
		}
	});
	
	//新增设备购置计划
	function doAdd() {
		$('#addDeviceWindow').form("clear");
		//alert("增加...");
		$('#addDeviceWindow').window("open");
	}


	// 删除
	function doDelete() {
		// 获取页面中 选择所有数据id
		var rows = $("#grid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length == 0){
			// 一行没选中 
			$.messager.alert("警告","删除功能至少选中一行数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var array = new Array();
		for(var i=0; i<rows.length; i++){
			array.push(rows[i].devPlanId);
		}
		var ids = array.join(",");
		//alert(ids);
		// 发送给服务器 
		location.href = "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_delete.do?devPlanId="+ids;
	}

	//还原设备购置计划
	function doRevert() {
		// 获取页面中 选择所有数据id
		var rows = $("#grid").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length == 0){
			// 一行没选中 
			$.messager.alert("警告","删除功能至少选中一行数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var array = new Array();
		for(var i=0; i<rows.length; i++){
			array.push(rows[i].devPlanId);
		}
		var ids = array.join(",");
		//alert(ids);
		// 发送给服务器 
		location.href = "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_revert.do?devPlanId="+ids;
	}
	
	//报审
	function doCheck() {
		// 获取页面中 选择所有数据id
		var rows = $("#noCheck").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能报审一条数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var id = rows[0].devPlanId;
		//赋值购置计划ID，报审的时候需要
		$("#checkPlanId").val(id);
		$('#checkDeviceWindow').window("open");
	}
	
	//计划顺延
	function doPlan() {
		// 获取页面中 选择所有数据id
		var rows = $("#noCheck").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能选择一条数据执行顺延","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var id = rows[0].devPlanId;
		//赋值购置计划ID，报审的时候需要
		location.href = "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_updatePlanTime.do?devPlanId="+id;
	}
	
	//购置
	function doBuy() {
		// 获取页面中 选择所有数据id
		var rows = $("#checkPass").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能查看一条数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var id = rows[0].devPlanId;
		//赋值购置计划ID，报审的时候需要
		location.href = "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_buyDevice.do?devPlanId="+id;
	}
	
	//审核中（查看流程审核历史记录）
	function doCheckState() {
		// 获取页面中 选择所有数据id
		var rows = $("#checking").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能查看一条数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var id = rows[0].devPlanId;
		//赋值购置计划ID，报审的时候需要
		location.href = "${pageContext.request.contextPath}/workflow/elecWorkflowAction_viewHisComment.do?devPlanId="+id;
		//location.href = "${pageContext.request.contextPath}/workflow/taskFormHis.jsp?devPlanId="+id;
	}
	
	//审核通过（查看流程审核历史记录）
	function doCheckPassState() {
		// 获取页面中 选择所有数据id
		var rows = $("#checkPass").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能查看一条数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var id = rows[0].devPlanId;
		//赋值购置计划ID，报审的时候需要
		location.href = "${pageContext.request.contextPath}/workflow/elecWorkflowAction_viewHisComment.do?devPlanId="+id;
		//location.href = "${pageContext.request.contextPath}/workflow/taskFormHis.jsp?devPlanId="+id;
	}
	
	//审核不通过（查看流程审核历史记录）
	function doCheckNoPassState() {
		// 获取页面中 选择所有数据id
		var rows = $("#checkNoPass").datagrid('getSelections');
		// 判断是否选中 
		if(rows.length != 1){
			// 一行没选中 
			$.messager.alert("警告","一次只能查看一条数据","warning");
			return ;
		}
		// 选中数据， 获取这些数据id
		var id = rows[0].devPlanId;
		//赋值购置计划ID，报审的时候需要
		location.href = "${pageContext.request.contextPath}/workflow/elecWorkflowAction_viewHisComment.do?devPlanId="+id;
		//location.href = "${pageContext.request.contextPath}/workflow/taskFormHis.jsp?devPlanId="+id;
	}
	
	
	//工具栏
	var toolbar = [ 
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, 
	{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRevert
	} ];
	//未审核工具栏
	//工具栏
	var nochecktoolbar = [ {
		id : 'button-check',
		text : '报审',
		iconCls : 'icon-ok',
		handler : doCheck
	},
	{
		id : 'button-plan',
		text : '计划顺延',
		iconCls : 'icon-remove',
		handler : doPlan
	}];
	//审核中工具栏
	//工具栏
	var checkingtoolbar = [ {
		id : 'button-check',
		text : '查看流程审核状态',
		iconCls : 'icon-ok',
		handler : doCheckState
	} ];
	//审核通过工具栏
	//工具栏
	var checkPasstoolbar = [ {
		id : 'button-check',
		text : '查看流程审核状态',
		iconCls : 'icon-ok',
		handler : doCheckPassState
	},{
		id : 'button-check',
		text : '购置',
		iconCls : 'icon-edit',
		handler : doBuy
	} ];
	
	//审核不通过工具栏
	//工具栏
	var checkNoPasstoolbar = [ {
		id : 'button-check',
		text : '查看流程审核状态',
		iconCls : 'icon-ok',
		handler : doCheckNoPassState
	}];
	
	
	// 定义列（首页）
	var columns = [ [ {
		field : 'devPlanId',
		checkbox : true,
		rowspan : 2//合并单元格（合并2行）
	}, {
		field : 'devName',
		title : '设备名称',
		width : 120,
		align : 'center',
		rowspan : 2
	}, {
		title : '购买数量单位',
		colspan : 2//合并单元格（合并2行）
	}, {
		field : 'devExpense',
		title : '金额',
		width : 120,
		align : 'center',
		rowspan : 2
	},{
		field : 'specType',
		title : '规格型号',
		width : 120,
		align : 'center',
		rowspan : 2
	},{
		field : 'useness',
		title : '用途',
		width : 120,
		align : 'center',
		rowspan : 2
	}, {
		field : 'useUnit',
		title : '使用单位',
		width : 120,
		align : 'center',
		rowspan : 2
	}, {
		field : 'jctIdName',
		title : '所属单位',
		width : 120,
		align : 'center',
		rowspan : 2
	}, {
		field : 'devTypeName',
		title : '设备类型',
		width : 120,
		align : 'center',
		rowspan : 2
	}, {
		field : 'planDate',
		title : '设备购置时间',
		width : 120,
		align : 'center',
		rowspan : 2
	},{
		field : 'isDelete',
		title : '是否删除',
		width : 120,
		align : 'center',
		rowspan : 2,
		formatter : function(data, row, index) {
			if (data == "1") {
				return "已删除";
			} else {
				return "正常使用";
			}
		}
	} ],[ {
		field : 'quality',
		title : '数量',
		width : 80,
		align : 'center'
	}, {
		field : 'qunit',
		title : '单位',
		width : 80,
		align : 'center'
	}] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});
		
		// 清空重置按钮事件
		$('#reset').click(function() {
			$('#form').form("clear");
		});

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
			url : "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_pageHome.do",
			//url : "${pageContext.request.contextPath}/json/devicePlan.json",
			idField : 'devPlanId',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加设备购置计划窗口
		$('#addDeviceWindow').window({
			title : '添加设备购置计划',
			width : 800,
			modal : true,
			shadow : true,
			closed : true,
			height : 450,
			resizable : false
		});
		
		// 准备报审设备购置计划窗口
		$('#checkDeviceWindow').window({
			title : '报审设备购置计划窗口',
			width : 800,
			modal : true,
			shadow : true,
			closed : true,
			height : 200,
			resizable : false
		});

		// 为保存按钮，添加点击事件
		$("#save").click(function() {
			// 手动校验
			/*
			var devName = $("input[name='devName']").val();
			if(devName.trim()==""){
				$.messager.alert("警告","设备名称不能为空","warning");
			}
			 */

			// 提交form 
			if ($("#DeviceForm").form('validate')) {
				// 当form 所有字段 都通过校验 提交form表单
				$("#DeviceForm").submit();
			}
		});
		
		// 为提交报审按钮，添加点击事件
		$("#checksave").click(function() {
			//alert($("#checkPlanId").val());
			// 提交form 
			if ($("#checkForm").form('validate')) {
				// 当form 所有字段 都通过校验 提交form表单
				$("#checkForm").submit();
			}
		});
		
		// 注册添加ajax按照条件查询
		$('#ajax').click(function() {
			// form 数据转换为json 格式 
			var param = $("#form").serializeJson();
			//alert(param.devName+"       "+param.planDateBegin+"       "+param.planDateEnd);
			console.info(param);
			// 缓存 datagrid 中 
			$("#grid").datagrid('load',param);
		});
		
		//显示未审核、审核中、审核通过、审核不通过
		//未审核
		$('#noCheck').datagrid( {
			fit : true,
			border : false,
			iconCls : 'icon-forward',
			fit : true,
			rownumbers : true,
			striped : true,
			pageList : [ 2, 50, 100 ],
			pagination : true,
			toolbar : nochecktoolbar,
			url : "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_pageHome.do?state=0",
			//url : "${pageContext.request.contextPath}/json/devicePlanNoCheck.json",
			idField : 'devPlanId',
			columns : [ [
			{
				field : 'devPlanId',
				checkbox : true
			}, {
				field : 'devName',
				title : '设备名称',
				width : 120,
				align : 'center'
			}, {
				field : 'devExpense',
				title : '金额',
				width : 120,
				align : 'center'
			},{
				field : 'specType',
				title : '规格型号',
				width : 120,
				align : 'center'
			},{
				field : 'useness',
				title : '用途',
				width : 120,
				align : 'center'
			}, {
				field : 'jctIdName',
				title : '所属单位',
				width : 120,
				align : 'center'
			}, {
				field : 'devTypeName',
				title : '设备类型',
				width : 120,
				align : 'center'
			}, {
				field : 'planDate',
				title : '设备购置时间',
				width : 120,
				align : 'center'
			}] ]
		});
		//审核中
		$('#checking').datagrid( {
			fit : true,
			border : false,
			iconCls : 'icon-forward',
			fit : true,
			rownumbers : true,
			striped : true,
			pageList : [ 2, 50, 100 ],
			pagination : true,
			toolbar : checkingtoolbar,
			url : "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_pageHome.do?state=1",
			//url : "${pageContext.request.contextPath}/json/devicePlanChecking.json",		
			idField : 'devPlanId',
			columns : [ [
			{
				field : 'devPlanId',
				checkbox : true
			}, {
				field : 'devName',
				title : '设备名称',
				width : 120,
				align : 'center'
			}, {
				field : 'devExpense',
				title : '金额',
				width : 120,
				align : 'center'
			},{
				field : 'specType',
				title : '规格型号',
				width : 120,
				align : 'center'
			},{
				field : 'useness',
				title : '用途',
				width : 120,
				align : 'center'
			}, {
				field : 'jctIdName',
				title : '所属单位',
				width : 120,
				align : 'center'
			}, {
				field : 'devTypeName',
				title : '设备类型',
				width : 120,
				align : 'center'
			}, {
				field : 'planDate',
				title : '设备购置时间',
				width : 120,
				align : 'center'
			}] ]
		});
		//审核通过
		$('#checkPass').datagrid( {
			fit : true,
			border : false,
			iconCls : 'icon-forward',
			fit : true,
			rownumbers : true,
			striped : true,
			pageList : [ 2, 50, 100 ],
			pagination : true,
			toolbar : checkPasstoolbar,
			url : "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_pageHome.do?state=2",
			//url : "${pageContext.request.contextPath}/json/devicePlanCheckPass.json",		
			idField : 'devPlanId',
			columns : [ [
			{
				field : 'devPlanId',
				checkbox : true
			}, {
				field : 'devName',
				title : '设备名称',
				width : 120,
				align : 'center'
			}, {
				field : 'devExpense',
				title : '金额',
				width : 120,
				align : 'center'
			},{
				field : 'specType',
				title : '规格型号',
				width : 120,
				align : 'center'
			},{
				field : 'useness',
				title : '用途',
				width : 120,
				align : 'center'
			}, {
				field : 'jctIdName',
				title : '所属单位',
				width : 120,
				align : 'center'
			}, {
				field : 'devTypeName',
				title : '设备类型',
				width : 120,
				align : 'center'
			}, {
				field : 'planDate',
				title : '设备购置时间',
				width : 120,
				align : 'center'
			}] ]
		});
		//审核不通过
		$('#checkNoPass').datagrid( {
			fit : true,
			border : false,
			iconCls : 'icon-forward',
			fit : true,
			rownumbers : true,
			striped : true,
			pageList : [ 2, 50, 100 ],
			pagination : true,
			toolbar : checkNoPasstoolbar,
			url : "${pageContext.request.contextPath}/equapment/elecDevicePlanAction_pageHome.do?state=3",
			//url : "${pageContext.request.contextPath}/json/devicePlanCheckNoPass.json",
			idField : 'devPlanId',
			columns : [ [
			{
				field : 'devPlanId',
				checkbox : true
			}, {
				field : 'devName',
				title : '设备名称',
				width : 120,
				align : 'center'
			}, {
				field : 'devExpense',
				title : '金额',
				width : 120,
				align : 'center'
			},{
				field : 'specType',
				title : '规格型号',
				width : 120,
				align : 'center'
			},{
				field : 'useness',
				title : '用途',
				width : 120,
				align : 'center'
			}, {
				field : 'jctIdName',
				title : '所属单位',
				width : 120,
				align : 'center'
			}, {
				field : 'devTypeName',
				title : '设备类型',
				width : 120,
				align : 'center'
			}, {
				field : 'planDate',
				title : '设备购置时间',
				width : 120,
				align : 'center'
			}] ]
		});
	});

	//双击修改
	function doDblClickRow(rowIndex, rowData) {
		// rowIndex 行号
		// rowData 当前点击 行 数据 js 对象
		//alert(rowData.devPlanId+"    "+rowData.devName);
		// 装载form数据 
		$("#DeviceForm").form('load',rowData);
		// 显示窗口
		$("#addDeviceWindow").window('open');
	}
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<!-- 查询条件 -->
	<div region="east" title="查询条件" icon="icon-forward" style="width:180px;overflow:auto;" split="false" border="true" >
		<div class="datagrid-toolbar">	
			<a id="reset" href="#" class="easyui-linkbutton" plain="true" icon="icon-reload">重置</a>
		</div>
		
		<form id="form" method="post" >
			<table class="table-edit" width="100%" >				
				<tr><td>
					<b>设备名称</b><span class="operator"><a name="username-opt" opt="all"></a></span>
					<input type="text" id="devName" name="devName"/>
				</td></tr>
				<tr><td>
					<b>设备类型</b><span class="operator"><a name="gender-opt" opt="all"></a></span>
					<input class="easyui-combobox" name="devType"  
    							data-options="valueField:'ddlCode',textField:'ddlName',
    							url:'${pageContext.request.contextPath }/equapment/elecDevicePlanAction_findSystemDDL.do?devType=设备类型',editable:false" />
				</td></tr>
				<tr><td>
					<b>设备购置时间</b><span class="operator"><a name="birthday-opt" opt="date"></a></span><br>
					从<input type="text" id="planDateBegin" name="planDateBegin" value="" class="easyui-datebox" /><br/>
					到<input type="text" id="planDateEnd" name="planDateEnd" value="" class="easyui-datebox" />
				</td></tr>

			</table>
		</form>
		
		<div class="datagrid-toolbar">	
			<a id="ajax" href="#" class="easyui-linkbutton" plain="true" icon="icon-search">查询</a>
		</div>
    </div>

	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对设备购置计划进行添加或者修改" id="addDeviceWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:void(0);"
					class="easyui-linkbutton" plain="true">保存</a>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="DeviceForm"
				action="${pageContext.request.contextPath }/equapment/elecDevicePlanAction_save.do"
				method="post">
				<table cellSpacing="1" cellPadding="5" width="700" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
					<input type="hidden" name="devPlanId"/>
					<tr>
				        <td class="ta_01" colSpan="4" align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
				         <font face="宋体" size="2"><strong>添加购置计划</strong></font>
				        </td>
			        </tr>
					<tr>
						<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">设备类型：
	
	
						</td>
						<td class="ta_01" bgColor="#ffffff" width="32%">
							<input class="easyui-combobox" name="devType"  
    							data-options="valueField:'ddlCode',textField:'ddlName',
    							url:'${pageContext.request.contextPath }/equapment/elecDevicePlanAction_findSystemDDL.do?devType=设备类型', required:true,editable:false" />
							&nbsp;<font color="#FF0000">*</font>
						</td>
						<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">设备名称：
	
	
						</td>
						<td class="ta_01" bgColor="#ffffff">
							<input id="devName" name="devName" type="text"  class="easyui-validatebox"
							data-options="required:true"  maxlength="50" id="devName" size="19" >&nbsp;<font color="#FF0000">*</font>
						</td>
					</tr>
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							品牌：
						</td>
						<td class="ta_01" bgColor="#ffffff">
							<input name="trademark" class="easyui-validatebox" data-options="required:true,validType:'telephone'" type="text" maxlength="25" id="trademark" size="19" >&nbsp;<!-- font color="#FF0000">*</font -->
						</td>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							规格型号：
						</td>
						<td class="ta_01" bgColor="#ffffff">
							<input name="specType" type="text" maxlength="25" id="specType" size="19" >&nbsp;<!-- font color="#FF0000">*</font -->
						</td>
	
					</tr>
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							厂家：
						</td>
						<td class="ta_01" bgColor="#ffffff">
							<input name="produceHome" type="text" maxlength="25" id="produceHome" size="19" >
						</td>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							数量：
						</td>
						<td class="ta_01" bgColor="#ffffff">
							<input name="quality" type="text" class="easyui-numberbox" data-options="required:true,min:0,precision:0" maxlength="10" style="text-align:left" id="quality" style="width:30" value="" onblur='checkNumber(this)'>
							<input name="qunit" type="text" class="easyui-validatebox" data-options="required:true" maxlength="5" id="qunit" style="width:40" value="">&nbsp;<font color="#FF0000">*</font></td>
	
					</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						用途：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="useness" type="text" maxlength="25" id="useness" size="19" >
					</td>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						产地：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="produceArea" type="text" maxlength="25" id="produceArea" size="19" >
					</td>

				</tr>

				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						金额：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="devExpense" type="text" style="width:145px" maxlength="17" id="devExpense" onblur='checkDecimal(this)'>
						<font face="宋体"> 元</font>

					</td>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						使用单位：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="useUnit" type="text" maxlength="25" id="useUnit" size="19" >&nbsp;<!-- font color="#FF0000">*</font -->
					</td>

				</tr>
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							所属单位：
						</td>
						<td class="ta_01" bgColor="#ffffff">
							<input class="easyui-combobox" name="jctId"  
	    							data-options="valueField:'ddlCode',textField:'ddlName',
	    							url:'${pageContext.request.contextPath }/equapment/elecDevicePlanAction_findSystemDDL.do?devType=所属单位', required:true,editable:true" />
								&nbsp;<font color="#FF0000">*</font>
						</td>
						<td class="ta_01" align="center" bgcolor="#f5fafe" height="22">
							计划时间：
						</td>
						<td class="ta_01" bgcolor="#ffffff" width="277">
							<input type="text" id="planDate" name="planDate" class="easyui-datebox" data-options="required:true"/>&nbsp;<font color="#FF0000">*</font>
						</td>

				  </tr>
				  <tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						校准周期：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="adjustPeriod" type="text" maxlength="4" id="adjustPeriod" size="12" value="" onblur='checkNumber(this)'>
						<select name="apunit" id="apunit">
							<option value="0"></option>
							<option value="年">
								年
							</option>
							<option value="月" selected>
								月
							</option>
						</select>&nbsp;<!-- font color="#FF0000">*</font -->
					</td>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						检修周期：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input name="overhaulPeriod" type="text" maxlength="4" id="overhaulPeriod" size="12" value="" onblur='checkNumber(this)'>
						<select name="opunit" id="opunit">
							<option value="0"></option>
							<option value="年">
								年
							</option>
							<option value="月" selected>
								月
							</option>
							<option value="周">
								周
							</option>
							<option value="日">
								日
							</option>
						</select>&nbsp;<!-- font color="#FF0000">*</font -->
					</td>
				</tr>

				<tr>
					<TD class="ta_01" align="center" bgColor="#f5fafe">
						配置：
					</TD>
					<TD class="ta_01" bgColor="#ffffff" colSpan="3">
						<textarea name="configure" id="configure" width="25" style="WIDTH:96%" rows="3"></textarea></TD>
				</tr>
				
				<tr>
					<TD class="ta_01" align="center" bgColor="#f5fafe">
						备注：
					</TD>
					<TD class="ta_01" bgColor="#ffffff" colSpan="3">
						<textarea name="ecomment" id="ecomment" width="25" style="WIDTH:96%" rows="3"></textarea></TD>
				</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 显示审核状态 -->
	<div region="south" border="false" style="height:180px">
		<div id="tabs" fit="true" class="easyui-tabs">
			<div title="未审核" id="noCheckTab"
				style="width:100%;height:100%">
				<table id="noCheck"></table>
			</div>	
			<div title="审核中" id="checkingTab"
				style="width:100%;height:100%">
				<table id="checking"></table>
			</div>	
			<div title="审核通过" id="checkPassTab"
				style="width:100%;height:100%">
				<table id="checkPass"></table>
			</div>
			<div title="审核不通过" id="checkNoPassTab"
				style="width:100%;height:100%">
				<table id="checkNoPass"></table>
			</div>
		</div>
	</div>
	
	<!-- 报审页面 -->
	<div class="easyui-window" title="报审窗口页面" id="checkDeviceWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="checksave" icon="icon-save" href="javascript:void(0);"
					class="easyui-linkbutton" plain="true">提交</a>
			</div>
		</div>

		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="checkForm"
				action="${pageContext.request.contextPath }/workflow/elecWorkflowAction_startProcess.do"
				method="post">
				<table cellSpacing="1" cellPadding="5" width="700" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
					<input type="hidden" name="devPlanId" id="checkPlanId"/>
					<input type="hidden" name="processDefinitionKey" value="devicePlan"/>
					<tr>
				        <td class="ta_01" colSpan="4" align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
				         <font face="宋体" size="2"><strong>报审设备购置计划</strong></font>
				        </td>
			        </tr>
					
				</table>
			</form>
		</div>
	</div>
</body>
</html>
