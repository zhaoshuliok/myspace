package cn.itcast.elec.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.itcast.elec.util.PageInfo;

public interface ICommonDao<T> {
	void save(T entity);
	void update(T entity);
	T findObjectByID(Serializable id);
	void deleteObjectByIDs(Serializable ... ids);
	void deleteObjectByCollection(List<T> list);
	void saveCollection(List<T> list);
	List<T> findCollectionByConditionNoPage(String condition,
			Object[] params, Map<String, String> orderby);
	List<T> findCollectionByConditionWithPage(String condition,
			Object[] params, Map<String, String> orderby, PageInfo pageInfo);

}
