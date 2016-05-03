package cn.itcast.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecSystemDDLDao;
import cn.itcast.elec.domain.ElecSystemDDL;

@Repository(IElecSystemDDLDao.SERVICE_NAME)
public class ElecSystemDDLDaoImpl extends CommonDaoImpl<ElecSystemDDL> implements IElecSystemDDLDao {

	//始化类型列表，并去掉重复值，返回List<ElecSystemDDL>，遍历在页面的下拉菜单中
	public List<ElecSystemDDL> findSystemDDLListWithDistinct() {
		String hql = "select distinct new cn.itcast.elec.domain.ElecSystemDDL(t.keyword) from ElecSystemDDL t";
		//方案一
//		List<Object> list = this.getHibernateTemplate().find(hql);
		//返回的是List<ElecSystemDDL> 
//		List<ElecSystemDDL> systemList = new ArrayList<ElecSystemDDL>();
//		if(list!=null && list.size()>0){
//			for(Object o:list){
//				ElecSystemDDL ddl = new ElecSystemDDL();
//				ddl.setKeyword(o.toString());
//				systemList.add(ddl);
//			}
//		}
//		return systemList;
		//方案二
		List<ElecSystemDDL> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	
	/**使用数据类型和数据项的编号获取数据项的值*/
	@Cacheable(value="elec")
	public String findDdlNameByKeywordAndDdlCode(final String keyword, final String ddlCode) {
		final String hql = "select ddlName from ElecSystemDDL where keyword=? and ddlCode = ?";
		List<Object> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter(0, keyword);
				query.setParameter(1, Integer.parseInt(ddlCode));
				//query.setCacheable(true);
				return query.list();
			}
			
		});
		String ddlName = "";
		if(list!=null && list.size()>0){
			ddlName = list.get(0).toString();
		}
		return ddlName;
	}
	
	/**使用数据类型和数据项的值获取数据项的编号*/
	@Cacheable(value="elec")
	public String findDdlCodeByKeywordAndDdlName(final String keyword, final String ddlName) {
		final String hql = "select ddlCode from ElecSystemDDL where keyword=? and ddlName = ?";
		List<Object> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter(0, keyword);
				query.setParameter(1, ddlName);
				//query.setCacheable(true);
				return query.list();
			}
			
		});
		String ddlCode = "";
		if(list!=null && list.size()>0){
			ddlCode = list.get(0).toString();
		}
		return ddlCode;
	}
}
