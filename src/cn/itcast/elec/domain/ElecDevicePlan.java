package cn.itcast.elec.domain;

import java.math.BigDecimal;
import java.util.Date;




public class ElecDevicePlan  implements java.io.Serializable {


    // Fields    

	
     private String devPlanId;	//购置计划
     private String jctId;		//所属单位（数据字典）
     private String devName;	//计划购置设备名称
     private String devType;	//设备类型（数据字典）
     private String trademark;	//品牌
     private String specType;	//规格型号
     private String produceHome;//厂家
     private String produceArea;//产地
     private String useness;	//用途
     private String quality;	//数量
     private String useUnit;	//使用单位
     private Double devExpense;	//金额
     private Date planDate;		//计划时间
     private String adjustPeriod;	//校准周期
     private String overhaulPeriod;	//检修周期
     private String configure;		//配置
     private String ecomment;		//备注
     private String purchaseState="0";	//购买状态（0表示计划购买，1表示购买）
     private String isDelete="0";		//是否删除
     private String createEmpId;	//创建人
     private Date createDate;		//创建时间
     private String lastEmpId;		//修改人
     private Date lastDate;			//修改时间
     private String qunit;			//数量单位
     private String apunit;			//校准周期单位
     private String opunit;			//检修周期单位
     
     private String state;	//审核状态（用于流程审核）0表示未审核，1表示审核中，2表示审核通过，3表示审核不通过
     
     private String processInstanceID;//流程实例ID
     
     
     
     public String getProcessInstanceID() {
		return processInstanceID;
	}

	public void setProcessInstanceID(String processInstanceID) {
		this.processInstanceID = processInstanceID;
	}



	//正在使用的设备
    private ElecDevice elecDevice;
     
    public ElecDevice getElecDevice() {
 		return elecDevice;
 	}

 	public void setElecDevice(ElecDevice elecDevice) {
 		this.elecDevice = elecDevice;
 	}
     
     private String planDateString;
     
     
     
    // Constructors

    /** default constructor */
    public ElecDevicePlan() {
    }

	/** minimal constructor */
    public ElecDevicePlan(String jctId, String devName, String devType, String purchaseState, String isDelete) {
        this.jctId = jctId;
        this.devName = devName;
        this.devType = devType;
        this.purchaseState = purchaseState;
        this.isDelete = isDelete;
    }
    
    /** full constructor */
    public ElecDevicePlan(String jctId, String devName, String devType, String trademark, String specType, String produceHome, String produceArea, String useness, String quality, String useUnit, Double devExpense, Date planDate, String adjustPeriod, String overhaulPeriod, String configure, String ecomment, String purchaseState, String isDelete, String createEmpId, Date createDate, String lastEmpId, Date lastDate) {
        this.jctId = jctId;
        this.devName = devName;
        this.devType = devType;
        this.trademark = trademark;
        this.specType = specType;
        this.produceHome = produceHome;
        this.produceArea = produceArea;
        this.useness = useness;
        this.quality = quality;
        this.useUnit = useUnit;
        this.devExpense = devExpense;
        this.planDate = planDate;
        this.adjustPeriod = adjustPeriod;
        this.overhaulPeriod = overhaulPeriod;
        this.configure = configure;
        this.ecomment = ecomment;
        this.purchaseState = purchaseState;
        this.isDelete = isDelete;
        this.createEmpId = createEmpId;
        this.createDate = createDate;
        this.lastEmpId = lastEmpId;
        this.lastDate = lastDate;
    }

   
    
    // Property accessors

    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDevPlanId() {
        return this.devPlanId;
    }
    
    public void setDevPlanId(String devPlanId) {
        this.devPlanId = devPlanId;
    }

    public String getJctId() {
        return this.jctId;
    }
    
    public void setJctId(String jctId) {
        this.jctId = jctId;
    }

