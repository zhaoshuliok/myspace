package cn.itcast.elec.dao;

import java.util.List;

import cn.itcast.elec.domain.ElecFileUpload;

public interface IElecFileUploadDao extends ICommonDao<ElecFileUpload> {

	public static final String SERVICE_NAME = "cn.itcast.elec.dao.impl.ElecFileUploadDaoImpl";

	List<ElecFileUpload> findFileUploadListByConditionWithLuceneSearch(
			ElecFileUpload elecFileUpload);

}
