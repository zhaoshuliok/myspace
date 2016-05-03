package cn.itcast.elec.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecCommonMsgContentDao;
import cn.itcast.elec.domain.ElecCommonMsgContent;

@Repository(IElecCommonMsgContentDao.SERVICE_NAME)
public class ElecCommonMsgContentDaoImpl extends CommonDaoImpl<ElecCommonMsgContent> implements IElecCommonMsgContentDao {

}
