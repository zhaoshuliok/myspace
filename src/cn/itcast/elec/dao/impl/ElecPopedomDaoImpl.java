package cn.itcast.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecPopedomDao;
import cn.itcast.elec.domain.ElecPopedom;

@Repository(IElecPopedomDao.SERVICE_NAME)
public class ElecPopedomDaoImpl extends CommonDaoImpl<ElecPopedom> implements IElecPopedomDao {

	/**使用查询条件（登录名和参数）获取当前用户具有的权限*/
	public List<ElecPopedom> findPopedomListByCondition(final String logonName,
			final String generatemenu) {
		final String sql = "select distinct a.id as \"id\",a.pid as \"pid\",a.name as \"name\",a.page as \"page\" from elec_popedom a " +
					 " inner join elec_role_popedom b on a.id = b.id " +
					 " inner join elec_role c on b.roleid = c.roleid " +
					 " inner join elec_user_role d on c.roleid = d.roleid " +
					 " inner join elec_user e on d.userid =e.userid " +
					 " where 1=1 and e.logonName = ? and a.isMenu = '0' and a.generatemenu = ? order by a.id";
		List<ElecPopedom> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter(0, logonName);
				query.setParameter(1, generatemenu);
				//查询投影部分 a.id,a.pid,a.name,a.page的属性名称和返回的List<ElecPopedom>中的ElecPopedom对象要一致，大小写都要一致
				query.setResultTransformer(Transformers.aliasToBean(ElecPopedom.class));
				return query.list();
			}
			
		});
		return list;
	}
}