    public String getDevName() {
        return this.devName;
    }
    
    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevType() {
        return this.devType;
    }
    
    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getTrademark() {
        return this.trademark;
    }
    
    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getSpecType() {
        return this.specType;
    }
    
    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public String getProduceHome() {
        return this.produceHome;
    }
    
    public void setProduceHome(String produceHome) {
        this.produceHome = produceHome;
    }

    public String getProduceArea() {
        return this.produceArea;
    }
    
    public void setProduceArea(String produceArea) {
        this.produceArea = produceArea;
    }

    public String getUseness() {
        return this.useness;
    }
    
    public void setUseness(String useness) {
        this.useness = useness;
    }

    public String getQuality() {
        return this.quality;
    }
    
    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getUseUnit() {
        return this.useUnit;
    }
    
    public void setUseUnit(String useUnit) {
        this.useUnit = useUnit;
    }

    public Double getDevExpense() {
        return this.devExpense;
    }
    
    public void setDevExpense(Double devExpense) {
        this.devExpense = devExpense;
    }

    public Date getPlanDate() {
        return this.planDate;
    }
    
   
   
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getAdjustPeriod() {
        return this.adjustPeriod;
    }
    
    public void setAdjustPeriod(String adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    public String getOverhaulPeriod() {
        return this.overhaulPeriod;
    }
    
    public void setOverhaulPeriod(String overhaulPeriod) {
        this.overhaulPeriod = overhaulPeriod;
    }

    public String getConfigure() {
        return this.configure;
    }
    
    public void setConfigure(String configure) {
        this.configure = configure;
    }

    

    public String getEcomment() {
		return ecomment;
	}

	public void setEcomment(String ecomment) {
		this.ecomment = ecomment;
	}

	public String getPurchaseState() {
        return this.purchaseState;
    }
    
    public void setPurchaseState(String purchaseState) {
        this.purchaseState = purchaseState;
    }

    public String getIsDelete() {
        return this.isDelete;
    }
    
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateEmpId() {
        return this.createEmpId;
    }
    
    public void setCreateEmpId(String createEmpId) {
        this.createEmpId = createEmpId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastEmpId() {
        return this.lastEmpId;
    }
    
    public void setLastEmpId(String lastEmpId) {
        this.lastEmpId = lastEmpId;
    }

    public Date getLastDate() {
        return this.lastDate;
    }
    
    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

	public String getApunit() {
		return apunit;
	}

	public void setApunit(String apunit) {
		this.apunit = apunit;
	}

	public String getOpunit() {
		return opunit;
	}

	public void setOpunit(String opunit) {
		this.opunit = opunit;
	}

	public String getQunit() {
		return qunit;
	}

	public void setQunit(String qunit) {
		this.qunit = qunit;
	}

	public String getPlanDateString() {
		return planDateString;
	}

	public void setPlanDateString(String planDateString) {
		this.planDateString = planDateString;
	}
   


	/*************非持久化javabean属性*********************/
	//所属单位中文
	private String jctIdName;
	//设备类型中文
	private String devTypeName;

	//购置时间开始查询
	private Date planDateBegin;
	//购置时间结束查询
	private Date planDateEnd;
	
	//职位
	private String postID;
	
	
	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public Date getPlanDateBegin() {
		return planDateBegin;
	}

	public void setPlanDateBegin(Date planDateBegin) {
		this.planDateBegin = planDateBegin;
	}

	public Date getPlanDateEnd() {
		return planDateEnd;
	}

	public void setPlanDateEnd(Date planDateEnd) {
		this.planDateEnd = planDateEnd;
	}

	public String getJctIdName() {
		return jctIdName;
	}

	public void setJctIdName(String jctIdName) {
		this.jctIdName = jctIdName;
	}

	public String getDevTypeName() {
		return devTypeName;
	}

	public void setDevTypeName(String devTypeName) {
		this.devTypeName = devTypeName;
	}

	
	
	





}