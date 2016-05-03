package cn.itcast.elec.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;

import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.web.form.Pagenation;
import cn.itcast.elec.web.form.WorkflowBean;


public interface IElecWorkflowService {

	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecWorkflowServiceImpl";

	void newDeploy(File file, String filename);

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionList();

	void deleteProcessDefinitionByID(String deploymentId);

	InputStream findInputStreamByDeploymentIdAndImageName(String deploymentId,
			String imageName);

	void startProcess(WorkflowBean workflowBean);

	void listGroupTask(Pagenation<WorkflowBean> pagenation);

	void listTask(Pagenation<WorkflowBean> pagenation);

	void claimTask(String taskId);

	void returnGroupTask(String taskId);

	ElecDevicePlan findDevicePlanByTaskId(String taskId);

	List<String> findOutComeListByTaskId(String taskId);

	void submitTask(WorkflowBean workflowBean);

	List<Comment> findCommentListByTaskId(String taskId);

	ElecDevicePlan findDevicePlanByDevPlanId(String devPlanId);

	List<Comment> findCommentListByDevPlanId(String devPlanId);

	ProcessDefinition findProcessDefinitionByTaskId(String taskId);

	Map<String, Object> findCoordingByTaskId(String taskId);


}
