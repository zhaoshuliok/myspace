function Pub(){ } 
/**
 * 
 * @param data：服务器返回的结果
 * @param eleid：表单Form2的名称
 */
Pub.handleResponse= function handleResponse(data,eleid){
	//获取表单Form2的对象
	var ele = $("#"+eleid);
    //将返回的结果放置到表单Form2的元素中
    ele.html(data);
}



/**
 * 
 * @param sForm：传递表单Form1的名称
 * @returns {String}：使用ajax返回服务器端的参数，传递的就是表单Form1中元素的参数
 */
Pub.getParams2Str=function getParams2Str(sForm){
	var strDiv = "";
	strDiv = $("#"+sForm).serialize();
    return strDiv;  
}

/***
 * Post请求
 * domId：表单Form2的名称
 * action：表示URL连接
 * sForm：表单Form1的名称
 */
Pub.submitActionWithForm=function(domId,action,sForm){
	var str = Pub.getParams2Str(sForm); 
	$.post(action,str,function(data){
		Pub.handleResponse(data,domId);
	})
}

/***
 * Get请求
 * domId：表单Form2的名称
 * action：表示URL连接
 * sForm：表单Form1的名称
 */
Pub.submitActionWithFormGet=function(domId,action,sForm){
	  
	$.get(action,{},function(data){
		Pub.handleResponse(data,domId);
	})
}

