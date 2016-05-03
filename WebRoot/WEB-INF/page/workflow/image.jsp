<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看当前流程图</title>
</head>
<body>
<!-- 1.获取到规则流程图 -->
<img style="position: absolute;top: 0px;left: 0px;" src="elecWorkflowAction_viewImage.do?deploymentId=<s:property value="#request.pd.deploymentId"/>&imageName=<s:property value="#request.pd.diagramResourceName"/>">

<!-- 2.根据当前活动的坐标，动态绘制DIV -->
<div style="position: absolute;border:1px solid red;top:<s:property value="#request.map.y"/>px;left: <s:property value="#request.map.x"/>px;width: <s:property value="#request.map.width"/>px;height:<s:property value="#request.map.height"/>px;   ">

</body>
</html>