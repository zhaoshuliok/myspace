package cn.itcast.elec.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.elec.dao.IElecCommonMsgContentDao;
import cn.itcast.elec.dao.IElecCommonMsgDao;
import cn.itcast.elec.domain.ElecCommonMsg;
import cn.itcast.elec.domain.ElecCommonMsgContent;
import cn.itcast.elec.service.IElecCommonMsgService;
import cn.itcast.elec.util.StringUtil;

@Service(IElecCommonMsgService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecCommonMsgServiceImpl implements IElecCommonMsgService {

	/**注入运行监控的DAO*/
	@Resource(name=IElecCommonMsgDao.SERVICE_NAME)
	private IElecCommonMsgDao elecCommonMsgDao;

	/**注入运行监控数据的DAO*/
	@Resource(name=IElecCommonMsgContentDao.SERVICE_NAME)
	private IElecCommonMsgContentDao elecCommonMsgContentDao;
	
//	/**查询数据库中的运行监控表的数据，在页面上进行显示（表单回显）*/
//	public ElecCommonMsg findCommonMsg() {
//		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
//		//只有一条数据
//		ElecCommonMsg commonMsg = null;
//		if(list!=null && list.size()>0){
//			commonMsg = list.get(0);
//		}
//		return commonMsg;
//	}
	
//	/**运行监控的保存*/
//	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
//	public void saveCommonMsg(ElecCommonMsg elecCommonMsg) {
//		//1：查询运行监控表，返回List，用来判断数据库中是否存在数据
//		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
//		/**
//		 * 2：
//		如果数据库中有数据，获取页面的传递的值，执行更新
//		如果数据库中没有数据，获取页面传递的值，执行新增
//		 */
//		//存在数据
//		if(list!=null && list.size()>0){
//			ElecCommonMsg commonMsg = list.get(0);//持久对象
//			commonMsg.setStationRun(elecCommonMsg.getStationRun());
//			commonMsg.setDevRun(elecCommonMsg.getDevRun());
//			commonMsg.setCreateDate(new Date());
//			//快照
//			/**不能使用代码：a different object with the same identifier value was already associated with the session: 
//			 * 一个Session的缓存不能出现2个相同的OID
//			 * */
////			elecCommonMsg.setComID(commonMsg.getComID());
////			elecCommonMsg.setCreateDate(new Date());
////			elecCommonMsgDao.update(elecCommonMsg);
//		}
//		//不存在数据
//		else{
//			elecCommonMsg.setCreateDate(new Date());
//			elecCommonMsgDao.save(elecCommonMsg);
//		}
//	}
	
	/***************使用数据表进行分表****************/
	/**查询数据库中的运行监控表的数据，在页面上进行显示（表单回显）*/
	public ElecCommonMsg findCommonMsg() {
		/**1：先查询运行监控表，获取运行监控的对象（唯一的值）*/
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		//唯一的值
		ElecCommonMsg commonMsg = null;
		if(list!=null && list.size()>0){
			commonMsg = list.get(0);
			//2：使用站点运行情况的类型作为查询条件，查询运行监控数据表，返回List<ElecCommonMsgContent>，遍历集合，组织一个站点运行情况的字符串，存放到运行监控的对象中
			String stationCondition = " and o.type=?";
			Object [] stationParams = {"1"};
			Map<String, String> stationOrderby = new LinkedHashMap<String, String>();
			stationOrderby.put("o.orderby", "asc");
			List<ElecCommonMsgContent> stationCommonList = elecCommonMsgContentDao.findCollectionByConditionNoPage(stationCondition, stationParams, stationOrderby);
			//组织字符串
			StringBuffer stationBuffer = new StringBuffer("");
			if(stationCommonList!=null && stationCommonList.size()>0){
				for(ElecCommonMsgContent commonMsgContent:stationCommonList){
					stationBuffer.append(commonMsgContent.getContent());
				}
			}
			//存放到ElecCommonMsg的对象中
			commonMsg.setStationRun(stationBuffer.toString());
			//3：使用设备运行情况的类型作为查询条件，查询运行监控数据表，返回List<ElecCommonMsgContent>，遍历集合，组织一个设备运行情况的字符串，存放到运行监控的对象中
			String devCondition = " and o.type=?";
			Object [] devParams = {"2"};
			Map<String, String> devOrderby = new LinkedHashMap<String, String>();
			stationOrderby.put("o.orderby", "asc");
			List<ElecCommonMsgContent> devCommonList = elecCommonMsgContentDao.findCollectionByConditionNoPage(devCondition, devParams, devOrderby);
			//组织字符串
			StringBuffer devBuffer = new StringBuffer("");
			if(devCommonList!=null && devCommonList.size()>0){
				for(ElecCommonMsgContent commonMsgContent:devCommonList){
					devBuffer.append(commonMsgContent.getContent());
				}
			}
			//存放到ElecCommonMsg的对象中
			commonMsg.setDevRun(devBuffer.toString());
		}
		return commonMsg;
	}
	
	
	/**运行监控的保存*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveCommonMsg(ElecCommonMsg elecCommonMsg) {
		/**1：查询站点运行情况和设备运行情况对应的数据表的内容，执行删除数据*/
		String condition = " and o.type in ('1','2') ";
		List<ElecCommonMsgContent> contentList = elecCommonMsgContentDao.findCollectionByConditionNoPage(condition, null, null);
		//执行删除
		elecCommonMsgContentDao.deleteObjectByCollection(contentList);
		/**2：获取从页面传递的站点运行情况和设备运行情况的值，采用截取字符串的方式，组织运行监控的数据表的PO对象，执行保存*/
		//获取站点运行情况
		String stationRun = elecCommonMsg.getStationRun();
		if(StringUtils.isNotBlank(stationRun)){
			//截取字符串
			List<String> commonList = StringUtil.getContentByList(stationRun, 50);//后续按2500截取
			//遍历循环，组织PO对象
			if(commonList!=null && commonList.size()>0){
				for(int i=0;i<commonList.size();i++){
					String common = commonList.get(i);
					ElecCommonMsgContent commonMsgContent = new ElecCommonMsgContent();
					commonMsgContent.setType("1");//1表示站点运行情况
					commonMsgContent.setContent(common);
					commonMsgContent.setOrderby(i+1);//从1开始
					elecCommonMsgContentDao.save(commonMsgContent);
				}
			}
		}
		
		//获取设备运行情况
		String devRun = elecCommonMsg.getDevRun();
		if(StringUtils.isNotBlank(devRun)){
			//截取字符串
			List<String> commonList = StringUtil.getContentByList(devRun, 50);//后续按2500截取
			//遍历循环，组织PO对象
			if(commonList!=null && commonList.size()>0){
				for(int i=0;i<commonList.size();i++){
					String common = commonList.get(i);
					ElecCommonMsgContent commonMsgContent = new ElecCommonMsgContent();
					commonMsgContent.setType("2");//2表示设备运行情况
					commonMsgContent.setContent(common);
					commonMsgContent.setOrderby(i+1);//从1开始
					elecCommonMsgContentDao.save(commonMsgContent);
				}
			}
		}
		/**3：组织运行监控表的PO对象数据，执行保存（没有数据）或者更新（存在数据）*/
		//判断数据库中是否存在数据
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		//如果存在数据，执行更新
		if(list!=null && list.size()>0){
			ElecCommonMsg commonMsg = list.get(0);//持久化对象
			commonMsg.setDevRun("2");
			commonMsg.setStationRun("1");
			commonMsg.setCreateDate(new Date());
		}
		//如果不存在数据，执行新增
		else{
			ElecCommonMsg commonMsg = new ElecCommonMsg();
			commonMsg.setStationRun("1");//1：站点运行情况
			commonMsg.setDevRun("2");//2：设备运行情况
			commonMsg.setCreateDate(new Date());
			elecCommonMsgDao.save(commonMsg);
		}
	}
}
