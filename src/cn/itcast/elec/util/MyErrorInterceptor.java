package cn.itcast.elec.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class MyErrorInterceptor extends MethodFilterInterceptor {

	/**控制struts2(.do)可以访问的url*/
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			//放行到Action指定的方法，返回Action类的方法返回值
			String value = invocation.invoke();
			return value;
		} catch (Exception e) {
			request.setAttribute("info", "系统服务忙，请稍后再试！");
			request.setAttribute("message", e.getMessage().toString());
			e.printStackTrace();
			/**写到日志文件中*/
			Log log = LogFactory.getLog(invocation.getAction().getClass());
			log.error(e.getMessage().toString(),e);
			return "errorMsg";
		}
	}

	

}
