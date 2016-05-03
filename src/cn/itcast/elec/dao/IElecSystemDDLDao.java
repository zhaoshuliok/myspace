package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecSystemDDL;

public interface IElecSystemDDLDao extends ICommonDao<ElecSystemDDL> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecSystemDDLDaoImpl";

	List<ElecSystemDDL> findSystemDDLListWithDistinct();

	String findDdlNameByKeywordAndDdlCode(String keyword, String ddlCode);

	String findDdlCodeByKeywordAndDdlName(String keyword, String ddlName);

}
