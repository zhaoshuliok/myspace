package cn.itcast.elec.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.elec.domain.ElecUser;

public class SystemFilter implements Filter {

	//用来定义放行的url
	List<String> list = new ArrayList<String>();
	public void init(FilterConfig config) throws ServletException {
		list.add("/index.jsp");
		list.add("/system/elecMenuAction_menuHome.do");
		
		list.add("/error.jsp");
		list.add("/system/elecMenuAction_logout.do");
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//是所有的url都存在Session吗？定义放行的url
		//获取访问的url地址（/index.jsp）
		String path = request.getServletPath();
		//需要放行
		if(list.contains(path)){
			chain.doFilter(request, response);
			return;
		}
		
		//从Session中获取globle_user的对象
		ElecUser elecUser = (ElecUser)request.getSession().getAttribute("globle_user");
		//存在Session，可以放行
		if(elecUser!=null){
			chain.doFilter(request, response);
			return;
		}
		//如果Session不存在，重定向到登录页面
		String redirectPath = request.getContextPath();//itcast1119elec
		//response.sendRedirect(redirectPath+"/index.jsp");
		//表示5秒后在跳转到登录页面
		response.sendRedirect(redirectPath+"/error.jsp");
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}



}
