<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/easyui/themes/icon.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/Style.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/easyui/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/script/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务办理</title>
</head>
<script type="text/javascript">
	$(function(){
		$("body").css({visibility:"visible"});
		
		// 对save按钮条件 点击事件
		/* $("a[id='save']").click(function(){
			//获取选择的连线名称
			var outcome = $("#save").text();
			//赋值给隐藏域
			$("#outcome").val(outcome);
			$('#planbillForm').submit();
		}); */
		
		
	});
	
	//将连线的名称赋值给隐藏域，然后提交表单
	function check(opporate){
		//赋值给隐藏域
		$("#outcome").val(opporate);
		$('#planbillForm').submit();
	}
</script>


<body class="easyui-layout" style="visibility:hidden;">
	<form id="planbillForm" action="${pageContext.request.contextPath }/workflow/elecWorkflowAction_submitTask.do" method="post">
	<div region="north" style="height:31px;overflow:hidden;" split="false"
		border="false">
		<div class="datagrid-toolbar">
			<s:if test="#request.outcomeList!=null && #request.outcomeList.size()>0">
				<s:iterator value="#request.outcomeList">
					<a id="save" icon="icon-edit" class="easyui-linkbutton" plain="true" onclick="check('<s:property/>');"><s:property/></a>	
				</s:iterator>
			</s:if>
			<a id="edit" icon="icon-back" href="#" onclick="javascript:history.go(-1);" class="easyui-linkbutton" plain="true">返回</a>	
		</div>
	</div>
	<div region="center" style="overflow:auto;padding:5px;" border="false">
		<!-- 任务ID -->
		<s:hidden name="taskId"></s:hidden>
		<!-- 设备购置计划单ID -->
		<s:hidden name="devPlanId"></s:hidden>
		<!-- 连线的名称 -->
		<input type="hidden" name="outcome" id="outcome"/>
		
		<table class="table-edit" width="95%" align="center">
			<tr>
			        <td class="ta_01" colSpan="4" align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
			         <font face="宋体" size="2"><strong>设备购置计划申请单</strong></font>
			        </td>
		        </tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">设备类型：
					</td>
					<td class="ta_01" bgColor="#ffffff" width="32%">
						<s:property value="devTypeName"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">设备名称：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="devName"/>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						品牌：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="trademark"/>
					</td>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						规格型号：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="specType"/>
					</td>

				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						厂家：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="produceHome"/>
					</td>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						数量：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="quality"/>&nbsp;&nbsp;<s:property value="qunit"/>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						用途：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="useness"/>
					</td>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						产地：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="produceArea"/>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						金额：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="devExpense"/><font face="宋体"> 元</font>
					</td>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						使用单位：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="useUnit"/>
					</td>
				</tr>
				
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						所属单位：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<s:property value="jctIdName"/>
					</td>
					<td class="ta_01" align="center" bgcolor="#f5fafe" height="22">
						计划时间：
					</td>
					<td class="ta_01" bgcolor="#ffffff" width="277">
						<s:date name="planDate" format="yyyy-MM-dd HH:mm:ss"/>
					</td>

				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						配置：
					</td>
					<td class="ta_01" bgColor="#ffffff" colSpan="3">
						<s:property value="configure"/>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						备注：
					</td>
					<td class="ta_01" bgColor="#ffffff" colSpan="3">
						<s:property value="ecomment"/>
					</td>
				</tr>
		</table>
		
			<table class="table-edit" width="95%" align="center">
				<tr>
				        <td class="ta_01" colSpan="4" align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
				         <font face="宋体" size="2"><strong>填写批注信息</strong></font>
				        </td>
			        </tr>
			       
					<tr>
						<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">批注：
						</td>
						<td class="ta_01" bgColor="#ffffff" width="32%">
							<textarea name="comment" cols="50" rows="5"></textarea>
						</td>
					</tr>
			</table>
		<hr>
		<br>
		<s:if test="#request.commentList!=null && #request.commentList.size()>0">
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
			        <td class="ta_01" colSpan="4" align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
			         <font face="宋体" size="2"><strong>显示设备购置计划申请的批注信息</strong></font>
			        </td>
		          </tr>
				  <tr>
				    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
				      <tr>
				        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">时间</span></div></td>
				        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">批注人</span></div></td>
				        <td width="75%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">批注信息</span></div></td>
				      </tr>
				      <s:iterator value="#request.commentList">
				        <tr>
					        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center"><s:date name="time" format="yyyy-MM-dd HH:mm:ss"/></div></td>
					        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><s:property value="userId"/></div></td>
					        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center"><s:property value="fullMessage"/></div></td>
					    </tr> 
				      
				      </s:iterator>
				      
				    </table></td>
				  </tr>
			</table>
		</s:if>
		<s:else>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
				    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td height="24" bgcolor="#F7F7F7"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td><table width="95%" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
				                <td width="94%" valign="bottom"><span><b>暂时没有批注信息</b></span></td>
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				      </tr>
				    </table></td>
				  </tr>
			</table>
		</s:else>
				
	</div>
	</form>
</body>


</html>