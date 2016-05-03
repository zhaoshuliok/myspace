package cn.itcast.elec.service;

import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.web.form.Pagenation;

public interface IElecDevicePlanService {

	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecDevicePlanServiceImpl";

	void findDevicePlanByCondition(ElecDevicePlan elecDevicePlan,
			Pagenation<ElecDevicePlan> pagenation);

	void save(ElecDevicePlan elecDevicePlan);

	void deleteById(ElecDevicePlan elecDevicePlan);

	void updatePlanTime(String devPlanId);

	void buyDevice(String devPlanId);

}
