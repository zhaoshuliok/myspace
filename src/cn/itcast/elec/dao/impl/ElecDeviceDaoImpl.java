package cn.itcast.elec.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecDeviceDao;
import cn.itcast.elec.domain.ElecDevice;

@Repository(IElecDeviceDao.SERVICE_NAME)
public class ElecDeviceDaoImpl extends CommonDaoImpl<ElecDevice> implements IElecDeviceDao {

}
