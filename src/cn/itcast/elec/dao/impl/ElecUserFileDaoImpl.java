package cn.itcast.elec.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecUserFileDao;
import cn.itcast.elec.domain.ElecUserFile;

@Repository(IElecUserFileDao.SERVICE_NAME)
public class ElecUserFileDaoImpl extends CommonDaoImpl<ElecUserFile> implements IElecUserFileDao {

}
