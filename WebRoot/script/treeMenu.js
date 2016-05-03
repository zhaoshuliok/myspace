var menu = {
	setting: {
		data : {
			simpleData : { // 简单数据 
				enable : true,
				idKey: "id",
                pIdKey: "pid"
			}
		}
    },
	loadMenuTree:function(){
		$.post("elecMenuAction_showMenu.do",{},function(data){
			//data是json数组的格式（data中存放的权限的数据）
			$.fn.zTree.init($("#menuTree"), menu.setting, data);			
		});
	}
};

$().ready(function(){
	menu.loadMenuTree();
});
