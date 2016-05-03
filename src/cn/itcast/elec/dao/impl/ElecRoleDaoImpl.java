package cn.itcast.elec.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecRoleDao;
import cn.itcast.elec.domain.ElecRole;

@Repository(IElecRoleDao.SERVICE_NAME)
public class ElecRoleDaoImpl extends CommonDaoImpl<ElecRole> implements IElecRoleDao {

	/**使用当前用户的登录名，查询角色的集合*/
	/**
	 * --sql语句
		select a.roleID,a.roleName from elec_role a
		inner join elec_user_role b on a.roleid = b.roleid
		inner join elec_user c on b.userid = c.userid
		where 1=1 and c.logonName = 'lisi'
		____________________________
		Hibernate: select elecrole0_.roleID as roleID7_, elecrole0_.roleName as roleName7_ 
		from Elec_Role elecrole0_ 
		inner join elec_user_role elecusers1_ on elecrole0_.roleID=elecusers1_.roleID 
		inner join Elec_User elecuser2_ on elecusers1_.userID=elecuser2_.userID 
		where elecuser2_.logonName=?

	 */
	public List<ElecRole> findRoleListByLogonName(String logonName) {
		String hql = "select a from ElecRole a inner join a.elecUsers b where b.logonName = ?";
		List<ElecRole> list = this.getHibernateTemplate().find(hql,logonName);
		return list;
	}
}
