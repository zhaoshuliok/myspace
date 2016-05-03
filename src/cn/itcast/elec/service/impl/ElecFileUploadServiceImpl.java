package cn.itcast.elec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecFileUploadDao;
import cn.itcast.elec.domain.ElecFileUpload;
import cn.itcast.elec.service.IElecFileUploadService;
import cn.itcast.elec.util.DateUtils;
import cn.itcast.elec.util.FileUploadHelper;
import cn.itcast.elec.util.FileUploadUtils;

@Service(IElecFileUploadService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecFileUploadServiceImpl implements IElecFileUploadService {

	/**资料图纸管理Dao*/
	@Resource(name=IElecFileUploadDao.SERVICE_NAME)
	private IElecFileUploadDao elecFileUploadDao;

	/**保存资料图纸数据*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveFileUpload(ElecFileUpload elecFileUpload) {
		String progressTime = DateUtils.dateToStringTime(new Date());
		//获取上传的文件
		File [] uploads = elecFileUpload.getUploads();
		//获取上传的文件名
		String [] uploadsFileNames = elecFileUpload.getUploadsFileName();
		//获取上传的文件描述
		String [] comments = elecFileUpload.getComments();
		//组织PO对象，向资料图纸信息表中添加数据
		if(uploads!=null && uploads.length>0){
			for(int i=0;i<uploads.length;i++){
				ElecFileUpload fileUpload = new ElecFileUpload();
				fileUpload.setProjId(elecFileUpload.getProjId());//所属单位
				fileUpload.setBelongTo(elecFileUpload.getBelongTo());//图纸类别
				fileUpload.setFileName(uploadsFileNames[i]);//文件名
				//完成文件上传的同时，返回相对路径
				String fileUrl = FileUploadUtils.fileUploadReturnPath(uploads[i], uploadsFileNames[i], "资料图纸管理");
				fileUpload.setFileUrl(fileUrl);//文件路径
				fileUpload.setProgressTime(progressTime);//上传时间
				fileUpload.setEcomment(comments[i]);//文件描述
				elecFileUploadDao.save(fileUpload);
			}
		}
	}
	
	/**使用查询条件，查询对应的资料图纸管理的集合*/
	public List<ElecFileUpload> findFileUploadListByCondition(
			ElecFileUpload elecFileUpload) {
		//查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//所属单位
		String projId = elecFileUpload.getProjId();
		if(StringUtils.isNotBlank(projId)){
			condition += " and o.projId = ?";
			paramsList.add(projId);
		}
		//图纸类别
		String belongTo = elecFileUpload.getBelongTo();
		if(StringUtils.isNotBlank(belongTo)){
			condition += " and o.belongTo = ?";
			paramsList.add(belongTo);
		}
		Object[] params = paramsList.toArray();
		//排序
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.progressTime", "asc");
		List<ElecFileUpload> list = elecFileUploadDao.findCollectionByConditionNoPage(condition, params, orderby);
		return list;
	}
	
	/**使用主键ID，查询对象*/
	public ElecFileUpload findFileByID(Integer fileID) {
		return elecFileUploadDao.findObjectByID(fileID);
	}
	
	/**获取页面传递的3个查询条件，组织条件先查询索引库，使用主键ID，查询数据库，最后获取List<ElecFileUpload>*/
	public List<ElecFileUpload> findFileUploadListByConditionWithLuceneSearch(
			ElecFileUpload elecFileUpload) {
		return elecFileUploadDao.findFileUploadListByConditionWithLuceneSearch(elecFileUpload);
	}
	
	/**使用主键ID，完成资料图纸信息的删除*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteFileUploadById(Integer seqId) {
		//删除文件
		ElecFileUpload elecFileUpload = elecFileUploadDao.findObjectByID(seqId);
		//相对路径
		String path = elecFileUpload.getFileUrl();
		//绝对路径
		FileUploadHelper fileUploadHelper = new FileUploadHelper();
		String basePath = fileUploadHelper.getPath()+path;
		File file = new File(basePath);
		if(file.exists()){
			//删除文件
			file.delete();
		}
		//删除数据库，删除索引库
		elecFileUploadDao.deleteObjectByIDs(seqId);
	}
}
