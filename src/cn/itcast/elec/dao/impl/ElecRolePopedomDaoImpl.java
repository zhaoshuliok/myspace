package cn.itcast.elec.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecRolePopedomDao;
import cn.itcast.elec.domain.ElecPopedom;
import cn.itcast.elec.domain.ElecRolePopedom;

@Repository(IElecRolePopedomDao.SERVICE_NAME)
public class ElecRolePopedomDaoImpl extends CommonDaoImpl<ElecRolePopedom> implements IElecRolePopedomDao {

	/**使用角色的集合，获取权限的数据*/
	public List<ElecPopedom> findPopedomListByRoleIds(String roleCondition) {
		String hql = "select distinct new cn.itcast.elec.domain.ElecPopedom(a.id) from ElecRolePopedom a where a.roleID in ("+roleCondition+")";
		List<ElecPopedom> list = this.getHibernateTemplate().find(hql);
		return list;
	}
}
