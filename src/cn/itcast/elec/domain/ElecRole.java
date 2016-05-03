package cn.itcast.elec.domain;

import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("serial")
public class ElecRole implements java.io.Serializable {
	
	private String roleID;		//主键ID
	private String roleName;	//角色名称
	
	private Set<ElecUser> elecUsers = new HashSet<ElecUser>();
		
	public Set<ElecUser> getElecUsers() {
		return elecUsers;
	}
	public void setElecUsers(Set<ElecUser> elecUsers) {
		this.elecUsers = elecUsers;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	/********非持久化javabean属性******/
	//页面选中的权限id（格式：id_pid）
	private String [] selectoper;
	//页面选中的用户ID
	private String [] selectuser;

	public String[] getSelectoper() {
		return selectoper;
	}
	public void setSelectoper(String[] selectoper) {
		this.selectoper = selectoper;
	}
	public String[] getSelectuser() {
		return selectuser;
	}
	public void setSelectuser(String[] selectuser) {
		this.selectuser = selectuser;
	}
	
}
