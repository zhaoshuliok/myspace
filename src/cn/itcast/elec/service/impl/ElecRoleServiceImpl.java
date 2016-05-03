package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecPopedomDao;
import cn.itcast.elec.dao.IElecRoleDao;
import cn.itcast.elec.dao.IElecRolePopedomDao;
import cn.itcast.elec.dao.IElecUserDao;
import cn.itcast.elec.domain.ElecPopedom;
import cn.itcast.elec.domain.ElecRole;
import cn.itcast.elec.domain.ElecRolePopedom;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecRoleService;

@Service(IElecRoleService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecRoleServiceImpl implements IElecRoleService {

	/**角色*/
	@Resource(name=IElecRoleDao.SERVICE_NAME)
	private IElecRoleDao elecRoleDao;
	
	/**权限*/
	@Resource(name=IElecPopedomDao.SERVICE_NAME)
	private IElecPopedomDao elecPopedomDao;

	/**角色权限*/
	@Resource(name=IElecRolePopedomDao.SERVICE_NAME)
	private IElecRolePopedomDao elecRolePopedomDao;
	
	/**用户*/
	@Resource(name=IElecUserDao.SERVICE_NAME)
	private IElecUserDao elecUserDao;

	/**查询系统中所有的角色*/
	@RequiresPermissions("ao:findRoleList")
	public List<ElecRole> findRoleList() {
		Map<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.roleID", "asc");
		List<ElecRole> list = elecRoleDao.findCollectionByConditionNoPage("", null, orderby);
		return list;
	}
	
	/**查询系统中所有的功能权限*/
	public List<ElecPopedom> findPopedomList() {
		//1：查询pid=0的权限节点的数据，遍历tr
		String condition = " and o.pid = ?";
		Object [] params = {"0"};
		Map<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id", "asc");
		List<ElecPopedom> list = elecPopedomDao.findCollectionByConditionNoPage(condition, params, orderby);
		//2：在td中遍历父节点对应的子节点（递归：父子关系）
		this.findChildList(list);
		return list;
	}

	//查询当前父节点中对应的子节点的集合
	private void findChildList(List<ElecPopedom> list) {
		//遍历父节点的集合
		if(list!=null && list.size()>0){
			for(ElecPopedom elecPopedom:list){
				//父的id
				String id = elecPopedom.getId();
				//以父节点id作为pid为查询条件查询
				String condition = " and o.pid = ?";
				Object [] params = {id};
				Map<String, String> orderby = new LinkedHashMap<String, String>();
				orderby.put("o.id", "asc");
				List<ElecPopedom> childList = elecPopedomDao.findCollectionByConditionNoPage(condition, params, orderby);
				//将子集合存放到父的对象中
				elecPopedom.setChildList(childList);
				//递归
				if(childList!=null && childList.size()>0){
					this.findChildList(childList);
				}
			}
		}
	}
	
	/**
	 * 一：使用选择的角色（传递角色ID），查询系统中所有的权限
	 	* 如果当前角色具有该权限，页面的复选框被选中
	 */
	public List<ElecPopedom> findPopedomListByRoleID(String roleID) {
		//1：查询系统中所有的功能权限
		List<ElecPopedom> list = this.findPopedomList();
		//2：使用当前角色，查询当前角色具有的权限（角色权限表）,将权限的Code存放到字符串中（或者List集合）
		String condition = " and o.roleID = ?";
		Object [] params = {roleID};
		List<ElecRolePopedom> rolePopedomList = elecRolePopedomDao.findCollectionByConditionNoPage(condition, params, null);
		//存放权限的code，中间使用使用@符号分开
		StringBuffer buffer = new StringBuffer();
		if(rolePopedomList!=null && rolePopedomList.size()>0){
			for(ElecRolePopedom elecRolePopedom:rolePopedomList){
				buffer.append(elecRolePopedom.getId()).append("@");
			}
			//去掉最后一个@
			buffer.deleteCharAt(buffer.length()-1);
		}
		//封装当前角色具有的权限字符串（用于匹配），格式：aa@ab@ac
		String popedom = buffer.toString();
		/**
		 * 3：完成匹配（使用递归）
		     * 因为返回页面的集合是List<ElecPopedom>，所以在ElecPopedom对象中添加一个flag属性
		       flag=1：页面的复选框被选中
		       flag=2：页面的复选框不被选中
		 */
		this.findPopedomListByPopedomAndList(popedom,list);
		return list;
	}

	/**
	 * 完成匹配（使用递归）
		     * 因为返回页面的集合是List<ElecPopedom>，所以在ElecPopedom对象中添加一个flag属性
		       flag=1：页面的复选框被选中
		       flag=2：页面的复选框不被选中
	 */
	private void findPopedomListByPopedomAndList(String popedom,
			List<ElecPopedom> list) {
		//遍历的父的集合
		if(list!=null && list.size()>0){
			for(ElecPopedom elecPopedom:list){
				//获取权限的id
				String id = elecPopedom.getId();
				//匹配
				if(popedom.contains(id)){
					elecPopedom.setFlag("1");//表示页面的复选框被选中
				}
				else{
					elecPopedom.setFlag("2");//表示页面的复选框不被选中
				}
				//获取子的集合
				List<ElecPopedom> childList = elecPopedom.getChildList();
				if(childList!=null && childList.size()>0){
					this.findPopedomListByPopedomAndList(popedom, childList);
				}
			}
		}
	}
	
	/**
	 * 二：使用选择的角色（传递角色ID），查询系统中所有的用户
	 * 如果当前角色具有该用户，页面的复选框被选中
	 */
	public List<ElecUser> findUserListByRoleID(String roleID) {
		//1：查询系统中所有的功能用户
		Map<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.userID", "asc");
		List<ElecUser> userList = elecUserDao.findCollectionByConditionNoPage("", null, orderby);
		//2：使用当前角色，查询当前角色具有的用户（用户角色表），将用户ID存放到集合中
		//使用角色ID，查询角色对象
		ElecRole elecRole = elecRoleDao.findObjectByID(roleID);
		//获取当前角色具有的用户集合
		Set<ElecUser> elecUsers = elecRole.getElecUsers();
		
		/**方案一：*/
//		//使用List<String>存放用户ID
//		List<String> userIDList = new ArrayList<String>();
//		if(elecUsers!=null && elecUsers.size()>0){
//			for(ElecUser elecUser:elecUsers){
//				userIDList.add(elecUser.getUserID());
//			}
//		}
//		/**
//		 * 3：完成匹配
//		     * 因为返回页面的集合是List<ElecUser>，所以在ElecUser对象中添加一个flag属性
//		       flag=1：页面的复选框被选中
//		       flag=2：页面的复选框不被选中
//		 */
//		//遍历所有的集合
//		if(userList!=null && userList.size()>0){
//			for(ElecUser elecUser:userList){
//				//获取用户ID
//				String userID = elecUser.getUserID();
//				//匹配
//				if(userIDList.contains(userID)){
//					elecUser.setFlag("1");
//				}
//				else{
//					elecUser.setFlag("2");
//				}
//			}
//		}
		/**方案二*/
		if(userList!=null && userList.size()>0){
			for(ElecUser elecUser:userList){
				//获取用户ID
				String userID = elecUser.getUserID();
				if(elecUsers!=null && elecUsers.size()>0){
					for(ElecUser user:elecUsers){
						//获取用户ID
						String uID = user.getUserID();
						//匹配
						if(userID.equals(uID)){
							elecUser.setFlag("1");
							//如果一旦匹配成功
							break;
						}
						else{
							elecUser.setFlag("2");
						}
					}
				}
			}
		}
		return userList;
	}
	
	/**保存角色权限关联表，保存用户角色关联表*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(ElecRole elecRole) {
		//获取页面的角色ID
		String roleID = elecRole.getRoleID();
		//获取页面的权限id（格式id_pid)
		String [] selectopers = elecRole.getSelectoper();
		//获取页面的用户ID
		String [] selectusers = elecRole.getSelectuser();
		/**一：操作角色权限关联表*/
		this.saveRolePopedom(roleID,selectopers);
		/**二：操作用户角色关联表*/
		this.saveUserRole(roleID,selectusers);
	}

	/**二：操作用户角色关联表*/
	private void saveUserRole(String roleID, String[] selectusers) {
		//1：使用角色ID，查询角色对象
		ElecRole elecRole = elecRoleDao.findObjectByID(roleID);
		/**方案一*/
//		//2：获取当前角色具有的用户集合
//		Set<ElecUser> elecUsers = elecRole.getElecUsers();//持久化对象
//		//先删除
//		elecUsers.clear();
//		//再创建
//		if(selectusers!=null && selectusers.length>0){
//			for(String userID:selectusers){
//				ElecUser elecUser = new ElecUser();
//				elecUser.setUserID(userID);
//				elecUsers.add(elecUser);
//			}
//		}
		/**方案二*/
		Set<ElecUser> elecUsers = new HashSet<ElecUser>();
		if(selectusers!=null && selectusers.length>0){
			for(String userID:selectusers){
				ElecUser elecUser = new ElecUser();
				elecUser.setUserID(userID);
				elecUsers.add(elecUser);
			}
		}
		elecRole.setElecUsers(elecUsers);//替换数据
	}

	/**一：操作角色权限关联表*/
	private void saveRolePopedom(String roleID, String[] selectopers) {
		//1：使用角色ID，查询当前角色具有的权限
		String condition = " and o.roleID = ?";
		Object [] params = {roleID};
		List<ElecRolePopedom> rolePopedomList = elecRolePopedomDao.findCollectionByConditionNoPage(condition, params, null);
		//2：删除当前角色具有的权限
		elecRolePopedomDao.deleteObjectByCollection(rolePopedomList);
		//3：建立新的关联关系，遍历数组，组织PO对象，执行保持
		if(selectopers!=null && selectopers.length>0){
			for(String ids:selectopers){
				String [] id = ids.split("_");
				//组织PO对象
				ElecRolePopedom elecRolePopedom = new ElecRolePopedom();
				elecRolePopedom.setRoleID(roleID);
				elecRolePopedom.setId(id[0]);
				elecRolePopedom.setPid(id[1]);
				//执行保存
				elecRolePopedomDao.save(elecRolePopedom);
			}
		}
	}
	
	/**使用当前用户的登录名，查询角色的集合*/
	public List<ElecRole> findRoleListByLogonName(String logonName) {
		return elecRoleDao.findRoleListByLogonName(logonName);
	}
	
	/**使用角色的集合，获取权限的数据*/
	public List<ElecPopedom> findPopedomListByRoleIds(List<ElecRole> roleList) {
		/**sql = select distinct a.id from elec_role_popedom a where a.roleid in ('2','3')*/
		StringBuffer buffer = new StringBuffer("");
		if(roleList!=null && roleList.size()>0){
			for(ElecRole elecRole:roleList){
				buffer.append("'").append(elecRole.getRoleID()).append("'").append(",");
			}
			//删除最后一个逗号
			buffer.deleteCharAt(buffer.length()-1);
		}
		//格式：'2','3'
		String roleCondition = buffer.toString();
		//查询
		return elecRolePopedomDao.findPopedomListByRoleIds(roleCondition);
	}
	
	/**使用查询条件（登录名和参数）获取当前用户具有的权限*/
	public List<ElecPopedom> findPopedomListByCondition(String logonName,
			String generatemenu) {
		return elecPopedomDao.findPopedomListByCondition(logonName,generatemenu);
	}
}
