package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecRole;

public interface IElecRoleDao extends ICommonDao<ElecRole> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecRoleDaoImpl";

	List<ElecRole> findRoleListByLogonName(String logonName);

}
