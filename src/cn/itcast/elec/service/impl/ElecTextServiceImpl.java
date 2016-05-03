package cn.itcast.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;

@Service(IElecTextService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecTextServiceImpl implements IElecTextService {

	@Resource(name=IElecTextDao.SERVICE_NAME)
	private IElecTextDao elecTextDao;
	
	/**测试保存，增删改的方法事务要可写*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecText(ElecText elecText) {
		elecTextDao.save(elecText);
	}
	
	/**使用查询条件，查询对应的集合列表*/
	/**
	 * select * from elec_text o where 1=1 --封装到DAO
		and o.textName like '%张%'  --封装到Service
		and o.textRemark like '%张%'  --封装到Service
		order by o.textDate asc,o.textName desc  --封装到Service
	 */
	public List<ElecText> findCollectionByConditionNoPage(ElecText elecText) {
		//查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//获取测试名称
		String textName = elecText.getTextName();
		if(StringUtils.isNotBlank(textName)){
			condition += " and o.textName like ?";
			paramsList.add("%"+textName+"%");
		}
		//获取测试的备注
		String textRemark = elecText.getTextRemark();
		if(StringUtils.isNotBlank(textRemark)){
			condition += " and o.textRemark like ?";
			paramsList.add("%"+textRemark+"%");
		}
		//传递可变参数，转换成数组
		Object[] params = paramsList.toArray();
		//排序
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.textDate", "asc");
		orderby.put("o.textName", "desc");
		//查询集合
		List<ElecText> list = elecTextDao.findCollectionByConditionNoPage(condition,params,orderby);
		return list;
	}

}
