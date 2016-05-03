package cn.itcast.elec.domain;

import java.util.ArrayList;
import java.util.List;



@SuppressWarnings("serial")
public class ElecPopedom implements java.io.Serializable {
	
	
	private String id;			//权限Code（主键ID）
	private String pid;			//父级权限code，如果已经是根节点则为0
	private String name;		//权限名称
	private String page;			//权限在系统中执行访问地址的URL
	private String generatemenu;		//左侧菜单名称
	private String description;		//功能描述
	private int orderby;	//排序（如果是父为0，子从1开始排序）
	private String isMenu;		//是否是系统菜单结构
	
	public ElecPopedom(){
		
	}
	
	public ElecPopedom(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getGeneratemenu() {
		return generatemenu;
	}
	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOrderby() {
		return orderby;
	}
	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}
	
	
	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}


	/*******************非持久化javabean属性*****************************/
	private List<ElecPopedom> childList = new ArrayList<ElecPopedom>();

	/**
     * 因为返回页面的集合是List<ElecPopedom>，所以在ElecPopedom对象中添加一个flag属性
       flag=1：页面的复选框被选中
       flag=2：页面的复选框不被选中
	 */
	private String flag;
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<ElecPopedom> getChildList() {
		return childList;
	}

	public void setChildList(List<ElecPopedom> childList) {
		this.childList = childList;
	}
	
	
	
	
	
}
