package cn.itcast.elec.service;

import cn.itcast.elec.domain.ElecExportFields;

public interface IElecExportFieldsService {

	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecExportFieldsServiceImpl";

	ElecExportFields findExportFieldsById(String belongTo);

	void saveSetExportExcel(ElecExportFields elecExportFields);

}
