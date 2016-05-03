package cn.itcast.elec.webservice;

import java.util.List;

import javax.jws.WebService;

import cn.itcast.elec.domain.ElecSystemDDL;

@WebService
public interface IWebSystemDDLService {

	/**远程数据调用的方法，发布总部的数据字典*/
	/**
	 * 发布服务的方法
	 *   * 参数String：传递的数据类型
	 *   * 返回值List<ElecSystemDDL>：根据传递的数据类型，查询该数据类型对应的结果，传递List集合，其中存放数据字典的对象
	 * */
	public List<ElecSystemDDL> findSystemByKeyword(String keyword);

}
