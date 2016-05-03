package cn.itcast.elec.web.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Controller("elecTextAction")
 * @Scope(value="prototype")
 * 相当于spring容器中定义
 * <bean id="elecTextAction" class="cn.itcast.elec.web.action.ElecTextAction" scope="prototype"></bean>
 * 由spring创建Action对象，struts要求Action对象是多实例多线程（1个线程是一个实例）
 */
@Controller("elecTextAction")
@Scope(value="prototype")
public class ElecTextAction extends BaseAction<ElecText> {

	//模型驱动的作用，将对象压入到栈顶
	ElecText elecText = this.getModel();
	
	//注入Service
	@Resource(name=IElecTextService.SERVICE_NAME)
	private IElecTextService elecTextService;

	/**执行保持*/
	public String save(){
//		String textDate = request.getParameter("textDate");
//		System.out.println(textDate);
		elecTextService.saveElecText(elecText);
		return "save";
	}
}
