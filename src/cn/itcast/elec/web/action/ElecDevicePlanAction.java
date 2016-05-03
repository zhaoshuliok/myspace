package cn.itcast.elec.web.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.service.IElecDevicePlanService;
import cn.itcast.elec.service.IElecSystemDDLService;
import cn.itcast.elec.util.ValueUtils;

@Controller("elecDevicePlanAction")
@Scope("prototype")
public class ElecDevicePlanAction extends BaseAction<ElecDevicePlan> {

	private ElecDevicePlan elecDevicePlan = this.getModel();

	/**设备购置计划*/
	@Resource(name=IElecDevicePlanService.SERVICE_NAME)
	private IElecDevicePlanService elecDevicePlanService;
	
	/**数据字典*/
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	/**设备购置计划首页显示*/
	public String home(){
		return "home";
	}
	
	/**使用url加载设备类型、所属单位*/
	public String findSystemDDL(){
		//获取类型
		String devType = elecDevicePlan.getDevType();
		//处理编码
		try {
			devType = new String(devType.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//以类型作为数据类型，查询对应数据字典的值
		List<ElecSystemDDL> list = elecSystemDDLService.findSystemDDLListByKeyword(devType);
		//将集合放置到栈顶
		ValueUtils.push(list);
		return "findSystemDDL";
	}
	
	/**使用url加载设备购置计划首页的集合列表，返回的都是json的数组*/
	/**
	 * 格式：
	 * 参数：
	 *  int page:表示当前页
 		int rows:表示当前页最多显示的记录数
	 * 
	 * 返回值
	 * 响应2个返回值
		  int tatal：表示总记录数
		  List<ElecDevice> rows:表示返回的结果集的集合，将rows转成需要的json
	 * {                                                      
			"total":100,	
			"rows":[ 
				{"devPlanId":"1","devName":"电机设备","quality":"1","qunit":"个","devExpense":"10000","specType":"V1.0","useness":"发电","useUnit":"北京中关村","jctIdName":"北京","devTypeName":"发电设备","planDate":"2015-1-1","isDelete":"0"},
				{"devPlanId":"1","devName":"电力设备","quality":"2","qunit":"个","devExpense":"10000","specType":"V1.0","useness":"发电","useUnit":"北京中关村","jctIdName":"北京","devTypeName":"发电设备","planDate":"2015-1-1","isDelete":"0"}
			]
		}
	 */
	public String pageHome(){
		//将数据查询后，将总的记录数和集合存放到pagenation
		elecDevicePlanService.findDevicePlanByCondition(elecDevicePlan,pagenation);
		//最终将pagenation放置到栈顶
		ValueUtils.push(pagenation);
		return "findSystemDDL";
	}
	
	/**保存设备购置计划*/
	public String save(){
		elecDevicePlanService.save(elecDevicePlan);
		return "home";
	}
	
	/**删除*/
	public String delete(){
		elecDevicePlanService.deleteById(elecDevicePlan);
		return "home";
	}
	
	/**计划顺延*/
	public String updatePlanTime(){
		//获取设备购置计划ID
		String devPlanId = elecDevicePlan.getDevPlanId();
		//顺延3个月
		elecDevicePlanService.updatePlanTime(devPlanId);
		return "home";
	}
	
	/**购置*/
	public String buyDevice(){
		//获取设备购置计划ID
		String devPlanId = elecDevicePlan.getDevPlanId();
		elecDevicePlanService.buyDevice(devPlanId);
		return "home";
	}
}
