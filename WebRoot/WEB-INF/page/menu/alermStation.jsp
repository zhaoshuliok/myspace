<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<STYLE>BODY {
	SCROLLBAR-ARROW-COLOR: #ffffff; SCROLLBAR-BASE-COLOR: #dee3f7
}
</STYLE>
<div style="padding:10px;">
    <table width="100%" border="0" id="table8">
				<tr>
					<td align="left" valign="middle"  style="word-break: break-all">
					<span class="style1">
						<s:property value="stationRun" escapeHtml="false"/>
					</span></td>
				</tr>		
	
				<tr>
					<td align="left" valign="middle"  style="word-break: break-all">
					<span class="style1">
					<font color="red"><s:date name="createDate"/></font>
					</span></td>
				</tr>		
				
	</table>
</div>