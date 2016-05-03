package cn.itcast.elec.web.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import cn.itcast.elec.domain.ElecText;
import cn.itcast.elec.util.TUtils;
import cn.itcast.elec.web.form.Pagenation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**通用的Action*/
public class BaseAction<T> extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<T> {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	//模型驱动的作用，将对象压入到栈顶
	T entity;
	
	//使用构造方法，获取子类传递的真实类型
	public BaseAction(){
		/**泛型转换，在父类中的泛型中，获取子类传递的真实类型*/
		Class entityClass = TUtils.getActualType(this.getClass());
		try {
			entity = (T) entityClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public T getModel() {
		return entity;
	}

	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
	}

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	/**jquery的easyUI中的4个参数*/
	Pagenation<T> pagenation = new Pagenation<>();
	
	//设置当前页
	public void setPage(int page){
		pagenation.setPage(page);
	}
	
	//设置当前页最多显示的记录数
	public void setRows(int rows){
		pagenation.setPageSize(rows);
	}
}
