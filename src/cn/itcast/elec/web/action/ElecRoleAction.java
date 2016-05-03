package cn.itcast.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecPopedom;
import cn.itcast.elec.domain.ElecRole;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecRoleService;

@Controller("elecRoleAction")
@Scope("prototype")
public class ElecRoleAction extends BaseAction<ElecRole> {

	private ElecRole elecRole = this.getModel();

	/**角色*/
	@Resource(name=IElecRoleService.SERVICE_NAME)
	private IElecRoleService elecRoleService;
	
	/**角色管理的首页显示*/
	@RequiresPermissions(value="ao")
	public String home(){
		/**一：查询系统中所有的角色，返回List<ElecRole>遍历在角色类型下拉菜单中*/
		List<ElecRole> roleList = elecRoleService.findRoleList();
		request.setAttribute("roleList", roleList);
		/**二：查询系统中所有的权限，返回List<ElecPopedom>遍历在页面的table中*/
		List<ElecPopedom> popedomList = elecRoleService.findPopedomList();
		request.setAttribute("popedomList", popedomList);
		return "home";
	}
	
	/**跳转到角色管理的编辑页面*/
	public String edit(){
		//获取角色ID
		String roleID = elecRole.getRoleID();
		/**
		 * 一：使用选择的角色（传递角色ID），查询系统中所有的权限
		 * 如果当前角色具有该权限，页面的复选框被选中
		 */
		List<ElecPopedom> popedomList = elecRoleService.findPopedomListByRoleID(roleID);
		request.setAttribute("popedomList", popedomList);
		/**
		 * 二：使用选择的角色（传递角色ID），查询系统中所有的用户
		 * 如果当前角色具有该用户，页面的复选框被选中
		 */
		List<ElecUser> userList = elecRoleService.findUserListByRoleID(roleID);
		request.setAttribute("userList", userList);
		return "edit";
	}
	
	/**保存*/
	public String save(){
		elecRoleService.save(elecRole);
		return "save";
	}
}
