package cn.itcast.elec.web.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecExportFields;
import cn.itcast.elec.service.IElecExportFieldsService;
import cn.itcast.elec.util.ListUtils;

@Controller("elecExportFieldsAction")
@Scope("prototype")
public class ElecExportFieldsAction extends BaseAction<ElecExportFields> {

	private ElecExportFields elecExportFields = this.getModel();

	@Resource(name=IElecExportFieldsService.SERVICE_NAME)
	private IElecExportFieldsService elecExportFieldsService;
	
	/**跳转到导出设置的页面*/
	public String setExportFields(){
		//1：获取主键ID
		String belongTo = elecExportFields.getBelongTo();
		//2：使用主键ID，查询对应的导出设置的信息
		ElecExportFields elecExportFields = elecExportFieldsService.findExportFieldsById(belongTo);
		/*3：创建2个Map集合
		   * 分别存放未导出的字段和导出的字段
		   * map集合的key作为英文字段
		   * map集合的value作为中文字段
		   */
		Map<String, String> map = new LinkedHashMap<String, String>();//导出
		Map<String, String> nomap = new LinkedHashMap<String, String>();//未导出
		
		//将字符串转换成集合
		List<String> zList = ListUtils.stringToList(elecExportFields.getExpNameList());
		List<String> eList = ListUtils.stringToList(elecExportFields.getExpFieldName());
		List<String> nozList = ListUtils.stringToList(elecExportFields.getNoExpNameList());
		List<String> noeList = ListUtils.stringToList(elecExportFields.getNoExpFieldName());
		//因为zList和eList的长度是一一对应的
		if(zList!=null && zList.size()>0){
			for(int i=0;i<zList.size();i++){
				map.put(eList.get(i), zList.get(i));
			}
		}
		//因为nozList和noeList的长度是一一对应的
		if(nozList!=null && nozList.size()>0){
			for(int i=0;i<nozList.size();i++){
				nomap.put(noeList.get(i), nozList.get(i));
			}
		}
		request.setAttribute("map", map);
		request.setAttribute("nomap", nomap);
		return "setExportFields";
	}
	
	/**保存运行监控的数据*/
	public String saveSetExportExcel(){
		//页面上已经对PO对象赋值
		elecExportFieldsService.saveSetExportExcel(elecExportFields);
		return "close";
	}
}
