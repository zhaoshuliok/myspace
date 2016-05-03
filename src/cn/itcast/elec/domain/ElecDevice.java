package cn.itcast.elec.domain;

import java.util.Date;




@SuppressWarnings("serial")
public class ElecDevice  implements java.io.Serializable {

     private String devId;
     private String jctId;
     private String devName;
     private String devType;
     private String trademark;
     private String specType;
     private String produceHome;
     private String produceArea;
     private String useness;
     private String quality;
     private String useUnit;
     private Double devExpense;
     private String adjustPeriod;
     private String overhaulPeriod;
     private String configure;
     private String devState;
     private String runDescribe;
     private String ecomment;
     private Date useDate;
     private String isDelete;
     private String createEmpId;
     private Date createDate;
     private String lastEmpId;
     private Date lastDate;
     private String qunit;
     private String apunit;
     private String opunit;
     private String apstate;
     private String opstate;
     
     private ElecDevicePlan elecDevicePlan;
     
    public String getDevId() {
        return this.devId;
    }
    
    public void setDevId(String devId) {
        this.devId = devId;
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

    public String getDevState() {
        return this.devState;
    }
    
    public void setDevState(String devState) {
        this.devState = devState;
    }

    public String getRunDescribe() {
        return this.runDescribe;
    }
    
    public void setRunDescribe(String runDescribe) {
        this.runDescribe = runDescribe;
    }

    

    public String getEcomment() {
		return ecomment;
	}

	public void setEcomment(String ecomment) {
		this.ecomment = ecomment;
	}

	public Date getUseDate() {
        return this.useDate;
    }
    
    public void setUseDate(Date useDate) {
        this.useDate = useDate;
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

	public String getApstate() {
		return apstate;
	}

	public void setApstate(String apstate) {
		this.apstate = apstate;
	}

	public String getOpstate() {
		return opstate;
	}

	public void setOpstate(String opstate) {
		this.opstate = opstate;
	}

	public ElecDevicePlan getElecDevicePlan() {
		return elecDevicePlan;
	}

	public void setElecDevicePlan(ElecDevicePlan elecDevicePlan) {
		this.elecDevicePlan = elecDevicePlan;
	}

	




}