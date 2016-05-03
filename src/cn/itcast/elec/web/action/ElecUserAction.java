package cn.itcast.elec.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.domain.ElecUserFile;
import cn.itcast.elec.service.IElecSystemDDLService;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.util.DateUtils;
import cn.itcast.elec.util.ExcelFileGenerator;
import cn.itcast.elec.util.FileUploadHelper;
import cn.itcast.elec.util.GenerateSqlFromExcel;
import cn.itcast.elec.util.MD5keyBean;
import cn.itcast.elec.util.ValueUtils;
import cn.itcast.elec.web.form.HighChartForm;

@Controller("elecUserAction")
@Scope("prototype")
public class ElecUserAction extends BaseAction<ElecUser> {

	private ElecUser elecUser = this.getModel();

	/**用户的Service*/
	@Resource(name=IElecUserService.SERVICE_NAME)
	private IElecUserService elecUserService;
	
	/**数据字典的Service*/
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
//	/**用户管理的首页显示，使用hql语句的缓存*/
//	public String home(){
//		//1：使用所属单位作为数据类型，查询数据字典，查询对应的集合List
//		List<ElecSystemDDL> jctList = elecSystemDDLService.findSystemDDLListByKeyword("所属单位");
//		request.setAttribute("jctList", jctList);
//		//2：使用查询条件查询结果集的列表，返回List<ElecUser>
//		List<ElecUser> userList = elecUserService.findUserListByCondition(elecUser);
//		request.setAttribute("userList", userList);
//		//3：数据字典的转换，将性别、职位进行数据字典的转换，使用数据类型和数据项的编号获取数据项的值
//		if(userList!=null && userList.size()>0){
//			for(ElecUser elecUser:userList){
//				//使用数据类型和数据项的编号获取数据项的值
//				String sexID = elecSystemDDLService.findDdlNameByKeywordAndDdlCode("性别",elecUser.getSexID());
//				elecUser.setSexID(sexID);
//				String postID = elecSystemDDLService.findDdlNameByKeywordAndDdlCode("职位",elecUser.getPostID());
//				elecUser.setPostID(postID);
//			}
//		}
//		/**添加分页 begin*/
//		String initPage = request.getParameter("initPage");
//		//执行ajax的操作（查询，上一页，下一页...）
//		if(initPage!=null && initPage.equals("1")){
//			return "list";
//		}
//		/**添加分页end*/
//		return "home";
//	}
	
	/**用户管理的首页显示，使用sql语句的联合查询*/
	public String home(){
		//1：使用所属单位作为数据类型，查询数据字典，查询对应的集合List
		List<ElecSystemDDL> jctList = elecSystemDDLService.findSystemDDLListByKeyword("所属单位");
		request.setAttribute("jctList", jctList);
		//2：使用查询条件查询结果集的列表，返回List<ElecUser>
		List<ElecUser> userList = elecUserService.findUserListByConditionWithSql(elecUser);
		request.setAttribute("userList", userList);
		
		/**添加分页 begin*/
		String initPage = request.getParameter("initPage");
		//执行ajax的操作（查询，上一页，下一页...）
		if(initPage!=null && initPage.equals("1")){
			return "list";
		}
		/**添加分页end*/
		/**故意抛出异常*/
//		try {
//			ElecUser user = null;
//			user.getAddress();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("抛出运行时异常UserAction类");
//		}
		
		/**代码级别的权限控制*/
		//需求：如果是系统管理员，返回数据的前100条，如果不是系统管理员，返回数据的前10条
		Subject subject = SecurityUtils.getSubject();
		//表示如果当前用户具有1的角色
		if(subject.hasRole("1")){
			System.out.println("返回100条数据！");
		}
		else{
			System.out.println("返回50条数据！");
		}
		return "home";
	}
	
	/**跳转到新增页面*/
	public String add(){
		//1：初始化页面数据字典加载的集合，遍历到页面的下拉菜单中
		this.initSystemDDL();
		return "add";
	}

