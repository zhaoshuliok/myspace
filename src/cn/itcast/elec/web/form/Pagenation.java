package cn.itcast.elec.web.form;

import java.util.List;

public class Pagenation<T> {

	// 请求参数
	private int page;//设置当前页
	private int pageSize; //设置当前最多显示的记录数 对应请求rows

	// 响应参数
	private long total;//总记录数
	private List<T> rows;//数据集合
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	

}
