package cn.itcast.elec.domain;

import java.io.File;
import java.io.InputStream;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.wltea.analyzer.lucene.IKAnalyzer;



@Indexed
@Analyzer(impl=IKAnalyzer.class)
public class ElecFileUpload  implements java.io.Serializable {

	 @DocumentId
     private Integer seqId;         //主键ID
	 @Field(index=Index.UN_TOKENIZED)
     private String projId;			//工程ID/所属单位
	 @Field(index=Index.UN_TOKENIZED)
     private String belongTo;		//所属模块/图纸类别
	 @Field
     private String fileName;		//文件名
	 
     private String fileUrl;		//文件上传的路径
     private String progressTime;	//上传时间
     
     @Field
     private String ecomment;		//文件描述

    public Integer getSeqId() {
        return this.seqId;
    }
    
    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public String getProjId() {
        return this.projId;
    }
    
    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getBelongTo() {
        return this.belongTo;
    }
    
    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getProgressTime() {
        return this.progressTime;
    }
    
    public void setProgressTime(String progressTime) {
        this.progressTime = progressTime;
    }

    
    public String getEcomment() {
		return ecomment;
	}

	public void setEcomment(String ecomment) {
		this.ecomment = ecomment;
	}




	/***********非持久化的javabean属性*************/
	/**按文件名称和描述搜素*/
    private String queryString;
    
    //上传的文件
    private File [] uploads;
    
    //上传的文件名称
    private String [] uploadsFileName;
    
    //上传的文件描述
    private String [] comments;
    //文件下载
    private InputStream inputStream;
    

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File[] getUploads() {
		return uploads;
	}

	public void setUploads(File[] uploads) {
		this.uploads = uploads;
	}

	public String[] getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(String[] uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public String[] getComments() {
		return comments;
	}

	public void setComments(String[] comments) {
		this.comments = comments;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String toString() {
		return "ElecFileUpload [seqId=" + seqId + ", projId=" + projId
				+ ", belongTo=" + belongTo + ", fileName=" + fileName
				+ ", fileUrl=" + fileUrl + ", progressTime=" + progressTime
				+ ", ecomment=" + ecomment + "]";
	}
	
    
	
	
    
}