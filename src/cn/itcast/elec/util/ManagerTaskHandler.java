package cn.itcast.elec.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecUserService;

public class ManagerTaskHandler implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		// 以名称作为查询条件，查询用户信息表，获取当前用户的信息的职位
		// 从web容器中获取spring容器的引用
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		IElecUserService elecUserService = (IElecUserService)ac.getBean(IElecUserService.SERVICE_NAME);
		//获取任务节点的id属性值
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		List<ElecUser> list = new ArrayList<ElecUser>();
		if(taskDefinitionKey!=null && taskDefinitionKey.equals("fukezhangtask")){
			list = elecUserService.findUserListByPostID("2");
		}
		if(taskDefinitionKey!=null && taskDefinitionKey.equals("kezhangtask")){
			list = elecUserService.findUserListByPostID("3");
		}
		if(taskDefinitionKey!=null && taskDefinitionKey.equals("caiwutask")){
			list = elecUserService.findUserListByPostID("4");
		}
		if(taskDefinitionKey!=null && taskDefinitionKey.equals("zongjinglitask")){
			list = elecUserService.findUserListByPostID("5");
		}
		if(taskDefinitionKey!=null && taskDefinitionKey.equals("zongcaitask")){
			list = elecUserService.findUserListByPostID("6");
		}
		if(list!=null && list.size()>0){
			for(ElecUser elecUser:list){
				//添加组任务的成员
				delegateTask.addCandidateUser(elecUser.getLogonName());
			}
		}

	}

}
