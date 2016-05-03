package cn.itcast.elec.domain;


@SuppressWarnings("serial")
public class ElecSystemDDL implements java.io.Serializable {
	
	private Integer seqID;		//主键ID(自增长)
	private String keyword;		//数据类型
	private Integer ddlCode;	//数据项的code
	private String ddlName;		//数据项的value
	
	public ElecSystemDDL(){
		
	}
	
	//构造方法存放数据类型
	public ElecSystemDDL(String keyword){
		this.keyword = keyword;
	}
	
	public Integer getSeqID() {
		return seqID;
	}
	public void setSeqID(Integer seqID) {
		this.seqID = seqID;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getDdlCode() {
		return ddlCode;
	}
	public void setDdlCode(Integer ddlCode) {
		this.ddlCode = ddlCode;
	}
	public String getDdlName() {
		return ddlName;
	}
	public void setDdlName(String ddlName) {
		this.ddlName = ddlName;
	}
	
	/*************非持久化javabean属性（VO对象）************************/
	//数据项的值
	private String [] itemname;
	//存放数据类型
	private String keywordname;
	
	/**
	 * 业务的标识字段
	 *  new：新增一种新的数据类型
	 *  add：在原数据类型的基础上进行编辑和修改
	 */
	private String typeflag;

	public String[] getItemname() {
		return itemname;
	}

	public void setItemname(String[] itemname) {
		this.itemname = itemname;
	}

	public String getKeywordname() {
		return keywordname;
	}

	public void setKeywordname(String keywordname) {
		this.keywordname = keywordname;
	}

	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}
	
	
	
}
