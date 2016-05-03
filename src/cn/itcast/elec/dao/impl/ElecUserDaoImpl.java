package cn.itcast.elec.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecUserDao;
import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.util.PageInfo;

@Repository(IElecUserDao.SERVICE_NAME)
public class ElecUserDaoImpl extends CommonDaoImpl<ElecUser> implements IElecUserDao {

	/**查询用户信息，使用sql语句的联合查询*/
	public List<ElecUser> findCollectionByConditionWithPageAndSql(
			final String condition, final Object[] params, Map<String, String> orderby,
			final PageInfo info) 
			{
				String sql = "select a.userID as userID,a.logonName as logonName,a.userName as userName,b.ddlName as sexID,a.contactTel as contactTel,a.ondutydate as onDutyDate,c.ddlName as postID from elec_user a " +
							 " inner join elec_systemddl b on a.sexid = b.ddlcode and b.keyword='性别' " +
							 " inner join elec_systemddl c on a.postid = c.ddlcode and c.keyword='职位' " +
							 " where 1=1  ";
				String orderbySql = this.orderbyHql(orderby);
				final String finalSql = sql+condition+orderbySql;
				List<Object []> list = this.getHibernateTemplate().execute(new HibernateCallback() {
	
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery query = session.createSQLQuery(finalSql);
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
				//将Object[]转换成ElecUser对象
				List<ElecUser> userList = new ArrayList<ElecUser>();
				if(list!=null && list.size()>0){
					for(Object [] o:list){
						ElecUser elecUser = new ElecUser();
						//主键ID   登录名 	用户姓名 	性别 	联系电话 	入职时间 	职位
						elecUser.setUserID(o[0]!=null?o[0].toString():"");
						elecUser.setLogonName(o[1]!=null?o[1].toString():"");
						elecUser.setUserName(o[2]!=null?o[2].toString():"");
						elecUser.setSexID(o[3]!=null?o[3].toString():"");
						elecUser.setContactTel(o[4]!=null?o[4].toString():"");
						elecUser.setOnDutyDate(o[5]!=null?(Date)o[5]:null);
						elecUser.setPostID(o[6]!=null?o[6].toString():"");
						userList.add(elecUser);
					}
				}
				return userList;
			}
	/**
	 * 使用sql语句，导出用户管理的excel报表
	 */
	public List findExcelFieldData(String condition, final Object[] params,
			Map<String, String> orderby, String selectCondition) {
		String sql = "select "+selectCondition+" from elec_user a "+
					 " inner join elec_systemddl b on a.sexid = b.ddlcode and b.keyword='性别' " +
					 " inner join elec_systemddl c on a.postid = c.ddlcode and c.keyword='职位' " +
					 " inner join elec_systemddl d on a.jctid = d.ddlcode and d.keyword='所属单位' " + 
					 " inner join elec_systemddl e on a.isduty = e.ddlcode and e.keyword='是否在职' " +
					 " where 1=1";
		String orderbySql = this.orderbyHql(orderby);
		final String finalSql = sql+condition+orderbySql;
		List list = this.getHibernateTemplate().execute(new HibernateCallback() {
	
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(finalSql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						query.setParameter(i, params[i]);
					}
				}
				return query.list();
			}
			
		});
		return list;
	}
	
	/**人员统计*/
	public List<Object[]> findChartDataSetByUser(String zName, String eName) {
		/**聚合函数*/
		final String sql = "select b.keyword,b.ddlname,count(b.Ddlcode) from elec_user a " +
					 " inner join elec_systemddl b on a."+eName+" = b.ddlcode and b.keyword='"+zName+"' " + 
					 " where 1=1 and a.isduty='1' " +
					 " group by b.keyword,b.ddlname,b.ddlcode " +
					 " order by b.ddlcode asc ";
		List<Object[]> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				return query.list();
			}
			
		});
		
		return list;
	}
}
