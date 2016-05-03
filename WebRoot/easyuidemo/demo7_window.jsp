<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>window控件使用</title>
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
	<div class="easyui-window" 
		style="width: 200px;height: 100px;"
		data-options="title:'添加窗口',collapsible:false,minimizable:false,closed:true,modal:false">
		这是一个窗口
	</div>
	
	<input type="button" value="出现" onclick="$('.easyui-window').window('open');" />
	
	<a href="javascript:void(0);" 
		class="easyui-linkbutton" 
		data-options="iconCls:'icon-save'" 
		onclick="$('.easyui-window').window('open');">出现</a>
</body>
</html>