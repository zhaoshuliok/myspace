package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecPopedom;
import cn.itcast.elec.domain.ElecRolePopedom;

public interface IElecRolePopedomDao extends ICommonDao<ElecRolePopedom> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecRolePopedomDaoImpl";

	List<ElecPopedom> findPopedomListByRoleIds(String roleCondition);

}
