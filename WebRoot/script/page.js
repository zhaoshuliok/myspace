function  gotopage(path,where){
       var $page=$("form:eq(1) input[name='pageNO']");
       
       if(where=="next"){ 
    	   $page.val($("input[name='nextpageNO']").val());
      
       }else if(where=="prev"){
    	   $page.val($("input[name='prevpageNO']").val());
       }else if(where=="end"){
    	   $page.val($("input[name='sumPage']").val());
       }else if(where=="start"){
    	   $page.val("1");
       }else if(where=="go"){
         if($.trim($("input[name='goPage']").val())=="")
	     {
		     alert("请输入页数"); 
		     $("input[name='goPage']")[0].focus();   
		     return false;
	     }
	     if(!checkNumber($("input[name='goPage']")[0]))
	     {
		     alert("请输入正确页数(数字)"); 
		     $("input[name='goPage']")[0].focus();  
		     return false;
	     }
	     
	     var objgo=parseInt($("input[name='goPage']").val());
	     var objsum=parseInt($("input[name='sumPage']").val());
	     if(objgo<=0||objgo>objsum){
	         alert("不存在此页，请重新输入页数"); 
	         $("input[name='goPage']")[0].focus();  
		     return false; 
	     }
	     $page.val($("input[name='goPage']").val());          
      } 
       $("form:eq(0) input[name='pageNO']").val($("form:eq(1) input[name='pageNO']").val());
       Pub.submitActionWithForm('Form2',path,'Form1');       
  }
  
  function gotoquery(path){
	  $("from:eq(0) input[name='pageNO']").val("1");
      Pub.submitActionWithForm('Form2',path,'Form1'); 
  }