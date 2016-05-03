<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>messager消息控件使用</title>
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
		// 1、 alert 提示消息框 
		// $.messager.alert("标题","消息内容","warning");
		
		// 2、确认框
		// $.messager.confirm("紧急提示","确认删除吗？",function(isConfirm){
		//	alert(isConfirm);
		//});
		
		// 3、 输入框
		//$.messager.prompt("个人信息","请输入您的爱好?",function(val){
			//alert(val);
		//});
		
		// 4、 提示框
		/**  $.messager.show({
			title:"大减价",
			msg: "等于白送！！！<a href='#'>快来点</a>",
			timeout : 5000 // 5秒后消失
		});*/ 
		
		// 5、 进度条
	     $.messager.progress({
			interval:1000
		});  
		
		// 特定时间关闭它
		window.setTimeout("$.messager.progress('close');",3000);
	});
</script>
</head>
<body>

</body>
</html>