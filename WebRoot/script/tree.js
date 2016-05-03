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
		$.fn.zTree.init($("#menuTree"), menu.setting, privilegeDate);
	}
};

$().ready(function(){
	menu.loadMenuTree();
});
