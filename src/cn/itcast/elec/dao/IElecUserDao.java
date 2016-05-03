package cn.itcast.elec.dao;

import java.util.List;
import java.util.Map;

import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.util.PageInfo;

public interface IElecUserDao extends ICommonDao<ElecUser> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecUserDaoImpl";

	List<ElecUser> findCollectionByConditionWithPageAndSql(String condition,
			Object[] params, Map<String, String> orderby, PageInfo pageInfo);

	List findExcelFieldData(String condition, Object[] params,
			Map<String, String> orderby, String selectCondition);

	List<Object[]> findChartDataSetByUser(String zName, String eName);

	

}
