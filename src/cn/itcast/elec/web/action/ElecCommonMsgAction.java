package cn.itcast.elec.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.util.ValueUtils;

@Controller("elecCommonMsgAction")
@Scope("prototype")
public class ElecCommonMsgAction extends BaseAction<ElecCommonMsg> {

	private ElecCommonMsg elecCommonMsg = this.getModel();

	/**运行监控*/
	@Resource(name=IElecCommonMsgService.SERVICE_NAME)
	private IElecCommonMsgService elecCommonMsgService;
	
	/**运行监控的首页显示*/
	public String home(){
		//1：查询数据库中的运行监控表的数据，在页面上进行显示（表单回显）
		ElecCommonMsg commonMsg = elecCommonMsgService.findCommonMsg();
		//放置到栈顶
		ValueUtils.push(commonMsg);
		return "home";
	}
	
	/**运行监控的保存操作*/
	public String save(){
		elecCommonMsgService.saveCommonMsg(elecCommonMsg);
		return "save";
	}
}
