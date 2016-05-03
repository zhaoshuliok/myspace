package cn.itcast.elec.util;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import cn.itcast.elec.domain.ElecPopedom;
import cn.itcast.elec.domain.ElecRole;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecRoleService;
import cn.itcast.elec.service.IElecUserService;

public class ElecRealm extends AuthorizingRealm {

	/**用户的Service*/
	@Resource(name=IElecUserService.SERVICE_NAME)
	private IElecUserService elecUserService;
	
	/**角色的Service*/
	@Resource(name=IElecRoleService.SERVICE_NAME)
	private IElecRoleService elecRoleService;
	
	/**认证：登录*/
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		System.out.println("认证...");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
		//获取传递的用户名
		String logonName = usernamePasswordToken.getUsername();
		//使用登录名作为查询条件，查询对应的用户（查询数据库）
		ElecUser elecUser = elecUserService.findUserByLogonName(logonName);
		/**
		 * 登录名输入有误
		 * return null：表示登录名输入有误，就会执行ElecMenuAction类的menuHome方法中的catch模块，抛出一个异常
		 * org.apache.shiro.authc.UnknownAccountException：表示用户名输入有误
		 * */
		if(elecUser==null){
			return null;
		}
		/**
		 * 密码输入有误
		 * shiro验证密码操作是放置到SimpleAuthenticationInfo对象中的第2个参数中，shiro会拿数据库中存放的密码和UsernamePasswordToken中的密码进行比对
		 *   * 如果没有问题，不会执行ElecMenuAction类的menuHome方法中的catch模块
		 *   * 如果出现问题（2个密码不一致），会执行ElecMenuAction类的menuHome方法中的catch模块，抛出一个异常
		 * org.apache.shiro.authc.IncorrectCredentialsException：密码输入有误
		 */
		return new SimpleAuthenticationInfo(elecUser, elecUser.getLogonPwd(),getName());//参数3表示realm的名称
	}
	
	/**授权：权限控制访问*/
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("授权...");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//查询数据库，获取当前用户具有的角色（多个）
		//从Session中，也可以从Subject中获取
		Subject subject = SecurityUtils.getSubject();
		ElecUser elecUser = (ElecUser)subject.getPrincipal();
		//获取当前登录名
		String logonName = elecUser.getLogonName();
		//使用当前登录名，获取当前登录名具有的角色集合
		List<ElecRole> roleList = elecRoleService.findRoleListByLogonName(logonName);
		if(roleList!=null && roleList.size()>0){
			for(ElecRole elecRole:roleList){
				simpleAuthorizationInfo.addRole(elecRole.getRoleID());
			}
		}
		//使用当前角色的集合，获取当前角色集合具有的权限
		List<ElecPopedom> popedomList = elecRoleService.findPopedomListByRoleIds(roleList);
		if(popedomList!=null && popedomList.size()>0){
			for(ElecPopedom elecPopedom:popedomList){
				simpleAuthorizationInfo.addStringPermission(elecPopedom.getId());
			}
		}
		
		//simpleAuthorizationInfo.addStringPermission("ao_findRoleList");
//		//添加角色
		//simpleAuthorizationInfo.addRole("1");
		
//		//添加权限
//		simpleAuthorizationInfo.addStringPermission("aa");
//		simpleAuthorizationInfo.addStringPermission("bb");
//		simpleAuthorizationInfo.addStringPermission("cc");
		return simpleAuthorizationInfo;
	}


}
