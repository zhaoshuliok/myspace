package cn.itcast.elec.web.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.service.IElecWorkflowService;
import cn.itcast.elec.util.ValueUtils;
import cn.itcast.elec.web.form.WorkflowBean;

@Controller("elecWorkflowAction")
@Scope("prototype")
public class ElecWorkflowAction extends BaseAction<WorkflowBean> {

	private WorkflowBean  workflowBean  = this.getModel();

	/**工作流*/
	@Resource(name=IElecWorkflowService.SERVICE_NAME)
	private IElecWorkflowService elecWorkflowService;
	
	/**流程部署管理的首页显示*/
	public String deployHome(){
		return "home";
	}
	
	/**完成部署（使用zip格式）*/
	public String newdeploy(){
		//获取文件
		File file = workflowBean.getFile();
		//获取流程名称
		String filename = workflowBean.getFilename();
		elecWorkflowService.newDeploy(file,filename);
		return "home";
	}
	
	/**一：查询部署对象的集合，返回List<Deployment>*/
	public String findDeploymentList(){
		//一：查询部署对象的集合，返回List<Deployment>
		List<Deployment> list = elecWorkflowService.findDeploymentList();
		//放置到栈顶
		ValueUtils.push(list);
		return "findDeploymentList";
	}
	
	/**二：查询流程定义的集合，返回List<ProcessDefinition>*/
	public String findProcessDefinitionList(){
		//二：查询流程定义的集合，返回List<ProcessDefinition>
		List<ProcessDefinition> list = elecWorkflowService.findProcessDefinitionList();
		//放置到栈顶
		ValueUtils.push(list);
		return "findProcessDefinitionList";
	}
	
	/**删除流程定义*/
	public String delDeployment(){
		//获取部署对象ID
		String deploymentId = workflowBean.getDeploymentId();
		elecWorkflowService.deleteProcessDefinitionByID(deploymentId);
		return "home";
	}
	
	/**查看流程图
	 * @throws Exception */
	public String viewImage() throws Exception{
		//获取部署对象ID
		String deploymentId = workflowBean.getDeploymentId();
		//获取资源图片名称
		String imageName = workflowBean.getImageName();
		//使用部署对象ID和资源图片名称，查询对应的输入流InputStream，写到输出流
		InputStream in = elecWorkflowService.findInputStreamByDeploymentIdAndImageName(deploymentId,imageName);
		/**方案一：*/
		OutputStream out = response.getOutputStream();
		//将输入流写到输出流
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);//输出到页面
		}
		out.close();
		in.close();
		/**方案二：将图片生成到图片服务器上，返回到一个页面，在页面上使用<img src="http://localhost:8080/elec_image/XXXX.png">*/
		return NONE;//或者null
	}
	
	/**
	 * 启动流程实例
	 * @return
	 */
	public String startProcess(){
		elecWorkflowService.startProcess(workflowBean);
		return "startProcess";
	}
	
	/**组任务查询*/
	public String taskGroupHome(){
		return "taskGroupHome";
	}
	
	/**个人任务查询*/
	public String taskHome(){
		return "taskHome";
	}
	
	/**查询组任务的结果集列表*/
	public String listGroupTask(){
		//查询组任务的结果集
		elecWorkflowService.listGroupTask(pagenation);
		//放置到栈顶
		ValueUtils.push(pagenation);
		return "jsonList";
	}
	
	/**查询个人任务的结果集列表*/
	public String listTask(){
		//查询个人任务的结果集
		elecWorkflowService.listTask(pagenation);
		//放置到栈顶
		ValueUtils.push(pagenation);
		return "jsonList";
	}
	
	/**拾取任务*/
	public String claimTask(){
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		//拾取任务
		elecWorkflowService.claimTask(taskId);
		return "taskGroupHome";
	}
	
	/**回退组任务*/
	public String returnGroupTask(){
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		//拾取任务
		elecWorkflowService.returnGroupTask(taskId);
		return "taskHome";
	}
	
	/**跳转到办理任务的页面*/
	public String audit(){
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		//一：使用任务ID，查询设备购置计划单的信息
		ElecDevicePlan elecDevicePlan = elecWorkflowService.findDevicePlanByTaskId(taskId);
		//放置到栈顶
		ValueUtils.push(elecDevicePlan);
		//二：使用任务ID，查询当前任务完成之后的连线的名称，返回List<String>，遍历在页面上。作为按钮
		List<String> outcomeList = elecWorkflowService.findOutComeListByTaskId(taskId);
		request.setAttribute("outcomeList", outcomeList);
		/**
		 * 三：使用任务ID，查询审核人历史的批注信息
			如果使用Activiti开发，提供了一张表，对应了List<Comment>,Comment对象对应act_hi_comment中的属性
			  * 记录：审核人，审核时间，审核信息，与任务ID、流程实例ID关联
			
			如果不使用这个表act_hi_comment，自己创建一个审核信息表
			  * 记录：审核人，审核时间，审核信息，与任务ID、流程实例ID关联、关联设备购置计划申请单ID
		 */
		List<Comment> commentList = elecWorkflowService.findCommentListByTaskId(taskId);
		request.setAttribute("commentList", commentList);
		return "audit";
	}
	
	/**提交表单，完成批注*/
	public String submitTask(){
		elecWorkflowService.submitTask(workflowBean);
		return "taskHome";
	}
	
	/**查看流程审核状态*/
	public String viewHisComment(){
		//获取设备购置计划ID
		String devPlanId = workflowBean.getDevPlanId();
		//一：使用设备购置计划ID，查询设备购置计划的对象，完成表单回显
		ElecDevicePlan elecDevicePlan = elecWorkflowService.findDevicePlanByDevPlanId(devPlanId);
		//放置到栈顶
		ValueUtils.push(elecDevicePlan);
		//二：使用设备购置计划ID，查询历史的批注信息表，获取审核人审核的批注信息
		List<Comment> commentList = elecWorkflowService.findCommentListByDevPlanId(devPlanId);
		request.setAttribute("commentList", commentList);
		return "viewHisComment";
	}
	
	/**查看当前流程图*/
	public String viewCurrentImage(){
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		/**
		 * 一：使用任务ID，查询任务对象，获取流程定义ID，查询流程定义的对象
			    从流程定义的对象中，获取部署对象ID和资源图片名称
			使用部署对象ID和资源图片名称，获取图片的输入流，在页面<img src="">
		 */
		ProcessDefinition pd = elecWorkflowService.findProcessDefinitionByTaskId(taskId);
		request.setAttribute("pd", pd);
		/**
		 * 二：使用任务ID，查询当前活动对应的坐标的信息，定义一个<div>标签，用来指定红色边框的坐标
		 * map集合中存放的就是当前活动的坐标
		 */
		Map<String, Object> map = elecWorkflowService.findCoordingByTaskId(taskId);
		request.setAttribute("map", map);
		return "viewCurrentImage";
	}
}
