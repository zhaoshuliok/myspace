<%@ page language="java" pageEncoding="UTF-8"%>








<html>

<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/My97DatePicker/WdatePicker.js"></script>
		<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
		<script type='text/javascript' src="${pageContext.request.contextPath }/script/validate.js"></script>
<SCRIPT type="text/javascript">
function ini(){
   document.all.devType.focus();
}
function check(){
	var theForm = document.forms[0];
	
    if(!checkNull(theForm.devType))
		{
			alert("请选择设备类型");
			theForm.devType.focus(); 
			return false;
		} 
	if(Trim(theForm.devType.value)==""){
	
	    	alert("请选择设备类型");
			theForm.devType.focus();
			return false;
	    }
	if(Trim(theForm.devName.value)==""){
	
	    	alert("请输入设备名称");
			theForm.devName.focus();
			return false;
	    }
	if(theForm.devState.value=="0"){
	
	    	alert("请选择设备状态");
			theForm.devState.focus();
			return false;
	    }    
	    
	  
	     if(Trim(theForm.quality.value)==""){
	
	    	alert("请输入数量");
			theForm.quality.focus();
			return false;
	    }
	    
	    
	     if(Trim(theForm.jctId.value)==""){
	
	    	alert("请输入所属单位");
			theForm.jctId.focus();
			return false;
	    }
	   if (theForm.runDescribe.value.length >250)
		{
		    alert("运行情况描述不能超过250个汉字！");
		    theForm.runDescribe.focus();
		    return false;
		}
	   if (theForm.comment.value.length >250)
		{
		    alert("备注不能超过250个汉字！");
		    theForm.comment.focus();
		    return false;
		}
		if(Trim(theForm.overhaulPeriod.value)!="" && theForm.opunit.value =="")
		{
		  alert("请选择检修周期！");
		  theForm.opunit.focus();
		   return false;
		}
		if(Trim(theForm.adjustPeriod.value)!="" && theForm.apunit.value =="")
		{
		  alert("请选择校准周期！");
		  theForm.apunit.focus();
		   return false;
		}
		
		

	    /*if(Trim(theForm.adjustPeriod.value)==""){
	
	    	alert("请输入校准周期");
			theForm.adjustPeriod.focus();
			return false;
	    }*/
	    
	    /*if(Trim(theForm.overhaulPeriod.value)==""){
	
	    	alert("请输入检修周期");
			theForm.overhaulPeriod.focus();
			return false;
	    }*/
	    
 
	    
	 if(window.opener)
	{
		document.Form1.action="updateDevice.do";
		document.Form1.submit();
		alert("保存成功！"); 
	    window.opener.Pub.submitActionWithForm('Form1','toDevice.do','F1');
	
	}
	self.close();
}

		function checkNumber(item){
			if(item.value != ""){
				isNumber1(item);
			}
		}
		
		function isNumber1(obj){
					
			if(obj == null){
				alert("对象为空!");
			return false;
			}
			
			var v = obj.value;
				
			var pattern = /^[0-9]*[1-9][0-9]*$/;
			flag = pattern.test(v);			
			if(!flag){
				alert("请输正整数!");
				
				obj.select();
				obj.focus();
				return false;
			}else{
				return true;
			}
		}
		
		function checkDecimal(item){
			if(item.value != "0" && item.value != "")
			{
				isDecimal(item);
			}
		}
		
		function isDecimal(obj){
				if(obj == null)
					alert("对象为空");
					
				var v = obj.value;
					
				var pattern = /^[0-9]+\.\d{1,2}$/;
				flag = pattern.test(v);
				
				if(!flag){
					alert("输入格式为: 0.00");			
					obj.select();
					obj.focus();
					return false;
				}else{
					return true;
				}
			}
			
</SCRIPT>
<title>仪器设备信息</title>
</head>

<body>

<form name="Form1" method="post" >

							<input type="hidden" name="goaddto" id="goaddto" value="1"> 
                      &nbsp;
			
  	<table cellSpacing="1" cellPadding="5" width="680" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">

		<tr>
			<td class="ta_01"  colSpan="4" align="center" background="../images/b-info.gif">
			<font face="宋体" size="2"><strong>设备信息</strong></font>
			</td>
		</tr>
		<tr>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">设备名称：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="devName" type="text" maxlength="25" id="devName" value="UPS123" size="19">&nbsp;<font color="#FF0000">*</font> </td>
			<td class="ta_01"  align="center" bgColor="#f5fafe">所属单位：</td>
			<td class="ta_01" bgColor="#ffffff">
			<select name="jctId" id="jctId" class="bg" style="width:148px">
					
											
					        <option value=""></option>
						
						 <option value="1" selected="selected">北京</option>	
		
							<option value="2">上海</option>	
							
							<option value="3">深圳</option>	
							
							<option value="4">厦门</option>	
							
							<option value="5">成都</option>	
							
							<option value="6">海尔滨</option>	
							
							<option value="7">长春</option>	
							
							<option value="8">沈阳</option>	
							
							<option value="9">广州</option>	
							
							<option value="10">西安</option>	
							
							<option value="11">南宁</option>	
							
							<option value="12">天津</option>	
							
							<option value="13">海南</option>	
					
				 
				
				
				
				
				
						</select>&nbsp;<font color="#FF0000">*</font></td>
		</tr>
<tr>
	<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">设备类型：</td>
	<td class="ta_01" bgColor="#ffffff" >
			<select name="devType" id="devType" class="bg">
							<option value="">
								全部
							</option>
						
						
						
						 <option value="1" selected>
								电力设备dd
							</option>
						
						
				 
						
						
					        <option value="2">
								电机设备
							</option>
					
				 
						
						
					        <option value="3">
								通讯设备
							</option>
					
				 
						
						
					        <option value="4">
								防雷设备
							</option>
					
				 
						
						
					        <option value="5">
								办公设备
							</option>
					
				 
						
						
					        <option value="6">
								电视机房设备
							</option>
					
				 
						
						
					        <option value="7">
								发电机房设备
							</option>
					
				 
				
						</select>&nbsp;<font color="#FF0000">*</font></td>
