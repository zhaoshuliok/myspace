package cn.itcast.elec.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ListUtils {

	/**
	 * 将传递的字符串，中间使用#号分隔，转换成List<String>
	 * @param expNameList
	 * @return
	 */
	public static List<String> stringToList(String name) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(name)){
			String [] arrays = name.split("#");
			if(arrays!=null && arrays.length>0){
				for(String s:arrays){
					list.add(s);
				}
			}
		}
		return list;
	}

}
