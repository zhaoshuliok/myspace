package cn.itcast.elec.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.wsdl.http.UrlEncoded;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecExportFieldsDao;
import cn.itcast.elec.dao.IElecUserDao;
import cn.itcast.elec.dao.IElecUserFileDao;
import cn.itcast.elec.domain.ElecExportFields;
import cn.itcast.elec.domain.ElecRole;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.domain.ElecUserFile;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.util.FileUploadHelper;
import cn.itcast.elec.util.FileUploadUtils;
import cn.itcast.elec.util.ListUtils;
import cn.itcast.elec.util.MD5keyBean;
import cn.itcast.elec.util.PageInfo;

@Service(IElecUserService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecUserServiceImpl implements IElecUserService {

	/**用户*/
	@Resource(name=IElecUserDao.SERVICE_NAME)
	private IElecUserDao elecUserDao;
	/**用户附件*/
	@Resource(name=IElecUserFileDao.SERVICE_NAME)
	private IElecUserFileDao elecUserFileDao;
	/**导出设置*/
	@Resource(name=IElecExportFieldsDao.SERVICE_NAME)
	private IElecExportFieldsDao elecExportFieldsDao;
	
	/**使用查询条件，查询用户的集合，返回List*/
	public List<ElecUser> findUserListByCondition(ElecUser elecUser) {
		//查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//用户名
		String userName = elecUser.getUserName();
		if(StringUtils.isNotBlank(userName)){
			condition += " and o.userName like ?";
			paramsList.add("%"+userName+"%");
		}
		//所属单位
		String jctID = elecUser.getJctID();
		if(StringUtils.isNotBlank(jctID)){
			condition += " and o.jctID = ?";
			paramsList.add(jctID);
		}
		//入职开始时间
		Date onDutyDateBegin = elecUser.getOnDutyDateBegin();
		if(onDutyDateBegin!=null){
			condition += " and o.onDutyDate >= ?";
			paramsList.add(onDutyDateBegin);
		}
		//入职结束时间
		Date onDutyDateEnd = elecUser.getOnDutyDateEnd();
		if(onDutyDateEnd!=null){
			condition += " and o.onDutyDate <= ?";
			paramsList.add(onDutyDateEnd);
		}
		//转换成Object数组
		Object [] params = paramsList.toArray();
		/**排序*/
		Map<String, String> orderby = new  LinkedHashMap<String, String>();
		orderby.put("o.userID", "asc");
		//不分页
		//List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, orderby);
		/**添加分页 begin*/
		PageInfo pageInfo = new PageInfo(ServletActionContext.getRequest());
		List<ElecUser> list = elecUserDao.findCollectionByConditionWithPage(condition, params, orderby,pageInfo);
		//放置到request中
		ServletActionContext.getRequest().setAttribute("page", pageInfo.getPageBean());
		/**添加分页 end*/
		return list;
	}
	
	/**使用查询条件，查询用户的集合，返回List*/
	public List<ElecUser> findUserListByConditionWithSql(ElecUser elecUser) {
		//查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//用户名
		String userName = elecUser.getUserName();
		if(StringUtils.isNotBlank(userName)){
			condition += " and a.userName like ?";
			paramsList.add("%"+userName+"%");
		}
		//所属单位
		String jctID = elecUser.getJctID();
		if(StringUtils.isNotBlank(jctID)){
			condition += " and a.jctID = ?";
			paramsList.add(jctID);
		}
		//入职开始时间
		Date onDutyDateBegin = elecUser.getOnDutyDateBegin();
		if(onDutyDateBegin!=null){
			condition += " and a.onDutyDate >= ?";
			paramsList.add(onDutyDateBegin);
		}
		//入职结束时间
		Date onDutyDateEnd = elecUser.getOnDutyDateEnd();
		if(onDutyDateEnd!=null){
			condition += " and a.onDutyDate <= ?";
			paramsList.add(onDutyDateEnd);
		}
		//转换成Object数组
		Object [] params = paramsList.toArray();
		/**排序*/
		Map<String, String> orderby = new  LinkedHashMap<String, String>();
		orderby.put("a.userID", "asc");
		//不分页
		//List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, orderby);
		/**添加分页 begin*/
		PageInfo pageInfo = new PageInfo(ServletActionContext.getRequest());
		List<ElecUser> list = elecUserDao.findCollectionByConditionWithPageAndSql(condition, params, orderby,pageInfo);
		//放置到request中
		ServletActionContext.getRequest().setAttribute("page", pageInfo.getPageBean());
		/**添加分页 end*/
		return list;
	}
	
	/**校准
	 * 返回message变量
	 * message=1：登录名为空
	 * message=2：登录名已经存在（不运行保存）
	 * message=3：登录名可以使用（登录名不存在）
	 */
	public String checkUserByLogonName(String logonName) {
		String message = "";
		if(StringUtils.isNotBlank(logonName)){
			String condition = " and o.logonName = ? ";
			Object [] params = {logonName};
			List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, null);
			if(list!=null && list.size()>0){
				message = "2";
			}
			else{
				message = "3";
			}
		}
		else{
			message = "1";
		}
		return message;
	}
	
	/**
	 * 保存用户信息
	 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveUser(ElecUser elecUser) {
		/**完成MD5的加密*/
		this.md5Password(elecUser);
		//一：组织PO对象，操作保存用户表
		//获取用户ID
		String userID = elecUser.getUserID();
		if(StringUtils.isBlank(userID)){
			elecUserDao.save(elecUser);			
		}
		else{
			elecUserDao.update(elecUser);
		}
		//二：遍历组织附件的PO对象，操作保存附件表
		this.saveUserFile(elecUser);
	}

	/**使用md5加密，完成对用户信息的操作*/
	private void md5Password(ElecUser elecUser) {
		String logonPwd = elecUser.getLogonPwd();
		String md5Password = "";
		
		//初始密码123
		if(StringUtils.isBlank(logonPwd)){
			logonPwd = "123";
		}
		//获取“用来存放修改用户之前的密码”
		String password = elecUser.getPassword();
		//表示了没有修改密码，不用加密
		if(password!=null && password.equals(logonPwd)){
			md5Password = password;
		}
		//需要加密
		else{
			MD5keyBean bean = new MD5keyBean();
			md5Password = bean.getkeyBeanofStr(logonPwd);			
		}
		
		//将md5Password放置到PO对象中
		elecUser.setLogonPwd(md5Password);
	}

	//二：遍历组织附件的PO对象，操作保存附件表
	private void saveUserFile(ElecUser elecUser) {
		Date progressTime = new Date();
		//获取上传的附件
		File [] uploads = elecUser.getUploads();
		//获取上传的附件名称
		String [] uploadsFileNames = elecUser.getUploadsFileName();
		//获取上传的附件类型
		String [] uploadsContentTypes = elecUser.getUploadsContentType();
		if(uploads!=null && uploads.length>0){
			for(int i=0;i<uploads.length;i++){
				ElecUserFile elecUserFile = new ElecUserFile();
				elecUserFile.setFileName(uploadsFileNames[i]);
				/**
				 * 路径存放相对路径
				 * 上传附件的同时，返回路径path
				 */
				String fileURL = FileUploadUtils.fileUploadReturnPath(uploads[i], uploadsFileNames[i], "用户管理");
				elecUserFile.setFileURL(fileURL);
				elecUserFile.setProgressTime(progressTime);
				elecUserFile.setElecUser(elecUser);//一对多，多的一端需要设置一的一端
				elecUserFileDao.save(elecUserFile);
			}
		}
	}
	
	/**使用主键ID，查询用户信息*/
	public ElecUser findUserById(String userID) {
		ElecUser elecUser = elecUserDao.findObjectByID(userID);
		//elecUser.getElecUserFiles().size();
		return elecUser;
	}
	
	/**使用附件ID，查询附件的信息*/
	public ElecUserFile findUserFileByID(String fileID) {
		return elecUserFileDao.findObjectByID(fileID);
	}
	
	/**使用用户ID，删除用户的信息*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteUserByUserID(String[] userIDs) {
		//1：删除用户表，删除附件表，删除附件
		//使用用户ID，查询用户信息
		if(userIDs!=null && userIDs.length>0){
			for(String userID:userIDs){
				ElecUser elecUser = elecUserDao.findObjectByID(userID);
				/**如果用户被分配了角色，同时删除用户角色中间表的数据*/
				Set<ElecRole> elecRoles = elecUser.getElecRoles();
				if(elecRoles!=null && elecRoles.size()>0){
					for(ElecRole elecRole:elecRoles){
						//获取当前角色具有的用户
						Set<ElecUser> elecUsers = elecRole.getElecUsers();
						elecUsers.remove(elecUser);//只删除集合中的某一个
					}
				}
				
				//获取附件的集合
				Set<ElecUserFile> elecUserFiles = elecUser.getElecUserFiles();
				if(elecUserFiles!=null && elecUserFiles.size()>0){
					for(ElecUserFile elecUserFile:elecUserFiles){
						
						//查找路径，删除附件
						String path = elecUserFile.getFileURL();
						FileUploadHelper fileUploadHelper = new FileUploadHelper();
						String basePath = fileUploadHelper.getPath()+path;
						File file = new File(basePath);
						if(file.exists()){
							file.delete();
						}
						//方案一：删除附件表
						//elecUserFileDao.deleteObjectByIDs(elecUserFile.getFileID());
						//方案二：<set name="elecUserFiles" table="Elec_User_File" inverse="true" order-by="progressTime asc" cascade="delete">
					}
				}
			}
		}
		//删除用户表
		elecUserDao.deleteObjectByIDs(userIDs);
	}
	
	/**使用登录名作为查询条件，查询对应登录名的用户对象*/
	public ElecUser findUserByLogonName(String name) {
		//查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(name)){
			condition += " and o.logonName = ?";
			paramsList.add(name);
		}
		Object [] params = paramsList.toArray();
		//查询
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, null);
		//因为只有一个对象
		ElecUser elecUser = null;
		if(list!=null && list.size()>0){
			elecUser = list.get(0);
		}
		return elecUser;
	}
	
	/**使用职位ID查询对应的用户集合，组任务*/
	public List<ElecUser> findUserListByPostID(String postID) {
		String condition = " and o.postID = ?";
		Object[] params = {postID};
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.onDutyDate", "asc");
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, orderby);
		return list;

	}
	
	/**获取excel的标题数据*/
	/**
	 * ArrayList<String> fieldName;
		  * excel标题数据
		  * fieldName.add("登录名");
		    fieldName.add("用户姓名");
	 */
	public ArrayList<String> findExcelFieldName() {
		ElecExportFields elecExportFields = elecExportFieldsDao.findObjectByID("5-1");
		//获取导出的中文字段
		String zName = elecExportFields.getExpNameList();
		ArrayList<String> fieldName = (ArrayList<String>) ListUtils.stringToList(zName);
		return fieldName;
	}
	
	/**获取excel的内容数据*/
	/**
	 * ArrayList<ArrayList<String>> fieldData;//存放所有行的数据
		  * excel内容数据
		  * ArrayList<String> data;//用来存放每一行的数据
		    data.add("liubei");
		    data.add("刘备");
		  * fieldData.add(data);
	 */
	public ArrayList<ArrayList<String>> findExcelFieldData(ElecUser elecUser) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		//1：查询导出设置表，获取导出英文字段，作为查询sql语句条件的投影部分
		ElecExportFields elecExportFields = elecExportFieldsDao.findObjectByID("5-1");
		//获取导出的英文字段(a.logonName#a.userName#d.ddlName as d#b.ddlName as b#c.ddlName as c#e.ddlName as e#a.birthday#a.onDutyDate)
		String zName = elecExportFields.getExpFieldName();
		String selectCondition = zName.replace("#", ",");
		//2：组织查询条件，查询结果
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//用户名
		String userName = elecUser.getUserName();
		try {
			/**
			 * 方案一：
			 * 页面：userName = encodeURI(userName,"UTF-8");
			 * 服务器：userName = new String(userName.getBytes("iso-8859-1"),"UTF-8");
			 */
			//userName = new String(userName.getBytes("iso-8859-1"),"UTF-8");
			/**
			 * 方案二：
			 * 页面：    userName = encodeURI(userName,"UTF-8");
			 * 		 userName = encodeURI(userName,"UTF-8");
			 *     相当于使用了：
			 *       URLEncoder.encode(userName, "UTF-8");
			 * 服务器：
			 */
			userName = URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(userName)){
			condition += " and a.userName like ?";
			paramsList.add("%"+userName+"%");
		}
		//所属单位
		String jctID = elecUser.getJctID();
		if(StringUtils.isNotBlank(jctID)){
			condition += " and a.jctID = ?";
			paramsList.add(jctID);
		}
		//入职开始时间
		Date onDutyDateBegin = elecUser.getOnDutyDateBegin();
		if(onDutyDateBegin!=null){
			condition += " and a.onDutyDate >= ?";
			paramsList.add(onDutyDateBegin);
		}
		//入职结束时间
		Date onDutyDateEnd = elecUser.getOnDutyDateEnd();
		if(onDutyDateEnd!=null){
			condition += " and a.onDutyDate <= ?";
			paramsList.add(onDutyDateEnd);
		}
		//转换成Object数组
		Object [] params = paramsList.toArray();
		/**排序*/
		Map<String, String> orderby = new  LinkedHashMap<String, String>();
		orderby.put("a.userID", "asc");
		//查询数据（查询的所有的数据）
		List list = elecUserDao.findExcelFieldData(condition,params,orderby,selectCondition);
		//3：组织需要ArrayList<ArrayList<String>>
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				//定义一个数组，用来存放每一行的数据
				Object [] arrays = null;
				//返回的是List<Object[]> 
				if(selectCondition.contains(",")){
					arrays = (Object[]) list.get(i);
				}
				//返回的是List <Object>
				else{
					arrays = new Object[1];
					arrays[0] = list.get(i);
				}
				//遍历每一行的数据
				//存放每一行的数据
				ArrayList<String> data = new ArrayList<String>();
				if(arrays!=null && arrays.length>0){
					for(Object o:arrays){
						data.add(o!=null?o.toString():"");
					}
				}
				fieldData.add(data);
			}
		}
		return fieldData;
	}
	
	/**保存用户的集合*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveUserList(List<ElecUser> userList) {
		elecUserDao.saveCollection(userList);
	}
	
	/**人员统计（数据字典的项）*/
	public List<Object[]> findChartDataSetByUser(String zName, String eName) {
		return elecUserDao.findChartDataSetByUser(zName,eName);
	}
}