	//1：初始化页面数据字典加载的集合，遍历到页面的下拉菜单中
	private void initSystemDDL() {
		List<ElecSystemDDL> jctList = elecSystemDDLService.findSystemDDLListByKeyword("所属单位");
		request.setAttribute("jctList", jctList);
		List<ElecSystemDDL> postList = elecSystemDDLService.findSystemDDLListByKeyword("职位");
		request.setAttribute("postList", postList);
		List<ElecSystemDDL> isDutyList = elecSystemDDLService.findSystemDDLListByKeyword("是否在职");
		request.setAttribute("isDutyList", isDutyList);
		List<ElecSystemDDL> sexList = elecSystemDDLService.findSystemDDLListByKeyword("性别");
		request.setAttribute("sexList", sexList);
	}
	
	/**ajax操作，二级联动效果，返回json数组*/
	public String findJctUnit(){
		//获取所属单位
		String jctID = elecUser.getJctID();
		//以所属单位的名称作为数据类型，查询数据字典的集合
		List<ElecSystemDDL> list = elecSystemDDLService.findSystemDDLListByKeyword(jctID);
		//要想转换成json，struts需要放置到栈顶
		ValueUtils.push(list);
		return "findJctUnit";
	}
	
	/**ajax操作，校验登录名是否出现重复*/
	public String checkUser(){
		//获取登录名
		String logonName = elecUser.getLogonName();
		/**校准
		 * 返回message变量
		 * message=1：登录名为空
		 * message=2：登录名已经存在（不运行保存）
		 * message=3：登录名可以使用（登录名不存在）
		 */
		String message = elecUserService.checkUserByLogonName(logonName);
		//放置到栈顶
		//ValueUtils.push(message);
		//将一个javabean的对象放置到栈顶
		elecUser.setMessage(message);
		return "checkUser";
	}
	
	/**保存用户*/
	public String save(){
		elecUserService.saveUser(elecUser);
		//关闭子页面，刷新父页面
		return "close";
	}
	
	/**跳转到编辑页面，表单回显*/
	public String edit(){
		//获取ID
		String userID = elecUser.getUserID();
		//一：使用主键ID，查询用户信息，放置到栈顶，完成表单回显
		ElecUser user = elecUserService.findUserById(userID);
		//放置到栈顶
		ValueUtils.push(user);
		//二：初始化页面数据字典加载的集合，遍历到页面的下拉菜单中
		this.initSystemDDL();
		//三：对“单位名称”的集合进行遍历，完成“单位名称”的回显
		//1:获取所属单位的编号
		String jctID = user.getJctID();
		//2：使用所属单位的编号和数据类型，查询对应的数据项的值
		String jctIDName = elecSystemDDLService.findDdlNameByKeywordAndDdlCode("所属单位", jctID);
		//3：使用所属单位的名称作为查询条件，查询对应数据字典的集合
		List<ElecSystemDDL> jctUnitIDList = elecSystemDDLService.findSystemDDLListByKeyword(jctIDName);
		request.setAttribute("jctUnitIDList", jctUnitIDList);
		return "edit";
	}
	
	/**文件下载，不使用struts2的方式*/
//	public String download() throws Exception{
//		//附件ID
//		String fileID = elecUser.getFileID();
//		//使用附件ID，查询附件
//		ElecUserFile elecUserFile = elecUserService.findUserFileByID(fileID);
//		//获取路径path（相对路径）
//		String path = elecUserFile.getFileURL();
//		//查找文件的绝对路径
//		FileUploadHelper fileUploadHelper = new FileUploadHelper();
//		//路径：D:\\tomcat\\apache-tomcat-7.0.52\\webapps\\image_elec\\upload\\2015\\04\\21\\用户管理
//		String basePath = fileUploadHelper.getPath()+path;
//		//获取文件
//		File baseFile = new File(basePath);
//			
//		/**设置头信息*/
//		//设置下载的数据格式类型
//		//获取文件名
//		String fileName = elecUserFile.getFileName();
//		//中文
//		fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
//		//使用文件名获取类型
//		String contentType = ServletActionContext.getServletContext().getMimeType(fileName);
//		response.setContentType(contentType);
//		//附件的操作方式（attachment，inline）
//		response.setHeader("Content-disposition", "attachment;filename="+fileName);
//			
//		//输入流
//		InputStream in = new FileInputStream(baseFile);
//		//将输入流中的文件，写到输出流
//		OutputStream out = response.getOutputStream();
//		for(int b=-1;(b=in.read())!=-1;){//读取文件从-1开始读，读到-1为止
//			out.write(b);
//		}
//		out.close();
//		in.close();
//		return NONE;//下载不需要指定页面的return NONE
//	}
	
