package cn.itcast.elec.webservice.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.elec.dao.IElecSystemDDLDao;
import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.webservice.IWebSystemDDLService;

public class WebSystemDDLServiceImpl implements IWebSystemDDLService {

	private IElecSystemDDLDao elecSystemDDLDao;
	
	public void setElecSystemDDLDao(IElecSystemDDLDao elecSystemDDLDao) {
		this.elecSystemDDLDao = elecSystemDDLDao;
	}

	public List<ElecSystemDDL> findSystemByKeyword(String keyword) {
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

}
