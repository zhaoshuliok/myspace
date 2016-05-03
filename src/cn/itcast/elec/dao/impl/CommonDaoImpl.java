package cn.itcast.elec.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.elec.dao.ICommonDao;
import cn.itcast.elec.util.PageInfo;
import cn.itcast.elec.util.TUtils;

public class CommonDaoImpl<T> extends HibernateDaoSupport implements ICommonDao<T> {

	/**泛型转换*/
	Class entityClass = TUtils.getActualType(this.getClass());
	
	/**
	 * spring容器中配置
	 * <!-- 配置文件 -->
		<bean id="commonDao" class="cn.itcast.elec.dao.impl.CommonDaoImpl">
			<property name="sessionFactory" ref="sessionFactory"></property>
		</bean>
	 */
	@Resource(name="sessionFactory")
	public void setDi(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
	
	/**保存*/
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}
	
	/**更新*/
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	/**使用主键ID，查询对象*/
	public T findObjectByID(Serializable id) {
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}
	
	/**使用主键ID的数组或者字符串（1个值或者多个值），进行删除*/
	public void deleteObjectByIDs(Serializable... ids) {
		if(ids!=null && ids.length>0){
			for(Serializable id:ids){
				//先查询对象
				Object entity = this.findObjectByID(id);
				//再删除
				this.getHibernateTemplate().delete(entity);
			}
		}
	}
	
	/**使用集合，进行删除*/
	public void deleteObjectByCollection(List<T> list) {
		this.getHibernateTemplate().deleteAll(list);
	}
	
	/**使用集合，进行新增*/
	public void saveCollection(List<T> list) {
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}
	
	/**使用查询条件，查询对应的集合列表*/
	/**
	 * select * from elec_text o where 1=1 --封装到DAO
		and o.textName like '%张%'  --封装到Service
		and o.textRemark like '%张%'  --封装到Service
		order by o.textDate asc,o.textName desc  --封装到Service
	 */
	public List<T> findCollectionByConditionNoPage(String condition,
			final Object[] params, Map<String, String> orderby) {
		String hql = "from "+entityClass.getSimpleName()+" o where 1=1 ";
		String orderbyHql = this.orderbyHql(orderby);
		final String finalHql = hql+condition+orderbyHql;
		/**方案一*/
//		List<T> list = this.getHibernateTemplate().find(finalHql,params);
		/**方案二，使用HibernateTemplate中的回调函数，调用Session*/
		List<T> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						query.setParameter(i, params[i]);
					}
				}
				//query.setCacheable(true);//开启二级缓存
				return query.list();
			}
			
		});
		return list;
	}
	
	/**使用查询条件，查询对应的集合列表*/
	/**
	 * select * from elec_text o where 1=1 --封装到DAO
		and o.textName like '%张%'  --封装到Service
		and o.textRemark like '%张%'  --封装到Service
		order by o.textDate asc,o.textName desc  --封装到Service
	 */
	public List<T> findCollectionByConditionWithPage(String condition,
			final Object[] params, Map<String, String> orderby,final PageInfo info) {
		String hql = "from "+entityClass.getSimpleName()+" o where 1=1 ";
		String orderbyHql = this.orderbyHql(orderby);
		final String finalHql = hql+condition+orderbyHql;
		/**方案一*/
//		List<T> list = this.getHibernateTemplate().find(finalHql,params);
		/**方案二，使用HibernateTemplate中的回调函数，调用Session*/
		List<T> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						query.setParameter(i, params[i]);
					}
				}
				/**添加分页 begin*/
				//初始化总的记录数
				info.setTotalResult(query.list().size());
				query.setFirstResult(info.getBeginResult());//当前页从第几条开始检索，默认是0,0表示第一条
				query.setMaxResults(info.getPageSize());//当前页最多显示的记录数
				/**添加分页end*/
				return query.list();
			}
			
		});
		return list;
	}

	/**排序的语句：order by o.textDate asc,o.textName desc*/
	public String orderbyHql(Map<String, String> orderby) {
		StringBuffer buffer = new StringBuffer();
		if(orderby!=null && orderby.size()>0){
			buffer.append(" order by ");
			for(Map.Entry<String, String> map:orderby.entrySet()){
				buffer.append(map.getKey()).append(" ").append(map.getValue()).append(",");
			}
			//在循环的最后，删除最后一个逗号
			buffer.deleteCharAt(buffer.length()-1);
		}
		return buffer.toString();
	}
}