	/**文件下载，使用struts2的方式*/
	public String download() throws Exception{
		//附件ID
		String fileID = elecUser.getFileID();
		//使用附件ID，查询附件
		ElecUserFile elecUserFile = elecUserService.findUserFileByID(fileID);
		//获取路径path（相对路径）
		String path = elecUserFile.getFileURL();
		//查找文件的绝对路径
		FileUploadHelper fileUploadHelper = new FileUploadHelper();
		//路径：D:\\tomcat\\apache-tomcat-7.0.52\\webapps\\image_elec\\upload\\2015\\04\\21\\用户管理
		String basePath = fileUploadHelper.getPath()+path;
		//获取文件
		File baseFile = new File(basePath);
			
		/**设置头信息*/
		//设置下载的数据格式类型
		//获取文件名
		String fileName = elecUserFile.getFileName();
		//中文
		fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
		//使用文件名获取类型
		String contentType = ServletActionContext.getServletContext().getMimeType(fileName);
		request.setAttribute("contentType", contentType);
		request.setAttribute("fileName", fileName);
			
		//输入流
		InputStream in = new FileInputStream(baseFile);
		//将文件的输入流放置到栈顶
		elecUser.setInputStream(in);
		return "success";
	}
	
	/**删除用户信息*/
	public String delete(){
		//获取用户ID（如果是多个值，中间使用了逗号空格的方式分开
		String userID = elecUser.getUserID();
		String userIDs [] = userID.split(", "); 
		elecUserService.deleteUserByUserID(userIDs);
		//解决重定向的request作用域失效
		request.setAttribute("pageNOdelete", request.getParameter("pageNO"));
		return "delete";
	}
	
//	/**导出excel报表，使用普通的方式导出
//	 * @throws Exception */
//	public String exportExcel() throws Exception{
//		ArrayList<String> fieldName = elecUserService.findExcelFieldName();
//		ArrayList<ArrayList<String>> fieldData = elecUserService.findExcelFieldData(elecUser);
//		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName,fieldData);
//		//获取输出流
//		OutputStream os = response.getOutputStream();
//		
//		/**文件操作，需要设置头部信息*/
//		String fileName = "用户统计报表（"+DateUtils.dateToStringExcel(new Date())+"）.xls";
//		fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
//		String contentType = ServletActionContext.getServletContext().getMimeType(fileName);
//		response.setContentType(contentType);
//		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
//		
//		//将生成的excel文件写到输出流
//		excelFileGenerator.expordExcel(os);
//		return NONE;
//	}
	
	/**导出excel报表，使用struts方式导出
	 * @throws Exception */
	public String exportExcel() throws Exception{
		ArrayList<String> fieldName = elecUserService.findExcelFieldName();
		ArrayList<ArrayList<String>> fieldData = elecUserService.findExcelFieldData(elecUser);
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName,fieldData);
		//获取输出流
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		/**文件操作，需要设置头部信息*/
		String fileName = "用户统计报表（"+DateUtils.dateToStringExcel(new Date())+"）.xls";
		fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
		request.setAttribute("fileName", fileName);
		String contentType = ServletActionContext.getServletContext().getMimeType(fileName);
		request.setAttribute("contentType", contentType);
	
