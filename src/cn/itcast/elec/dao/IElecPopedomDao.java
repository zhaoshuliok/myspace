package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecPopedom;

public interface IElecPopedomDao extends ICommonDao<ElecPopedom> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecPopedomDaoImpl";

	List<ElecPopedom> findPopedomListByCondition(String logonName,
			String generatemenu);

}
