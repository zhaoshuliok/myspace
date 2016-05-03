<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>使用 ztree 树形菜单，完成选项卡联动 </title>
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
<!-- 导入ztree -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery.ztree.all-3.5.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/zTreeStyle/zTreeStyle.css">
	
<script type="text/javascript">
	$(function(){
		// 配置ztree 属性
		var setting = {
			data : {
				simpleData: {
					// 启动简单数据格式
					enable : true
				}
			},
			callback : {
				onClick: function(event, treeId, treeNode){
					//alert(event+"   "+treeId+"   "+treeNode.id+"   "+treeNode.pId+"    "+treeNode.name+"   "+treeNode.page);
					// 打印page属性 
					// 只针对有page属性节点 进行操作
					if(treeNode.page != null){
						// 判断选项卡是否存在，不存在添加 
						if($(".easyui-tabs").tabs('exists',treeNode.name)){
							// 选项卡存在，切换到选项卡
							$(".easyui-tabs").tabs('select',treeNode.name);
						}else{
							// 选项卡不存在
							// 添加新的选项卡 
							$(".easyui-tabs").tabs('add', {
								title : treeNode.name,
								content : '<div style="width:100%;height:100%;overflow:hidden;">'
									+ '<iframe src="'
									+ treeNode.page
									+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>',
								closable:true
							});
						}
						
					}
				}
			}
		};
		
		// 准备节点数据 
		var zNodes = [
			{name:"基本功能",id:1, pId:0 },
			{name:"友情链接",id:2, pId:0 },
			{name:"百度", id:3, pId:2, url:"http://www.baidu.com"},
			{name:"主页布局demo",id:4, pId:1, url:"${pageContext.request.contextPath}/easyuidemo/test.jsp"}
		];
		
		// 初始化ztree
		$.fn.zTree.init($("#menu"), setting, zNodes);
	});
</script>	
</head>
<!-- 1、 对body 应用页面布局 -->
<body class="easyui-layout">
	<!-- 2、通过 data-options 的 region属性，指定div哪个部分 -->
	<div data-options="region:'north',title:'国家电力设备资源管理系统'" style="height: 100px">北部</div>
	<div data-options="region:'west',title:'系统菜单'" style="width: 150px">
		<!-- 多功能菜单  折叠效果-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个div 就是一个菜单 -->
			<div data-options="title:'基本菜单'">
				<!-- 通过ztree制作基本菜单 -->
				<ul id="menu" class="ztree"></ul>
			</div>
			<div data-options="title:'管理员菜单'">
				菜单二
			</div>
			<div data-options="title:'控制面板'">菜单三</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 提供选项卡菜单 -->
		<div class="easyui-tabs" data-options="fit:true">
			<!-- 每个div 都是一个选项卡 -->
			<div data-options="title:'选项卡一',closable:true">选项卡一</div> 
			<div data-options="title:'选项卡二'">选项卡二</div>
			<div data-options="title:'选项卡三'">选项卡三</div>
			<div data-options="title:'选项卡四'">选项卡四</div>
		</div>
	</div>
	<div data-options="region:'south'" style="height: 80px">南部</div>
</body>
</html>