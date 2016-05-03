package cn.itcast.elec.dao;

import java.util.Map;

import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.web.form.Pagenation;

public interface IElecDevicePlanDao extends ICommonDao<ElecDevicePlan> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecDevicePlanDaoImpl";

	void findDevicePlanByCondition(String condition, Object[] params,
			Map<String, String> orderby, Pagenation<ElecDevicePlan> pagenation);

	ElecDevicePlan findDevPlanById(String devPlanId);

	void updatePlanTime(String devPlanId);

}