<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
			<td class="ta_01" bgColor="#ffffff">
				<input name="quality" type="text" maxlength="10" style="text-align:left" id="quality" style="width:40" value="1" onblur='checkNumber(this)'>
						<input name="qunit" type="text" maxlength="5" id="qunit" style="width:105" value="ge">&nbsp;<font color="#FF0000">*</font>
					</td>
</tr>

		<tr>
			<td class="ta_01" align="center" bgColor="#f5fafe">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
	  <td class="ta_01" bgColor="#ffffff">
	    <input name="devExpense" type="text" maxlength="17" id="devExpense" size="19" value="1.00" onblur='checkDecimal(this)'>&nbsp;元&nbsp;</td>
			<td class="ta_01" align="center" bgColor="#f5fafe">配&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="configure" type="text" maxlength="50" id="configure" value="40KVA" size="19"></td>
		</tr>
		<tr>
			<td class="ta_01" align="center" bgColor="#f5fafe">规格型号：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="specType" type="text" maxlength="25" id="specType" size="19" value="FR-UK 3140"></td>
			
			<td class="ta_01" align="center" bgColor="#f5fafe" height="30">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</td>
			<td class="ta_01" bgColor="#ffffff" height="30">
			<input name="trademark" type="text" maxlength="25" id="trademark" size="19" value="科华"></td>
		</tr>
		<tr>
			<td class="ta_01" align="center" bgcolor="#f5fafe" height="22">设备状态：</td>
					<td class="ta_01" bgcolor="#ffffff" align="left">
					
					
					
					
					<select name="devState" id="devState" class="bg">
							<option value="0">
								全部
							</option>
						
						
						
						 <option value="1" selected>
								正常
							</option>
						
						
				 
						
						
					        <option value="2">
								检修
							</option>
					
				 
						
						
					        <option value="3">
								维修
							</option>
					
				 
				
						</select></td>
			<td class="ta_01" align="center" bgColor="#f5fafe">厂&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="produceHome" type="text" maxlength="25" id="produceHome" size="19" value="恒电"></td>
		</tr>

<tr>
			<td class="ta_01" align="center" bgColor="#f5fafe">产&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="produceArea" type="text" maxlength="25" id="produceArea" size="19" value=""><font face="宋体" color="red">
			</font></td>
			<td class="ta_01" align="center" bgColor="#f5fafe">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="useness" type="text" maxlength="25" id="useness" size="19" value="电视机房设备供电"><font face="宋体" color="red"> </font></td>
		</tr>

<tr>
			<td class="ta_01" align="center" bgColor="#f5fafe">使用单位：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="useUnit" type="text" maxlength="25" id="useUnit" size="19" value=""><font face="宋体" color="red">
			</font></td>
			<td class="ta_01" align="center" bgColor="#f5fafe">检修周期：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="overhaulPeriod" type="text" maxlength="25" id="overhaulPeriod" size="3" value="1" onblur='checkNumber(this)'>
					<select name="opunit" id="opunit">
							<option value=""></option>
							
							
							
						      <option value="年">
								年



							</option>
							<option value="月" selected>
								月



							</option>
							<option value="周">
								周



							</option>
							<option value="日">
								日



							</option>
						 
							
							
							
							
							
							
							


                                                      



						</select>&nbsp;<!-- font color="#FF0000">*</font --></td>
		</tr>
<tr>
	<td class="ta_01" align="center" bgColor="#f5fafe" height="30">使用日期：</td>
			<td class="ta_01" bgColor="#ffffff" height="30">
			<input name="useDate" type="text" maxlength="40" id="useDate" size="19" onclick="WdatePicker()"  value="2007-06-25" size="20"><font face="宋体" color="red">
			</font></td>
			<td class="ta_01" align="center" bgColor="#f5fafe">校准周期：</td>
			<td class="ta_01" bgColor="#ffffff">
			<input name="adjustPeriod" type="text" maxlength="25" id="adjustPeriod" size="3" value="" onblur='checkNumber(this)'>
					<select name="apunit" id="apunit">
						
						
							<option value=""></option>
							
					       <option value="年" selected>
								年



							</option>
							<option value="月">
								月



							</option>
					
					

                                          
						</select>&nbsp;<!-- font color="#FF0000">*</font --></td>
	  </tr>

<TR>
<TD class="ta_01" align="center" bgColor="#f5fafe">运行情况描述：</TD>
<TD class="ta_01" bgColor="#ffffff" colSpan="3"><textarea name="runDescribe" id="runDescribe"  style="WIDTH:96%" rows="3"></textarea></TD>
</TR>															
<TR>
<TD class="ta_01" align="center" bgColor="#f5fafe">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</TD>
<TD class="ta_01" bgColor="#ffffff" colSpan="3"><textarea name="comment" id="comment"  style="WIDTH:96%" rows="3"></textarea></TD>
</TR>
		<tr>
			<td class="ta_01" style="width: 100%" align="center" bgColor="#f5fafe" colSpan="4">
			 <input type="hidden" name="devId" value="ab15b1290c004d53af24bd2ce8845fa4">
			 
			<input style="font-size:12px; color:black; height=22;width=55" type="reset" value="关闭" ID="Reset1" NAME="Reset1" onClick="window.close();">
			<span id="Label1"></span></td>
		</tr>
	</table>
	<br>
</form>

	
</body>

</html>