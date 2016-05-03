package cn.itcast.elec.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	/**
	 * 数据保存的时候，需要截取字符串进行保存
	 * @param wholecontent:传递的文本字符串
	 * @param cutcount：需要分割的字符串的长度
	 * @return，分割后的List集合，存放结果集
	 */
	public static List<String> getContentByList(String wholecontent,int cutcount){
		List<String> list = new ArrayList<String>();
		//获取完整内容字符串的总长度
    	int contentlen = wholecontent.length(); 
        //内容截取，用内容总长和截取长度进行比较，无须截取的话直接插入
	    if (contentlen < cutcount){ 
	    	list.add(wholecontent);
	    }
	    //内容长度超过截取长度
	    else{
	    	//定义并初始化内容段落
	    	String contentpart ="";
	    	//定义并初始化被截取的段落数量
	    	int contentround =0;
            //开始截取的位置
	    	int begincount = 0; 
            //判断截取的段落数
	 	    int contentcutpart = contentlen/cutcount; 
		    int contentcutparts = contentlen%cutcount; //求余数
		    //若余数为0，说明被整除，内容的长度正好是截取长度的倍数。
		    if (contentcutparts==0){
		    	contentround = contentcutpart;
		    }
		    else{
		    	contentround = contentcutpart+1;
		    }
		    //循环截取内容
	    	for (int i = 1; i <= contentround; i++) {
	    		//如果不是最后一个截取部分
	            if (i != contentround){
	            	//按照截断长度截取内容
	            	contentpart = wholecontent.substring(begincount, cutcount*i);
	            }
	            else{
	            	//截取最后一部分内容
	            	contentpart = wholecontent.substring(begincount, contentlen);
	            }
	            //赋值下一截取部分的起点位置
	 		    begincount = cutcount*i; 
	 		    list.add(contentpart);
	    	}
	    }
	    return list;
	}
}
