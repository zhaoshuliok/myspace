package cn.itcast.elec.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;

/**
 * @Repository("elecTextDao")
 * 相当于在spring容器中定义
 *<bean id="elecTextDao" class="cn.itcast.elec.dao.impl.ElecTextDaoImpl"></bean>
 *
 * 使用注解的优势：在于spring的配置文件中的内容相对少了很多，问题，如何保证bean id="elecTextDao"的属性名称要唯一。
 * @Repository(IElecTextDao.SERVICE_NAME)
 * 相当于在spring容器中定义：
 * <bean id="cn.itcast.elec.dao.impl.ElecTextDaoImpl" class="cn.itcast.elec.dao.impl.ElecTextDaoImpl"></bean>
 */
@Repository(IElecTextDao.SERVICE_NAME)
public class ElecTextDaoImpl extends CommonDaoImpl<ElecText> implements IElecTextDao {

	
	
}
