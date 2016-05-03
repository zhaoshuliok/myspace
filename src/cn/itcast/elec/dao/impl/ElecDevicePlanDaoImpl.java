package cn.itcast.elec.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecDevicePlanDao;
import cn.itcast.elec.domain.ElecDevicePlan;
import cn.itcast.elec.web.form.Pagenation;

@Repository(IElecDevicePlanDao.SERVICE_NAME)
public class ElecDevicePlanDaoImpl extends CommonDaoImpl<ElecDevicePlan> implements IElecDevicePlanDao {

	/**使用sql语句，查询对应的设备购置计划的集合*/
	public void findDevicePlanByCondition(String condition, final Object[] params,
			Map<String, String> orderby, final Pagenation<ElecDevicePlan> pagenation) {
		String sql = "SELECT dp.devPlanId as devPlanId,ddl1.ddlName as jctidName,dp.DevName as devName,"
				+ " ddl2.ddlName as devTypeName,dp.trademark as trademark,dp.SpecType as SpecType,"
				+ " dp.produceHome as produceHome,dp.produceArea as produceArea, "
				+ " dp.Useness as Useness,dp.quality as quality,"
				+ " dp.useUnit as useUnit, dp.devExpense as devExpense,dp.planDate as planDate,"
				+ " dp.adjustPeriod as adjustPeriod,dp.overhaulPeriod as overhaulPeriod," 
				+ " dp.configure as configure,dp.ecomment as ecomment,dp.purchaseState as purchaseState,"
				+ " dp.isDelete as isDelete,dp.createEmpId as createEmpId,dp.createDate as createDate," 
				+ " dp.lastEmpId as lastEmpId,dp.lastDate as lastDate,"
				+ " dp.qunit as qunit,dp.apunit as apunit,dp.opunit as opunit,"
				+ " dp.jctId as jctId,dp.devType as devType"
				+ " FROM Elec_Device_Plan dp LEFT OUTER JOIN  "
				+ " Elec_SystemDDL ddl1 ON dp.JctID = ddl1.DdlCode and ddl1.Keyword='所属单位' "
				+ " LEFT OUTER JOIN Elec_SystemDDL ddl2 ON  dp.DevType = ddl2.DdlCode and ddl2.Keyword='设备类型' "
                + " where 1=1 ";
	
		final String finalSql = sql+condition+ " and dp.PurchaseState='0' " + this.orderbyHql(orderby);
		//执行查询
		List<Object[]> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(finalSql);
				if(params!=null && params.length>0){
					for(int i=0;i<params.length;i++){
						query.setParameter(i, params[i]);
					}
				}
				/**easyui的分页*/
				//初始化总的记录数total
				pagenation.setTotal(query.list().size());
				int firstResult = (pagenation.getPage()-1)*pagenation.getPageSize();
				int maxResult = pagenation.getPageSize();
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResult);
				/***/
				return query.list();
			}
		
		});
		//返回的集合
		List<ElecDevicePlan> devicePlanList = new ArrayList<ElecDevicePlan>();
		//将Object[]转换成ElecDevicePlan
		for (int i = 0; i < list.size(); i++) {
			ElecDevicePlan elecDevicePlan = new ElecDevicePlan();//VO对象
			Object[] ret = (Object[]) list.get(i);			
			elecDevicePlan.setDevPlanId(ret[0]!=null?ret[0].toString():"");
			elecDevicePlan.setJctIdName(ret[1]!=null?ret[1].toString():"");//数据字典（所属单位）（数据项的值）
			elecDevicePlan.setDevName(ret[2]!=null?ret[2].toString():"");
			elecDevicePlan.setDevTypeName(ret[3]!=null?ret[3].toString():"");//数据字典（所属单位）（数据项的值）
			elecDevicePlan.setTrademark(ret[4]!=null?ret[4].toString():"");
			elecDevicePlan.setSpecType(ret[5]!=null?ret[5].toString():"");
			elecDevicePlan.setProduceHome(ret[6]!=null?ret[6].toString():"");
			elecDevicePlan.setProduceArea(ret[7]!=null?ret[7].toString():"");
			elecDevicePlan.setUseness(ret[8]!=null?ret[8].toString():"");
			elecDevicePlan.setQuality(ret[9]!=null?ret[9].toString():"");
			elecDevicePlan.setUseUnit(ret[10]!=null?ret[10].toString():"");
			elecDevicePlan.setDevExpense(ret[11]!=null?Double.parseDouble(ret[11].toString()):0);
			elecDevicePlan.setPlanDate(ret[12]!=null?(Date)ret[12]:null);
			elecDevicePlan.setAdjustPeriod(ret[13]!=null?ret[13].toString():"");
			elecDevicePlan.setOverhaulPeriod(ret[14]!=null?ret[14].toString():"");
			elecDevicePlan.setConfigure(ret[15]!=null?ret[15].toString():"");
			elecDevicePlan.setEcomment(ret[16]!=null?ret[16].toString():"");
			elecDevicePlan.setPurchaseState(ret[17]!=null?ret[17].toString():"");
			elecDevicePlan.setIsDelete(ret[18]!=null?ret[18].toString():"");
			elecDevicePlan.setCreateEmpId(ret[19]!=null?ret[19].toString():"");
			elecDevicePlan.setCreateDate(ret[20]!=null?(Date)ret[20]:null);
			elecDevicePlan.setLastEmpId(ret[21]!=null?ret[21].toString():"");
			elecDevicePlan.setLastDate(ret[22]!=null?(Date)ret[22]:null);
			elecDevicePlan.setQunit(ret[23]!=null?ret[23].toString():"");
			elecDevicePlan.setApunit(ret[24]!=null?ret[24].toString():"");
			elecDevicePlan.setOpunit(ret[25]!=null?ret[25].toString():"");
			elecDevicePlan.setJctId(ret[26]!=null?ret[26].toString():"");//数据字典（所属单位）（数据项的编号）
			elecDevicePlan.setDevType(ret[27]!=null?ret[27].toString():"");//数据字典（所属单位）（数据项的编号）			
			devicePlanList.add(elecDevicePlan);
		}
		//初始化结果集集合
		pagenation.setRows(devicePlanList);
	}
	
	/**使用主键ID，查询设备购置计划信息*/
	public ElecDevicePlan findDevPlanById(final String devPlanId) {
		final String sql = "SELECT dp.devPlanId as devPlanId,ddl1.ddlName as jctidName,dp.DevName as devName,"
				+ " ddl2.ddlName as devTypeName,dp.trademark as trademark,dp.SpecType as SpecType,"
				+ " dp.produceHome as produceHome,dp.produceArea as produceArea, "
				+ " dp.Useness as Useness,dp.quality as quality,"
				+ " dp.useUnit as useUnit, dp.devExpense as devExpense,dp.planDate as planDate,"
				+ " dp.adjustPeriod as adjustPeriod,dp.overhaulPeriod as overhaulPeriod," 
				+ " dp.configure as configure,dp.ecomment as ecomment,dp.purchaseState as purchaseState,"
				+ " dp.isDelete as isDelete,dp.createEmpId as createEmpId,dp.createDate as createDate," 
				+ " dp.lastEmpId as lastEmpId,dp.lastDate as lastDate,"
				+ " dp.qunit as qunit,dp.apunit as apunit,dp.opunit as opunit,"
				+ " dp.jctId as jctId,dp.devType as devType"
				+ " FROM Elec_Device_Plan dp LEFT OUTER JOIN  "
				+ " Elec_SystemDDL ddl1 ON dp.JctID = ddl1.DdlCode and ddl1.Keyword='所属单位' "
				+ " LEFT OUTER JOIN Elec_SystemDDL ddl2 ON  dp.DevType = ddl2.DdlCode and ddl2.Keyword='设备类型' "
                + " where 1=1 and dp.devPlanId = ?";
		//执行查询
		List<Object[]> list = this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter(0, devPlanId);
				return query.list();
			}
		
		});
		//返回对象
		ElecDevicePlan elecDevicePlan = new ElecDevicePlan();//VO对象
		if(list!=null && list.size()>0){
			Object[] ret = (Object[]) list.get(0);//只有1条数据（主键查询）
			elecDevicePlan.setDevPlanId(ret[0]!=null?ret[0].toString():"");
			elecDevicePlan.setJctIdName(ret[1]!=null?ret[1].toString():"");//数据字典（所属单位）（数据项的值）
			elecDevicePlan.setDevName(ret[2]!=null?ret[2].toString():"");
			elecDevicePlan.setDevTypeName(ret[3]!=null?ret[3].toString():"");//数据字典（所属单位）（数据项的值）
			elecDevicePlan.setTrademark(ret[4]!=null?ret[4].toString():"");
			elecDevicePlan.setSpecType(ret[5]!=null?ret[5].toString():"");
			elecDevicePlan.setProduceHome(ret[6]!=null?ret[6].toString():"");
			elecDevicePlan.setProduceArea(ret[7]!=null?ret[7].toString():"");
			elecDevicePlan.setUseness(ret[8]!=null?ret[8].toString():"");
			elecDevicePlan.setQuality(ret[9]!=null?ret[9].toString():"");
			elecDevicePlan.setUseUnit(ret[10]!=null?ret[10].toString():"");
			elecDevicePlan.setDevExpense(ret[11]!=null?Double.parseDouble(ret[11].toString()):0);
			elecDevicePlan.setPlanDate(ret[12]!=null?(Date)ret[12]:null);
			elecDevicePlan.setAdjustPeriod(ret[13]!=null?ret[13].toString():"");
			elecDevicePlan.setOverhaulPeriod(ret[14]!=null?ret[14].toString():"");
			elecDevicePlan.setConfigure(ret[15]!=null?ret[15].toString():"");
			elecDevicePlan.setEcomment(ret[16]!=null?ret[16].toString():"");
			elecDevicePlan.setPurchaseState(ret[17]!=null?ret[17].toString():"");
			elecDevicePlan.setIsDelete(ret[18]!=null?ret[18].toString():"");
			elecDevicePlan.setCreateEmpId(ret[19]!=null?ret[19].toString():"");
			elecDevicePlan.setCreateDate(ret[20]!=null?(Date)ret[20]:null);
			elecDevicePlan.setLastEmpId(ret[21]!=null?ret[21].toString():"");
			elecDevicePlan.setLastDate(ret[22]!=null?(Date)ret[22]:null);
			elecDevicePlan.setQunit(ret[23]!=null?ret[23].toString():"");
			elecDevicePlan.setApunit(ret[24]!=null?ret[24].toString():"");
			elecDevicePlan.setOpunit(ret[25]!=null?ret[25].toString():"");
			elecDevicePlan.setJctId(ret[26]!=null?ret[26].toString():"");//数据字典（所属单位）（数据项的编号）
			elecDevicePlan.setDevType(ret[27]!=null?ret[27].toString():"");//数据字典（所属单位）（数据项的编号）	
		}
		return elecDevicePlan;
	}
	
	/**更新计划时间，推迟3个月*/
	public void updatePlanTime(final String devPlanId) {
		final String sql = "update elec_device_plan set plandate = add_months(plandate,'3') " +
					 " where devplanid = ? and plandate>sysdate";
		this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter(0, devPlanId);
				int row = query.executeUpdate();
				return null;
			}
			
		});
	}
}
