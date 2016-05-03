package cn.itcast.elec.dao;

import cn.itcast.elec.domain.ElecDevice;

public interface IElecDeviceDao extends ICommonDao<ElecDevice> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecDeviceDaoImpl";

}
