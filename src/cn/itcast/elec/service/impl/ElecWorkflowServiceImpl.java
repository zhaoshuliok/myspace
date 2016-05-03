package cn.itcast.elec.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;


import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecDevicePlanDao;
import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecWorkflowService;
import cn.itcast.elec.util.ValueUtils;
import cn.itcast.elec.web.form.Pagenation;
import cn.itcast.elec.web.form.WorkflowBean;

@Service(IElecWorkflowService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecWorkflowServiceImpl implements IElecWorkflowService {

	/**RepositoryService*/
	@Resource(name="repositoryService")
	private RepositoryService repositoryService;

	/**RuntimeService*/
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	
	/**TaskService*/
	@Resource(name="taskService")
	private TaskService taskService;
	
	/**RepositoryService*/
	@Resource(name="historyService")
	private HistoryService historyService;
	
	/**设备购置计划的Dao*/
	@Resource(name=IElecDevicePlanDao.SERVICE_NAME)
	private IElecDevicePlanDao elecDevicePlanDao;
	
	/**部署流程定义*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void newDeploy(File file, String filename) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));//将文件转换成zip的输入流
			repositoryService.createDeployment()//
						.name(filename)//部署对象名称
						.addZipInputStream(zipInputStream)//使用zip的输入流部署
						.deploy();//完成部署
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**查询部署对象的List集合*/
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//
					.orderByDeploymenTime().asc()//
					.list();
		return list;
	}
	
	/**查询流程定义的List集合*/
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//
					.orderByProcessDefinitionVersion().asc()//
					.list();
		return list;
	}
	
	/**使用部署对象ID，删除流程定义*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteProcessDefinitionByID(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	/**使用部署对象ID和资源图片名称，查询对应的输入流InputStream，写到输出流*/
	public InputStream findInputStreamByDeploymentIdAndImageName(
			String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
	
	/**启动流程实例*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void startProcess(WorkflowBean workflowBean) {
		//设备购置计划ID
		String devPlanId = workflowBean.getDevPlanId();
		//流程定义的key
		String processDefinitionKey = workflowBean.getProcessDefinitionKey();
		//使用设备购置计划ID，查询设备购置计划的信息
		ElecDevicePlan elecDevicePlan = elecDevicePlanDao.findObjectByID(devPlanId);
		/**
		 *   1：在启动流程实例的同时，设置流程变量
			     *（1）：设置userID，设置当前登录人就是第一个任务的办理人（使用登录名）
			     *（2）：使用流程变量objID，用来设置业务表设备购置计划单的主键ID
			     *（3）：设置流程变量money，用来判断流程是执行总裁还是总经理，值就是当前购买的金额
		 */
		Map<String, Object> variables = new HashMap<String, Object>();
		ElecUser elecUser = ValueUtils.getSession();
		variables.put("userID", elecUser.getLogonName());
		variables.put("objID", devPlanId);
		variables.put("money", elecDevicePlan.getDevExpense());
		/**
		 * 2：使用流程定义的KEY，启动流程实例（最新版本启动），同时向正在执行的执行对象表中BUSINESS_KEY的字段，设置存放设备购置计划单业务ID
		 */
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey,devPlanId,variables);
		//获取流程实例ID
		String processInstanceId = pi.getProcessInstanceId();
		/**
		 *  3：完成当前人的第一个个人任务
		 */
		//因为启动流程实例后只能经历一个任务，所以可以使用流程实例ID，查询唯一的任务对象
		Task task = taskService.createTaskQuery()//
					.processInstanceId(processInstanceId)//按照流程实例ID查询
					.singleResult();
		//获取任务ID
		String taskId = task.getId();
		taskService.complete(taskId);
		/**
		 * 4：	更新设备购置计划单的状态state从0变成1（审核中）
     			更新设备购置计划单的流程实例ID，更新成当前启动流程的流程实例ID
		 */
		elecDevicePlan.setState("1");
		elecDevicePlan.setProcessInstanceID(processInstanceId);
	}
	
	/**使用当前登录人，查询组任务的成员列表*/
	public void listGroupTask(Pagenation<WorkflowBean> pagenation) {
		//从Session中获取当前登录人的登录名
		ElecUser elecUser = ValueUtils.getSession();
		String logonName = elecUser.getLogonName();
		
		//查询组任务的列表的数量
		Long total = taskService.createTaskQuery()//
					.taskCandidateUser(logonName)
					.count();
		//总记录数
		pagenation.setTotal(total);
		
		//查询组任务的列表集合，返回List<Task>
		int firstResult = (pagenation.getPage()-1)*pagenation.getPageSize();
		int maxResults = pagenation.getPageSize();
		List<Task> list = taskService.createTaskQuery()//
					.taskCandidateUser(logonName)//使用当前登录人查询组任务
					.orderByTaskCreateTime().asc()//
					.listPage(firstResult, maxResults);
		//将流程的信息和业务的信息都查询出来放置到List<WorkflowBean>中的WorkflowBean的对象中
		/**{"id":"1","name":"部门经理审批","createTime":"2014-10-11 9:50:00","assignee":"李四","processDefinitionId":"100","processInstanceId":"101","executionId":"101",
		 * "devName":"电机设备","quality":"2个","useUnit":"北京","devExpense":"50000元","planDate":"2014-11-30"},
		 */
		List<WorkflowBean> rows = new ArrayList<WorkflowBean>();
		if(list!=null && list.size()>0){
			for(Task task:list){
				WorkflowBean workflowBean = new WorkflowBean();
				workflowBean.setId(task.getId());
				workflowBean.setName(task.getName());
				workflowBean.setCreateTime(task.getCreateTime());
				//如果是组任务办理人字段为null
				//查询组任务办理人表
				StringBuffer userID = new StringBuffer("");
				List<IdentityLink> identityList = taskService.getIdentityLinksForTask(task.getId());//使用任务ID查询，组任务的办理人列表
				if(identityList!=null && identityList.size()>0){
					for(IdentityLink identityLink:identityList){
						//办理人
						String user = identityLink.getUserId();
						userID.append(user).append(",");
					}
					//去掉逗号
					userID.deleteCharAt(userID.length()-1);
				}
				workflowBean.setAssignee(userID.toString());		
				workflowBean.setProcessDefinitionId(task.getProcessDefinitionId());
				workflowBean.setProcessInstanceId(task.getProcessInstanceId());
				workflowBean.setExecutionId(task.getExecutionId());
				//获取流程实例，使用流程实例查询业务信息
				String processInstanceId = task.getProcessInstanceId();
				//查询业务表，获取业务表的数据
				String condition = " and o.processInstanceID = ?";
				Object [] params = {processInstanceId};
				List<ElecDevicePlan> planList = elecDevicePlanDao.findCollectionByConditionNoPage(condition, params, null);
				if(planList!=null && planList.size()>0){
					ElecDevicePlan devicePlan = planList.get(0);
					workflowBean.setDevName(devicePlan.getDevName());
				    workflowBean.setQuality(devicePlan.getQuality());
					workflowBean.setUseUnit(devicePlan.getUseUnit());
			    	workflowBean.setDevExpense(devicePlan.getDevExpense());
					workflowBean.setPlanDate(devicePlan.getPlanDate());
				}
				rows.add(workflowBean);
			}
		}
		//查询的集合List
		pagenation.setRows(rows);
	}
	
	/**查询个人任务列表*/
	public void listTask(Pagenation<WorkflowBean> pagenation) {
		//从Session中获取当前登录人的登录名
		ElecUser elecUser = ValueUtils.getSession();
		String logonName = elecUser.getLogonName();
		
		//查询个人任务的列表的数量
		Long total = taskService.createTaskQuery()//
					.taskAssignee(logonName)
					.count();
		//总记录数
		pagenation.setTotal(total);
		
		//查询组任务的列表集合，返回List<Task>
		int firstResult = (pagenation.getPage()-1)*pagenation.getPageSize();
		int maxResults = pagenation.getPageSize();
		List<Task> list = taskService.createTaskQuery()//
					.taskAssignee(logonName)//使用当前登录人查询组任务
					.orderByTaskCreateTime().asc()//
					.listPage(firstResult, maxResults);
		//将流程的信息和业务的信息都查询出来放置到List<WorkflowBean>中的WorkflowBean的对象中
		/**{"id":"1","name":"部门经理审批","createTime":"2014-10-11 9:50:00","assignee":"李四","processDefinitionId":"100","processInstanceId":"101","executionId":"101",
		 * "devName":"电机设备","quality":"2个","useUnit":"北京","devExpense":"50000元","planDate":"2014-11-30"},
		 */
		List<WorkflowBean> rows = new ArrayList<WorkflowBean>();
		if(list!=null && list.size()>0){
			for(Task task:list){
				WorkflowBean workflowBean = new WorkflowBean();
				workflowBean.setId(task.getId());
				workflowBean.setName(task.getName());
				workflowBean.setCreateTime(task.getCreateTime());
				//任务的办理人
				workflowBean.setAssignee(task.getAssignee());		
				workflowBean.setProcessDefinitionId(task.getProcessDefinitionId());
				workflowBean.setProcessInstanceId(task.getProcessInstanceId());
				workflowBean.setExecutionId(task.getExecutionId());
				//获取流程实例，使用流程实例查询业务信息
				String processInstanceId = task.getProcessInstanceId();
				//查询业务表，获取业务表的数据
				String condition = " and o.processInstanceID = ?";
				Object [] params = {processInstanceId};
				List<ElecDevicePlan> planList = elecDevicePlanDao.findCollectionByConditionNoPage(condition, params, null);
				if(planList!=null && planList.size()>0){
					ElecDevicePlan devicePlan = planList.get(0);
					workflowBean.setDevName(devicePlan.getDevName());
				    workflowBean.setQuality(devicePlan.getQuality());
					workflowBean.setUseUnit(devicePlan.getUseUnit());
			    	workflowBean.setDevExpense(devicePlan.getDevExpense());
					workflowBean.setPlanDate(devicePlan.getPlanDate());
				}
				rows.add(workflowBean);
			}
		}
		//查询的集合List
		pagenation.setRows(rows);
	}
	
	/**拾取任务*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void claimTask(String taskId) {
		//获取当前登录人
		ElecUser elecUser = ValueUtils.getSession();
		taskService.claim(taskId, elecUser.getLogonName());
	}
	
	/**回退组任务*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void returnGroupTask(String taskId) {
		taskService.setAssignee(taskId, null);
	}
	
	/**一：使用任务ID，查询设备购置计划单的信息*/
	public ElecDevicePlan findDevicePlanByTaskId(String taskId) {
		//1：使用任务ID，查询任务的对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//2：使用流程实例ID，查询流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		String devPlanId = pi.getBusinessKey();
		//3：使用设备购置计划单ID，查询设备购置计划
		ElecDevicePlan elecDevicePlan = elecDevicePlanDao.findDevPlanById(devPlanId);
		return elecDevicePlan;
	}
	
	/**使用任务ID，查询连线的集合名词*/
	public List<String> findOutComeListByTaskId(String taskId) {
		//连线名称集合
		List<String> list = new ArrayList<String>();
		//1：使用任务ID，查询任务的对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//获取执行ID
		String executionId = task.getExecutionId();
		//使用执行对象ID，查询执行对象，从而获取当前活动ID
		Execution execution = runtimeService.createExecutionQuery()//
					.executionId(executionId)//使用执行对象ID查询
					.singleResult();
		//获取当前活动ID
		String activityId = execution.getActivityId();
		//2：获取devicePlan.bpmn文件中的数据，从而获取连线的名称、坐标
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//3:获取连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvmTransition:pvmList){
				String outcome = (String) pvmTransition.getProperty("name");
				//设置按钮的初始值
				if(StringUtils.isBlank(outcome)){
					outcome = "默认提交";
				}
				list.add(outcome);
			}
		}
		
		return list;
	}
	
	/**提交审核记录，完成对申请人的任务的批注信息填写*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void submitTask(WorkflowBean workflowBean) {
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		//获取连线的名称
		String outcome = workflowBean.getOutcome();
		//获取页面填写的批注信息
		String comment = workflowBean.getComment();
		//使用任务Id，查询任务的对象，获取流程实例ID
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		
		/**1：在完成任务之前，获取页面传递的批注，向批注信息表中添加数据。Act_hi_comment表*/
		/**
		 * 参数一taskId：对应Act_hi_comment表的Task_Id的字段
		 * 参数二processInstanceId：对应Act_hi_comment表的Task_Id的字段proc_inst_id字段
		 * 参数三comment：Act_hi_comment表的Task_Id的字段message字段
		 * 
		 * 观察到Act_hi_comment表user_id的字段是null
		 * 提示：
		 * 	String userId = Authentication.getAuthenticatedUserId();
		    CommentEntity comment = new CommentEntity();
		    comment.setUserId(userId);
		   	要求：
		   	ElecUser elecUser = ValueUtils.getSession();
			Authentication.setAuthenticatedUserId(elecUser.getLogonName());
		 */
		//从Session中获取当前登录人
		ElecUser elecUser = ValueUtils.getSession();
		Authentication.setAuthenticatedUserId(elecUser.getLogonName());
		taskService.addComment(taskId, processInstanceId, comment);
		/**
		 * 2：在完成任务之前，设置流程变量，使用流程变量用来判断执行的连线：
  			究竟执行哪条连线，使用${message=='批准'}，message表示流程变量的名称，批注表示执行的操作（表示连线的名称）
		 */
		Map<String, Object> variables = new HashMap<String, Object>();
		if(outcome!=null && !outcome.equals("默认提交")){
			variables.put("message", outcome);
		}
		/**3：使用任务ID，完成任务，同时设置流程变量*/
		taskService.complete(taskId,variables);
		/**
		 * 4：判断流程是否结束，如果任务完成之后，流程结束了
		  * 如果点击的是【批准】，那么更新设备购置计划表的state状态从1变成2（审核通过）
		  * 如果点击的是【驳回】，那么更新设备购置计划表的state状态从1变成3（审核不通过）
		 */
		//使用流程实例ID，查询流程实例的对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//正在执行
						.processInstanceId(processInstanceId)//
						.singleResult();
		//表示流程结束了
		if(pi==null){
			//获取设备购置计划ID
			String devPlanId = workflowBean.getDevPlanId();
			//使用设备购置计划ID，查询设备购置计划的信息
			ElecDevicePlan devicePlan = elecDevicePlanDao.findObjectByID(devPlanId);
			if(outcome!=null && outcome.equals("批准")){
				devicePlan.setState("2");//审核通过
			}
			else if(outcome!=null && outcome.equals("驳回")){
				devicePlan.setState("3");//审核不通过
			}
		}
	}
	
	/**使用当前任务ID，查询当前流程对应的批注信息，返回List<Comment>*/
	public List<Comment> findCommentListByTaskId(String taskId) {
		List<Comment> commentList = new ArrayList<Comment>();
		//使用当前的任务ID，查询所有对应该流程的历史的任务ID
		//使用当前任务ID，查询当前任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**方案一：查询历史表，使用任务ID，查询批注*/
		//使用流程实例ID，查询历史的任务表，返回历史任务的集合
//		List<HistoricTaskInstance> hisTaskList = historyService.createHistoricTaskInstanceQuery()//
//					.processInstanceId(processInstanceId)//使用流程实例ID查询
//					.list();
//		//遍历
//		if(hisTaskList!=null && hisTaskList.size()>0){
//			for(HistoricTaskInstance hti:hisTaskList){
//				//获取历史的任务ID
//				String historyTaskId = hti.getId();
//				List<Comment> list = taskService.getTaskComments(historyTaskId);
//				//获取到了批注信息
//				if(list!=null && list.size()>0){
//					commentList.addAll(list);
//				}
//			}
//		}
		/**方案二：使用流程实例ID，查询批注*/
		commentList = taskService.getProcessInstanceComments(processInstanceId);
		return commentList;
	}
	
	/**使用设备购置计划ID，查询设备购置计划的对象*/
	public ElecDevicePlan findDevicePlanByDevPlanId(String devPlanId) {
		return elecDevicePlanDao.findDevPlanById(devPlanId);
	}
	
	/**使用设备购置计划ID，查询历史的批注信息*/
	public List<Comment> findCommentListByDevPlanId(String devPlanId) {
		/**方案一：查询历史的流程实例表，获取流程实例ID*/
//		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//
//						.processInstanceBusinessKey(devPlanId)//使用BUSINESS_KEY的字段查询
//						.singleResult();
//		String processInstanceId = hpi.getId();
		/**方案二：查询历史的流程变量表，获取流程实例ID*/
//		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//
//						.variableValueEquals("objID", devPlanId)//使用流程变量的名称查询和值
//						.singleResult();
//		String processInstanceId = hvi.getProcessInstanceId();
		/**方案三：使用设备购置计划ID，查询设备购置计划的对象*/
		ElecDevicePlan devicePlan = elecDevicePlanDao.findObjectByID(devPlanId);
		String processInstanceId = devicePlan.getProcessInstanceID();
		
		List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);
		return commentList;
	}
	
	/**使用任务ID，查询流程定义的对象*/
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		/**
		 * 一：使用任务ID，查询任务对象，获取流程定义ID，查询流程定义的对象
    		从流程定义的对象中，获取部署对象ID和资源图片名称
		 */
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		//使用流程定义ID，查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
					.processDefinitionId(processDefinitionId)//使用流程定义ID查询
					.singleResult();
		return pd;
	}
	
	/**使用任务ID，查询当前活动对应的坐标信息*/
	public Map<String, Object> findCoordingByTaskId(String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//1：使用任务ID，查询任务的对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		/**方案一：使用执行ID，查询执行对象，获取当前活动ID*/
//		//获取执行ID
//		String executionId = task.getExecutionId();
//		//使用执行对象ID，查询执行对象，从而获取当前活动ID
//		Execution execution = runtimeService.createExecutionQuery()//
//					.executionId(executionId)//使用执行对象ID查询
//					.singleResult();
//		//获取当前活动ID
//		String activityId = execution.getActivityId();
		/**方案二：直接获取任务定义的key*/
		String activityId = task.getTaskDefinitionKey();
		//2：获取devicePlan.bpmn文件中的数据，从而获取连线的名称、坐标
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}
}
