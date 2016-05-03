package junit;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.domain.ElecText;

public class TestHibernateCache {

	/**测试一级缓存*/
	@Test
	public void testFirstCache(){
		Configuration configuration = new Configuration();
		//加载src下的Hibernate.cfg.xml
		configuration.configure();
		SessionFactory sf = configuration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();
		
		ElecSystemDDL elecSystemDDL1 = (ElecSystemDDL) s.get(ElecSystemDDL.class, 1);//select语句
		ElecSystemDDL elecSystemDDL2 = (ElecSystemDDL) s.get(ElecSystemDDL.class, 1);//不会出现select语句，从一级缓存
		
		tr.commit();
		s.close();//s关闭，一级缓存就没有了
		
	}
	
	/**测试二级缓存*/
	@Test
	public void testSecondeCache(){
		Configuration configuration = new Configuration();
		//加载src下的Hibernate.cfg.xml
		configuration.configure();
		SessionFactory sf = configuration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();
		
		ElecSystemDDL elecSystemDDL1 = (ElecSystemDDL) s.get(ElecSystemDDL.class, 1);//select语句
		ElecSystemDDL elecSystemDDL2 = (ElecSystemDDL) s.get(ElecSystemDDL.class, 1);//不会出现select语句，从一级缓存
		
		tr.commit();
		s.close();//s关闭，一级缓存就没有了
		/****************************************/
		s = sf.openSession();
		tr = s.beginTransaction();
		
		ElecSystemDDL elecSystemDDL3 = (ElecSystemDDL) s.get(ElecSystemDDL.class, 1);//select语句
		
		tr.commit();
		s.close();//s关闭，一级缓存就没有了
		
		
	}
	
	/**测试二级缓存（查询缓存）*/
	@Test
	public void testQueryCache(){
		Configuration configuration = new Configuration();
		//加载src下的Hibernate.cfg.xml
		configuration.configure();
		SessionFactory sf = configuration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();
		
		String hql = "select ddlName from ElecSystemDDL where keyword='性别' and ddlCode = 1";
		Query query = s.createQuery(hql);
		query.setCacheable(true);//开启了查询缓存
		query.list();
		
		tr.commit();
		s.close();//s关闭，一级缓存就没有了
		/****************************************/
		s = sf.openSession();
		tr = s.beginTransaction();
		
		Query query1 = s.createQuery(hql);
		query1.setCacheable(true);//开启了查询缓存
		query1.list();
		
		tr.commit();
		s.close();//s关闭，一级缓存就没有了
		
		
	}
}
