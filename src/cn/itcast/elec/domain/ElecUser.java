package cn.itcast.elec.domain;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class ElecUser implements java.io.Serializable {
	
	private String userID;		//主键ID
	private String jctID;		//所属单位code
	private String jctUnitID;	//所属单位的单位名称（联动）
	private String userName;	//用户姓名
	private String logonName;	//登录名
	private String logonPwd;	//密码
	private String sexID;		//性别
	private Date birthday;		//出生日期
	private String address;		//联系地址
	private String contactTel;	//联系电话 
	private String email;		//电子邮箱
	private String mobile;		//手机
	private String isDuty;		//是否在职
	private String postID;      //职位
	private Date onDutyDate;	//入职时间
	private Date offDutyDate;	//离职时间
	private String remark;		//备注
	
	
	private Set<ElecUserFile> elecUserFiles = new HashSet<ElecUserFile>();

	public Set<ElecUserFile> getElecUserFiles() {
		return elecUserFiles;
	}
	public void setElecUserFiles(Set<ElecUserFile> elecUserFiles) {
		this.elecUserFiles = elecUserFiles;
	}

	private Set<ElecRole> elecRoles = new HashSet<ElecRole>();
	
	public Set<ElecRole> getElecRoles() {
		return elecRoles;
	}
	public void setElecRoles(Set<ElecRole> elecRoles) {
		this.elecRoles = elecRoles;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getJctID() {
		return jctID;
	}
	public void setJctID(String jctID) {
		this.jctID = jctID;
	}
	public String getJctUnitID() {
		return jctUnitID;
	}
	public void setJctUnitID(String jctUnitID) {
		this.jctUnitID = jctUnitID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogonName() {
		return logonName;
	}
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}
	public String getLogonPwd() {
		return logonPwd;
	}
	public void setLogonPwd(String logonPwd) {
		this.logonPwd = logonPwd;
	}
	public String getSexID() {
		return sexID;
	}
	public void setSexID(String sexID) {
		this.sexID = sexID;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIsDuty() {
		return isDuty;
	}
	public void setIsDuty(String isDuty) {
		this.isDuty = isDuty;
	}
	public String getPostID() {
		return postID;
	}
	public void setPostID(String postID) {
		this.postID = postID;
	}
	public Date getOnDutyDate() {
		return onDutyDate;
	}
	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}
	public Date getOffDutyDate() {
		return offDutyDate;
	}
	public void setOffDutyDate(Date offDutyDate) {
		this.offDutyDate = offDutyDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**非持久化的javabean属性*/
	private Date onDutyDateBegin;
	
	private Date onDutyDateEnd;
	
	//添加message的属性，在校验登录名是否存在
	private String message;

	/**文件上传*/
	//上传的文件
	private File [] uploads;
	//上传的文件名
	private String [] uploadsFileName;
	//上传的文件类型
	private String [] uploadsContentType;
	
	//附件ID
	private String fileID;
	
	//输入流的属性（文件下载）
	private InputStream inputStream;
	
	//用来存放修改用户之前的密码
	private String password;
	
	/**
     * 因为返回页面的集合是List<ElecUser>，所以在ElecUser对象中添加一个flag属性
       flag=1：页面的复选框被选中
       flag=2：页面的复选框不被选中
	 * @return
	 */
	private String flag;
	
	//导入的excel文件
	private File file;
	
	
	
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public File[] getUploads() {
		return uploads;
	}
	public void setUploads(File[] uploads) {
		this.uploads = uploads;
	}
	public String[] getUploadsFileName() {
		return uploadsFileName;
	}
	public void setUploadsFileName(String[] uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}
	public String[] getUploadsContentType() {
		return uploadsContentType;
	}
	public void setUploadsContentType(String[] uploadsContentType) {
		this.uploadsContentType = uploadsContentType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getOnDutyDateBegin() {
		return onDutyDateBegin;
	}
	public void setOnDutyDateBegin(Date onDutyDateBegin) {
		this.onDutyDateBegin = onDutyDateBegin;
	}
	public Date getOnDutyDateEnd() {
		return onDutyDateEnd;
	}
	public void setOnDutyDateEnd(Date onDutyDateEnd) {
		this.onDutyDateEnd = onDutyDateEnd;
	}
	
	
	
	
}
