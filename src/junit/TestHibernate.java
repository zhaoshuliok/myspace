package junit;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.elec.domain.ElecText;

public class TestHibernate {

	/**测试保存*/
	@Test
	public void save(){
		Configuration configuration = new Configuration();
		//加载src下的Hibernate.cfg.xml
		configuration.configure();
		SessionFactory sf = configuration.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();
		
		ElecText elecText = new ElecText();
		elecText.setTextName("测试Hibernate名称1");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试Hibernate备注1");
		s.save(elecText);
		
		tr.commit();
		s.close();
		
	}
}
