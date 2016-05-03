package cn.itcast.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.service.IElecSystemDDLService;

@Controller("elecSystemDDLAction")
@Scope("prototype")
public class ElecSystemDDLAction extends BaseAction<ElecSystemDDL> {

	private ElecSystemDDL elecSystemDDL = this.getModel();

	//数据字典的Service
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	/**数据字典的首页显示*/
	public String home(){
		//1：初始化类型列表，并去掉重复值，返回List<ElecSystemDDL>，遍历在页面的下拉菜单中
		List<ElecSystemDDL> list = elecSystemDDLService.findSystemDDLListWithDistinct();
		request.setAttribute("list", list);
		return "home";
	}
	
	/**使用数据类型查询对应数据类型下集合，跳转到编辑页面*/
	public String edit(){
		//获取数据类型
		String keyword = elecSystemDDL.getKeyword();
		//2：使用数据类型作为查询条件，查询对应数据类型的集合，返回List<ElecSystemDDL>，将集合遍历在dictionaryEdit.jsp中
		List<ElecSystemDDL> list = elecSystemDDLService.findSystemDDLListByKeyword(keyword);
		request.setAttribute("list", list);
		return "edit";
	}
	
	/**保存*/
	public String save(){
		elecSystemDDLService.save(elecSystemDDL);
		return "save";
	}
}
