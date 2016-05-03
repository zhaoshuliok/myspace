package cn.itcast.elec.util;

import org.apache.struts2.ServletActionContext;

import cn.itcast.elec.domain.ElecUser;

public class ValueUtils {

	/**放置到栈顶*/
	public static void push(Object o) {
		ServletActionContext.getContext().getValueStack().push(o);
	}

	/**获取Session*/
	public static ElecUser getSession() {
		ElecUser elecUser = (ElecUser)ServletActionContext.getRequest().getSession().getAttribute("globle_user");
		return elecUser;
	}

	
}