		//将生成的excel文件写到输出流
		excelFileGenerator.expordExcel(os);
		byte [] buf = os.toByteArray();
		ByteArrayInputStream in = new ByteArrayInputStream(buf);
		//将输入流放置到栈顶
		elecUser.setInputStream(in);
		return "success";
	}
	
	/**跳转到导入excel页面*/
	public String importPage(){
		return "importPage";
	}

	/**从excel中读取数据，导入到数据库中
	 * @throws Exception */
	public String importData() throws Exception{
		//获取导入的excel文件
		File formFile = elecUser.getFile();
		GenerateSqlFromExcel fromExcel = new GenerateSqlFromExcel();
		ArrayList<String[]> list = fromExcel.generateUserSql(formFile);
		//添加错误信息的集合
		List<String> errorList = new ArrayList<String>();
		//将从excel文件中获取的集合ArrayList<String[]>转换成PO的集合List<ElecUser>，同时完成校验，如果校验出现问题，向errorList的集合中添加错误的数据（某行某列）
		List<ElecUser> userList = this.excelListToPOList(list,errorList);
		//存在了错误的信息，不能执行导入
		if(errorList!=null && errorList.size()>0){
			request.setAttribute("errorList", errorList);
		}
		//不存错误的信息，可以执行导入
		else{
			//组织PO的集合对象，执行批量保存
			elecUserService.saveUserList(userList);			
		}
		return "importPage";
	}

	//将从excel文件中获取的集合ArrayList<String[]>转换成PO的集合List<ElecUser>
	private List<ElecUser> excelListToPOList(ArrayList<String[]> list,List<String> errorList) {
		List<ElecUser> userList = new ArrayList<ElecUser>();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				ElecUser elecUser = new ElecUser();
				//每一行的数据（数组需要按照模板定义的顺序：登录名	密码	用户姓名	性别	所属单位	联系地址	是否在职	出生日期	职位）
				String [] o = list.get(i);
				//登录名
				if(StringUtils.isNotBlank(o[0])){
					String message = elecUserService.checkUserByLogonName(o[0]);
					if(message!=null && message.equals("3")){
						elecUser.setLogonName(o[0]);						
					}
					else{
						errorList.add("第"+(i+2)+"行，第"+(0+1)+"列，登录名在数据库中已经存在！");
					}
				}
				else{
					errorList.add("第"+(i+2)+"行，第"+(0+1)+"列，登录名不能为空！");
				}
				//密码
				if(StringUtils.isNotBlank(o[1])){
					MD5keyBean bean = new MD5keyBean();
					String logonPwd = bean.getkeyBeanofStr(o[1]);
					elecUser.setLogonPwd(logonPwd);
				}
				//用户姓名
				if(StringUtils.isNotBlank(o[2])){
					elecUser.setUserName(o[2]);
				}
				//性别（转换）
				if(StringUtils.isNotBlank(o[3])){
					//使用数据类型和数据项的值，获取数据项的编号
					String ddlCode = elecSystemDDLService.findDdlCodeByKeywordAndDdlName("性别", o[3]);
					if(StringUtils.isNotBlank(ddlCode)){
						elecUser.setSexID(ddlCode);						
					}
					else{
						errorList.add("第"+(i+2)+"行，第"+(3+1)+"列，性别在数据字典的转换中出现问题！");
					}
				}
				else{
					errorList.add("第"+(i+2)+"行，第"+(3+1)+"列，性别不能为空！");
				}
				//所属单位
				if(StringUtils.isNotBlank(o[4])){
					//使用数据类型和数据项的值，获取数据项的编号
					String ddlCode = elecSystemDDLService.findDdlCodeByKeywordAndDdlName("所属单位", o[4]);
					if(StringUtils.isNotBlank(ddlCode)){
						elecUser.setJctID(ddlCode);						
					}
					else{
						errorList.add("第"+(i+2)+"行，第"+(4+1)+"列，所属单位在数据字典的转换中出现问题！");
					}
				}
				else{
					errorList.add("第"+(i+2)+"行，第"+(4+1)+"列，所属单位不能为空！");
				}
				//联系地址
				if(StringUtils.isNotBlank(o[5])){
					elecUser.setAddress(o[5]);
				}
				//是否在职
				if(StringUtils.isNotBlank(o[6])){
					//使用数据类型和数据项的值，获取数据项的编号
					String ddlCode = elecSystemDDLService.findDdlCodeByKeywordAndDdlName("是否在职", o[6]);
					if(StringUtils.isNotBlank(ddlCode)){
						elecUser.setIsDuty(ddlCode);						
					}
					else{
						errorList.add("第"+(i+2)+"行，第"+(6+1)+"列，是否在职在数据字典的转换中出现问题！");
					}
				}
				else{
					errorList.add("第"+(i+2)+"行，第"+(6+1)+"列，是否在职不能为空！");
				}
				//出生日期
				if(StringUtils.isNotBlank(o[7])){
					Date birthday = DateUtils.stringToDate(o[7]);
					elecUser.setBirthday(birthday);
				}
				//职位
				if(StringUtils.isNotBlank(o[8])){
					//使用数据类型和数据项的值，获取数据项的编号
					String ddlCode = elecSystemDDLService.findDdlCodeByKeywordAndDdlName("职位", o[8]);
					if(StringUtils.isNotBlank(ddlCode)){
						elecUser.setPostID(ddlCode);						
					}
					else{
						errorList.add("第"+(i+2)+"行，第"+(8+1)+"列，职位在数据字典的转换中出现问题！");
					}
				}
				else{
					errorList.add("第"+(i+2)+"行，第"+(8+1)+"列，职位不能为空！");
				}
				userList.add(elecUser);
			}
		}
		return userList;
	}
	
	/**使用FusitionChartFree完成人员的统计*/
	public String chartUserFCF(){
		//核心组织xml的数据
		//查询数据库，获取图形需要数据集合（用户管理的统计项）
		List<Object[]> list = elecUserService.findChartDataSetByUser("性别","sexID");
		//组织XML的数据
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
				Object[] objects = (Object[])list.get(i);
				if(i==0){//组织第一个值
					String x = "男女比例统计";
					String y = "unit";//存在FusionChart中的一个问题，Y轴的显示不支持中文，所以我们用英文代替
					builder.append("<graph caption='用户统计报表("+objects[0].toString()+")' xAxisName='"+x+"' bgColor='FFFFDD' yAxisName='"+y+"' showValues='1'  decimals='0' baseFontSize='18'  maxColWidth='60' showNames='1' decimalPrecision='0'> ");
					builder.append("<set name='"+objects[1].toString()+"' value='"+objects[2].toString()+"' color='AFD8F8'/>");
				}
			    if(i==list.size()-1){//组织最后一个值
			    	builder.append("<set name='"+objects[1].toString()+"' value='"+objects[2].toString()+"' color='B3AA00'/>");
			    	builder.append("</graph>");
			    }
		} 
		request.setAttribute("chart", builder);
		return "chartUserFCF";
	}
	
	/**跳转到highChart报表生成的页面上*/
	public String chartUserHighChart(){
		return "chartUserHighChart";
	}
	
	/**ajax调用，返回json的数据形式，用来加载highchart的数据*/
	public String highChartUser(){
		//调用所属单位的数据
		//查询数据库，获取图形需要数据集合（用户管理的统计项）
		List<Object[]> list = elecUserService.findChartDataSetByUser("所属单位","jctID");
		//json的数组
		List<HighChartForm> chartList = new ArrayList<HighChartForm>();
		//b.keyword,b.ddlname,count(b.Ddlcode)
		if(list!=null && list.size()>0){
			for(Object [] o:list){
				HighChartForm highChartForm = new HighChartForm();
				highChartForm.setName(o[1].toString());
				highChartForm.setData(o[2].toString());
				chartList.add(highChartForm);
			}
		}
		//放置到栈顶
		ValueUtils.push(chartList);
		return "highChartUser";
	}
}
