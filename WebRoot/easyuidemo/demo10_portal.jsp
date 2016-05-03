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
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>

<!-- 导入ext -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/ext/jquery.cookie.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/ext/portal.css">
<script type="text/javascript">
	$(function() {
		panels = [ {
			id : 'p1',
			title : '站点运行情况',
			height : 255,
			collapsible : true,
			href:'${pageContext.request.contextPath }/easyuidemo/alerm1.jsp'
		}, {
			id : 'p2',
			title : '设备运行情况',
			height : 255,
			collapsible : true,
			href:'${pageContext.request.contextPath }/easyuidemo/alerm2.jsp'
		}, {
			id : 'p3',
			title : '代办任务事宜',
			height : 255,
			collapsible : true,
			href:'${pageContext.request.contextPath }/easyuidemo/alerm3.jsp'
		}, {
			id : 'p4',
			title : '系统通知栏',
			height : 255,
			collapsible : true,
			href:'${pageContext.request.contextPath }/easyuidemo/alerm4.jsp'
		}];
		 $('#layout_portal_portal').portal({
			border : false,
			fit : true
		 });
		var state = 'p1,p3:p2,p4';/*冒号代表列，逗号代表行*/

		addPortalPanels(state);
		$('#layout_portal_portal').portal('resize');
	
	});
	
	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	function addPortalPanels(portalState) {
		var columns = portalState.split(':');
		for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for (var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					$('#layout_portal_portal').portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
</script>
<body>
	<div id="layout_portal_portal" style="position:relative;height:600px;">
		<div></div>
		<div></div>
	</div>
</body>
</html>