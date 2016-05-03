<%@ page language="java"  pageEncoding="UTF-8"%>
<html>
  <head>     
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    
	</head>
<link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/jquery.easyui.min.js"></script>
	
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
			href:'${pageContext.request.contextPath }/system/elecMenuAction_alermStation.do'
		}, {
			id : 'p2',
			title : '设备运行情况',
			height : 255,
			collapsible : true,
			href:'${pageContext.request.contextPath }/system/elecMenuAction_alermDevice.do'
		}, {
			id : 'p3',
			title : '代办任务事宜',
			height : 255,
			collapsible : true,
			href:'${pageContext.request.contextPath }/system/elecMenuAction_alermTask.do'
		}, {
			id : 'p4',
			title : '系统通知栏',
			height : 255,
			collapsible : true,
			href:'${pageContext.request.contextPath }/system/elecMenuAction_alermNotice.do'
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
</head>
<body>
	<div id="layout_portal_portal" style="position:relative;height:600px;">
		<div></div>
		<div></div>
	</div>
</body>
</html>

