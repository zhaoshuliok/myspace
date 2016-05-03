<%@ page language="java" pageEncoding="UTF-8"%>
<div style="padding:10px;">
	<div style="margin-bottom:8px;">国家电力检测中心通知栏<br /></div>
	<form id="notice_form" method="post" enctype="application/x-www-form-urlencoded">
		<textarea name="content" id="content" rows="5" cols="60" style="border:solid 3px #E2E2E2;line-height:16px; padding:5px;"></textarea>
		<br />
		<div style="margin-top:8px;"><a href="javascript:void(0);" onclick="$('#notice_form').submit();" id="notice_form_but" class="easyui-linkbutton">提交</a></div>
	</form>
</div>
<script>
$().ready(function(){
	/**
	$.post("elecNoticeAction_findNotice.do",{},function(data,textStatus){
   	    //将查询的结果放置到通知的文本域中
   	    if(data!=null){
   	    	$("#content").val(data.content);
   	    }
    });
	*/
})
//功能相对独立，应该单独放置
$('#notice_form').form({  
	url:"${pageContext.request.contextPath}/system/elecNoticeAction_saveNotice.do",  
	onSubmit: function(){  
		if($('#content').val()==""){
			$.messager.alert('Warning',"内容太少");	
			return false;
		}
	}, 
	success:function(data){  
		$.messager.alert('Warning',"提交成功"); 
		$('#notice_c').val("");
	}  
});   
</script>