package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecDeviceDao;
import cn.itcast.elec.dao.IElecDevicePlanDao;
import cn.itcast.elec.domain.ElecDevice;
import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.service.IElecDevicePlanService;
import cn.itcast.elec.util.ValueUtils;
import cn.itcast.elec.web.form.Pagenation;

@Service(IElecDevicePlanService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecDevicePlanServiceImpl implements IElecDevicePlanService {

	/**设备购置计划*/
	@Resource(name=IElecDevicePlanDao.SERVICE_NAME)
	private IElecDevicePlanDao elecDevicePlanDao;

	/**设备信息*/
	@Resource(name=IElecDeviceDao.SERVICE_NAME)
	private IElecDeviceDao elecDeviceDao;
	/***
	 * 使用查询条件，查询设备购置计划表的数据，返回List<ElecDevicePlan>
	 */
	public void findDevicePlanByCondition(ElecDevicePlan elecDevicePlan,
			Pagenation<ElecDevicePlan> pagenation) {
		//封装查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//设备名称
		String devName = elecDevicePlan.getDevName();
		if(StringUtils.isNotBlank(devName)){
			condition += " and dp.devName like ?";
			paramsList.add("%"+devName+"%");
		}
		//设备类型
		String devType = elecDevicePlan.getDevType();
		if(StringUtils.isNotBlank(devType)){
			condition += " and dp.devType = ?";
			paramsList.add(""+devType+"");
		}
		//设备购置时间开始
		Date planDateBegin = elecDevicePlan.getPlanDateBegin();
		if(planDateBegin!=null){
			condition += " and dp.planDate >= ?";
			paramsList.add(planDateBegin);
		}
		//设备购置时间结束
		Date planDateEnd = elecDevicePlan.getPlanDateEnd();
		if(planDateEnd!=null){
			condition += " and dp.planDate <= ?";
			paramsList.add(planDateEnd);
		}
		//设备审核状态
		String state = elecDevicePlan.getState();
		if(StringUtils.isNotBlank(state)){
			condition += " and state = ?";
			paramsList.add(""+state+"");
		}
			
		Object [] params = paramsList.toArray();
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("dp.PlanDate", "desc");
			
		elecDevicePlanDao.findDevicePlanByCondition(condition,params,orderby,pagenation);

		
	}
	
	/**保存设备购置计划*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(ElecDevicePlan elecDevicePlan) {
		Date date = new Date();
		/**
		 * 	 private String createEmpId;	//创建人
		     private Date createDate;		//创建时间
		     private String lastEmpId;		//修改人
		     private Date lastDate;			//修改时间
		 */
		//从Session中获取当前用户信息
		ElecUser elecUser = ValueUtils.getSession();
		elecDevicePlan.setCreateDate(date);
		elecDevicePlan.setCreateEmpId(elecUser.getLogonName());
		elecDevicePlan.setLastDate(date);
		elecDevicePlan.setLastEmpId(elecUser.getLogonName());
		//添加审核状态，0表示未审核，1表示审核中，2表示审核通过，3表示审核不通过
		elecDevicePlan.setState("0");
		//获取设备购置计划ID
		String devPlanId = elecDevicePlan.getDevPlanId();
		if(StringUtils.isBlank(devPlanId)){
			elecDevicePlanDao.save(elecDevicePlan);			
		}
		else{
			elecDevicePlanDao.update(elecDevicePlan);			
		}
	}
	
	/**使用主键ID，删除设备购置计划*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteById(ElecDevicePlan elecDevicePlan) {
		String devPlanId = elecDevicePlan.getDevPlanId();
		String devPlanIds [] = devPlanId.split(",");
		//更新isDelete字段的状态
		if(devPlanIds!=null && devPlanIds.length>0){
			for(String planId:devPlanIds){
				//先查询
				ElecDevicePlan devicePlan = elecDevicePlanDao.findObjectByID(planId);
				devicePlan.setIsDelete("1");//1表示已经删除
			}
		}
	}
	
	/**计划顺延，将时间推迟3个月*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updatePlanTime(String devPlanId) {
		elecDevicePlanDao.updatePlanTime(devPlanId);
	}
	
	/**
	 * 购置
	 * 将计划购买的设备，存放到正式的设备表中
	 * */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void buyDevice(String devPlanId) {
		//使用设备购置计划ID，查询设备购置计划的信息
		ElecDevicePlan elecDevicePlan = elecDevicePlanDao.findObjectByID(devPlanId);//返回的是持久化对象
		//1：组织PO对象，向设备表中添加数据
		ElecDevice elecDevice = new ElecDevice();
		//完成复制
		try {
			BeanUtils.copyProperties(elecDevice, elecDevicePlan);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		/**其他属性*/
		ElecUser elecUser = ValueUtils.getSession();
		elecDevice.setCreateEmpId(elecUser.getLogonName());
		elecDevice.setCreateDate(new Date());
		elecDevice.setLastEmpId(elecUser.getLogonName());
		elecDevice.setLastDate(new Date());
		elecDevice.setIsDelete("0");//假删除
		/**建立一对一的关联关系，如果不建立关联关系，此时外键列就为null*/
		elecDevice.setElecDevicePlan(elecDevicePlan);
		elecDeviceDao.save(elecDevice);
		//2：更新设备购置计划表的购买状态，从0变成1
		elecDevicePlan.setPurchaseState("1");
	}
}
