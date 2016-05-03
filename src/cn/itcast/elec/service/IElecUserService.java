package cn.itcast.elec.service;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.elec.domain.ElecUser;
import cn.itcast.elec.domain.ElecUserFile;

public interface IElecUserService {

	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecUserServiceImpl";

	List<ElecUser> findUserListByCondition(ElecUser elecUser);

	String checkUserByLogonName(String logonName);

	void saveUser(ElecUser elecUser);

	ElecUser findUserById(String userID);

	ElecUserFile findUserFileByID(String fileID);

	void deleteUserByUserID(String[] userIDs);

	ElecUser findUserByLogonName(String name);

	List<ElecUser> findUserListByConditionWithSql(ElecUser elecUser);

	List<ElecUser> findUserListByPostID(String postID);

	ArrayList<String> findExcelFieldName();

	ArrayList<ArrayList<String>> findExcelFieldData(ElecUser elecUser);

	void saveUserList(List<ElecUser> userList);

	List<Object[]> findChartDataSetByUser(String zName, String eName);

}
