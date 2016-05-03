package cn.itcast.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecPopedom;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.service.IElecRoleService;
import cn.itcast.elec.service.IElecUserService;
import cn.itcast.elec.util.MD5keyBean;
import cn.itcast.elec.util.ValueUtils;
import cn.itcast.elec.web.form.MenuForm;

@Controller("elecMenuAction")
@Scope("prototype")
public class ElecMenuAction extends BaseAction<MenuForm> {

	private MenuForm menuForm = this.getModel();

	//运行监控
	@Resource(name=IElecCommonMsgService.SERVICE_NAME)
	private IElecCommonMsgService elecCommonMsgService;
	
	//用户
	@Resource(name=IElecUserService.SERVICE_NAME)
	private IElecUserService elecUserService;
	
	//角色
	@Resource(name=IElecRoleService.SERVICE_NAME)
	private IElecRoleService elecRoleService;
	
//	/**登录首页(普通项目)*/
//	public String menuHome(){
//		
//		
//		//获取登录名
//		String name = menuForm.getName();
//		//获取密码
//		String password = menuForm.getPassword();
//		
//		/**使用登录名作为查询条件，查询对应的登录名下的用户信息*/
//		ElecUser elecUser = elecUserService.findUserByLogonName(name);
//		//校验
//		if(elecUser==null){
//			//使用this.addFieldError，页面<s:fielderror/>
////			this.addFieldError(fieldName, errorMessage)
//			//使用this.addActionError，页面<s:actionerror/>
//			this.addActionError("登录名输入有误");
//			return "error";//跳转到登录页面
//		}
//		if(StringUtils.isBlank(password)){
//			this.addActionError("密码不能为空");
//			return "error";//跳转到登录页面
//		}
//		else{
//			MD5keyBean bean = new MD5keyBean();
//			String md5password = bean.getkeyBeanofStr(password);
//			if(!md5password.equals(elecUser.getLogonPwd())){
//				this.addActionError("密码输入有误");
//				return "error";//跳转到登录页面
//			}
//		}
//		//将用户存放到Session中
//		request.getSession().setAttribute("globle_user", elecUser);
//		return "menuHome";
//	}
	
	/**登录首页(使用shiro完成认证)*/
	public String menuHome(){
		//获取登录名
		String name = menuForm.getName();
		//获取密码
		String password = menuForm.getPassword();
		//对密码进行加密（数据是加密）
		MD5keyBean md5keyBean = new MD5keyBean();
		String md5password = md5keyBean.getkeyBeanofStr(password);
		//调用主题用户
		Subject subject = SecurityUtils.getSubject();
		//authenticationToken：表示将用户名和密码存放到authenticationToken参数对象中（用来传递用户名和密码）
		UsernamePasswordToken authenticationToken = new UsernamePasswordToken(name,md5password);
		//完成登录（查询数据库，在realm中完成）
		/**
		 * shiro认证的原理
		 * 如果正常登录：用户名和密码输入正确，此时不会执行catch模块，表示正常
		 * 如果校验出现问题：用户名和密码输入错误，此时会执行catch模块，表示登录失败
		 */
		try {
			subject.login(authenticationToken);
			//通过getPrincipal方法，获取的当前用户信息
			ElecUser elecUser = (ElecUser)subject.getPrincipal();
			//将用户存放到Session中
			request.getSession().setAttribute("globle_user", elecUser);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof UnknownAccountException){
				this.addActionError("用户名输入有误！");
			}
			if(e instanceof IncorrectCredentialsException){
				this.addActionError("密码输入有误！");
			}
			return "error";//跳转到登录页面
		}
		
		return "menuHome";
	}
	
	/**退出系统*/
	public String logout(){
		//清空Session，指定Session的名称清空Session
//		request.getSession().removeAttribute(arg0);
		//清空Session
		request.getSession().invalidate();
		return "logout";
	}
	
	/**加载消息中心*/
	public String loading(){
		return "loading";
	}
	
	/**站点运行情况*/
	public String alermStation(){
		//1：查询数据库中的运行监控表的数据，在页面上进行显示（表单回显）
		ElecCommonMsg commonMsg = elecCommonMsgService.findCommonMsg();
		//放置到栈顶
		ValueUtils.push(commonMsg);
		return "alermStation";
	}
	
	/**设备运行情况*/
	public String alermDevice(){
		//1：查询数据库中的运行监控表的数据，在页面上进行显示（表单回显）
		ElecCommonMsg commonMsg = elecCommonMsgService.findCommonMsg();
		//放置到栈顶
		ValueUtils.push(commonMsg);
		return "alermDevice";
	}
	
	/**代办任务*/
	public String alermTask(){
		return "alermTask";
	}
	
	/**系统通知栏*/
	public String alermNotice(){
		return "alermNotice";
	}
	
	/**动态显示菜单的树*/
	/**
	 * [
	    { "id":"100", "pid":"0", "name":"系统管理"},
		{ "id":"1001", "pid":"100", "name":"用户管理", "page":"../system/elecUserAction_home.do"},
		{ "id":"1002", "pid":"100", "name":"角色管理", "page":"../system/elecRoleAction_home.do"},
		{ "id":"1003", "pid":"100", "name":"运行监控", "page":"../system/elecCommonMsgAction_home.do"},
		{ "id":"1003", "pid":"100", "name":"数据字典", "page":"../system/elecSystemDDLAction_home.do"}
]
	 * @return
	 */
	public String showMenu(){
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		ElecUser elecUser = (ElecUser)subject.getPrincipal();
		//登录名
		String logonName = elecUser.getLogonName();
		//获取当前generatemenu
		String generatemenu = request.getParameter("generatemenu");
		//使用当前登录名查询当前用户具有的权限的集合
		List<ElecPopedom> list = elecRoleService.findPopedomListByCondition(logonName,generatemenu);
		//返回json
		ValueUtils.push(list);
		return "showMenu";
	}
}
