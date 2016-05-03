package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecFileUpload;

public interface IElecFileUploadService {

	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecFileUploadServiceImpl";

	void saveFileUpload(ElecFileUpload elecFileUpload);

	List<ElecFileUpload> findFileUploadListByCondition(
			ElecFileUpload elecFileUpload);

	ElecFileUpload findFileByID(Integer fileID);

	List<ElecFileUpload> findFileUploadListByConditionWithLuceneSearch(
			ElecFileUpload elecFileUpload);

	void deleteFileUploadById(Integer seqId);

}
