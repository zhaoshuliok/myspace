package cn.itcast.elec.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecExportFieldsDao;
import cn.itcast.elec.domain.ElecExportFields;
import cn.itcast.elec.service.IElecExportFieldsService;

@Service(IElecExportFieldsService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecExportFieldsServiceImpl implements IElecExportFieldsService {

	@Resource(name=IElecExportFieldsDao.SERVICE_NAME)
	private IElecExportFieldsDao elecExportFieldsDao;

	//使用主键ID，查询导出设置表的对象
	public ElecExportFields findExportFieldsById(String belongTo) {
		return elecExportFieldsDao.findObjectByID(belongTo);
	}
	
	//使用主键ID，完成更新
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveSetExportExcel(ElecExportFields elecExportFields) {
		elecExportFieldsDao.update(elecExportFields);
	}
}
