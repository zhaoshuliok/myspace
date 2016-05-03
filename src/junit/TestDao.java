package junit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.service.IElecTextService;

public class TestDao {

	/**测试保存*/
	@Test
	public void save(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
		
		ElecText elecText = new ElecText();
		elecText.setTextName("测试Dao名称1");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试Dao备注1");
		
		elecTextDao.save(elecText);
		
	}
	
	/**测试更新*/
	@Test
	public void update(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
	
		ElecText elecText = new ElecText();
		elecText.setTextID("4028817b511dc7b401511dc7bf7f0000");
		elecText.setTextName("赵六");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("赵小六");
		
		elecTextDao.update(elecText);
		
	}
	
	/**使用主键ID，查询对象*/
	@Test
	public void findObjectByID(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
		
		Serializable id = "4028817b511dc7b401511dc7bf7f0000";
		ElecText elecText = elecTextDao.findObjectByID(id);
		System.out.println(elecText.toString());
		
	}
	
	/**使用主键ID的数组或者字符串（1个值或者多个值），进行删除*/
	@Test
	public void deleteObjectByIDs(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
		
		//Serializable [] ids = {"4028817b511de2ee01511de2efb50000","4028817b511de6a201511de6a4940000"};
		Serializable ids = "4028817b511dc85e01511dc860310000";
		elecTextDao.deleteObjectByIDs(ids);
		
	}
	
	/**使用集合，进行删除*/
	@Test
	public void deleteObjectByCollection(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
		
		List<ElecText> list = new ArrayList<ElecText>();
		ElecText elecText1 = elecTextDao.findObjectByID("4028817b511dfef201511dfefcac0000");
		
		ElecText elecText2 = elecTextDao.findObjectByID("4028817b511e033f01511e0341b10000");
		list.add(elecText1);
		list.add(elecText2);
		elecTextDao.deleteObjectByCollection(list);
	}
	
	/**使用集合，进行新增/*/
	@Test
	public void saveCollection(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextDao elecTextDao = (IElecTextDao)ac.getBean(IElecTextDao.SERVICE_NAME);
		
		List<ElecText> list = new ArrayList<ElecText>();
		ElecText elecText = new ElecText();
		elecText.setTextName("田七");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("天小气");
		
		ElecText elecText1 = new ElecText();
		elecText1.setTextName("胡八");
		elecText1.setTextDate(new Date());
		elecText1.setTextRemark("胡小八");
		
		list.add(elecText);
		list.add(elecText1);
		
		elecTextDao.saveCollection(list);
	}
	
	/**使用查询条件，查询对应的集合列表*/
	/**
	 * select * from elec_text o where 1=1 --封装到DAO
		and o.textName like '%张%'  --封装到Service
		and o.textRemark like '%张%'  --封装到Service
		order by o.textDate asc,o.textName desc  --封装到Service
	 */
	@Test
	public void findCollectionByConditionNoPage(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextService elecTextService = (IElecTextService)ac.getBean(IElecTextService.SERVICE_NAME);
		//使用模型驱动
		ElecText elecText = new ElecText();
//		elecText.setTextName("张");
//		elecText.setTextRemark("张");
		
		List<ElecText> list = elecTextService.findCollectionByConditionNoPage(elecText);
		if(list!=null && list.size()>0){
			for(ElecText elecText2:list){
				System.out.println(elecText2.toString());
			}
		}
	}
}
