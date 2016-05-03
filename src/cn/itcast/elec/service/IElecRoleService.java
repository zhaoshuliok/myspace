package cn.itcast.elec.service;

import java.util.List;

import cn.itcast.elec.domain.ElecPopedom;
import cn.itcast.elec.domain.ElecRole;
import cn.itcast.elec.domain.ElecUser;

public interface IElecRoleService {

	public static final String SERVICE_NAME = "cn.itcast.elec.service.impl.ElecRoleServiceImpl";

	List<ElecRole> findRoleList();

	List<ElecPopedom> findPopedomList();

	List<ElecPopedom> findPopedomListByRoleID(String roleID);

	List<ElecUser> findUserListByRoleID(String roleID);

	void save(ElecRole elecRole);

	List<ElecRole> findRoleListByLogonName(String logonName);

	List<ElecPopedom> findPopedomListByRoleIds(List<ElecRole> roleList);

	List<ElecPopedom> findPopedomListByCondition(String logonName,
			String generatemenu);

}
