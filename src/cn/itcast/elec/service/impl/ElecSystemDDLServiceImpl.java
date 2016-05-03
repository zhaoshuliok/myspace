package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.p;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecSystemDDLDao;
import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.service.IElecSystemDDLService;
import cn.itcast.elec.util.StringUtil;

@Service(IElecSystemDDLService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecSystemDDLServiceImpl implements IElecSystemDDLService {

	@Resource(name=IElecSystemDDLDao.SERVICE_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;

	//1：初始化类型列表，并去掉重复值，返回List<ElecSystemDDL>，遍历在页面的下拉菜单中
	public List<ElecSystemDDL> findSystemDDLListWithDistinct() {
		return elecSystemDDLDao.findSystemDDLListWithDistinct();
	}
	
	/**使用数据类型作为查询条件，查询对应数据类型的集合，返回List<ElecSystemDDL>，将集合遍历在dictionaryEdit.jsp中*/
	@Cacheable(value="elec")
	public List<ElecSystemDDL> findSystemDDLListByKeyword(String keyword) {
		//查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(keyword)){
			condition += " and o.keyword = ?";
			paramsList.add(keyword);
		}
		Object[] params = paramsList.toArray();
		//排序
		Map<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.ddlCode", "asc");
		List<ElecSystemDDL> list = elecSystemDDLDao.findCollectionByConditionNoPage(condition, params, orderby);
		return list;
	}
	
	/**数据字典的保存*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(ElecSystemDDL elecSystemDDL) {
		//数据项的值
		String [] itemnames = elecSystemDDL.getItemname();
		//存放数据类型
		String keywordname = elecSystemDDL.getKeywordname();
		/**
		 * 业务的标识字段
		 *  new：新增一种新的数据类型
		 *  add：在原数据类型的基础上进行编辑和修改
		 */
		String typeflag = elecSystemDDL.getTypeflag();
		//1：使用传递的业务标识作为判断
		//2：如果是新增一种数据类型
		if(typeflag!=null && typeflag.equals("new")){
			//遍历数组，组织PO对象，执行保存
			this.saveSystemDDL(itemnames,keywordname);
		}
		//2：如果是在已有数据类型基础上进行编辑和修改
		else{
			//* 使用数据类型查询之前数据类型对应的数据集合
			List<ElecSystemDDL> list = this.findSystemDDLListByKeyword(keywordname);
			//* 先删除集合
			elecSystemDDLDao.deleteObjectByCollection(list);
			//* 遍历数组，组织PO对象，执行保存
			this.saveSystemDDL(itemnames,keywordname);
		}
	}

	//遍历数组，组织PO对象，执行保存
	private void saveSystemDDL(String[] itemnames, String keywordname) {
		if(itemnames!=null && itemnames.length>0){
			for(int i=0;i<itemnames.length;i++){
				ElecSystemDDL systemDDL = new ElecSystemDDL();
				systemDDL.setKeyword(keywordname);
				systemDDL.setDdlCode(i+1);
				systemDDL.setDdlName(itemnames[i]);
				elecSystemDDLDao.save(systemDDL);
			}
		}
	}
	
	/**使用数据类型和数据项的编号获取数据项的值*/
	public String findDdlNameByKeywordAndDdlCode(String keyword, String ddlCode) {
		return elecSystemDDLDao.findDdlNameByKeywordAndDdlCode(keyword,ddlCode);
	}
	
	/**使用数据类型和数据项的值获取数据项的编号*/
	public String findDdlCodeByKeywordAndDdlName(String keyword, String ddlName) {
		return elecSystemDDLDao.findDdlCodeByKeywordAndDdlName(keyword,ddlName);
	}
}
