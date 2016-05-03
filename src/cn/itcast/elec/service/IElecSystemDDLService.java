package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecFileUpload;
import cn.itcast.elec.domain.ElecSystemDDL;

public interface IElecSystemDDLService {

	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecSystemDDLServiceImpl";

	List<ElecSystemDDL> findSystemDDLListWithDistinct();

	List<ElecSystemDDL> findSystemDDLListByKeyword(String keyword);

	void save(ElecSystemDDL elecSystemDDL);

	String findDdlNameByKeywordAndDdlCode(String keyword, String ddlCode);

	String findDdlCodeByKeywordAndDdlName(String keyword, String ddlName);



}
