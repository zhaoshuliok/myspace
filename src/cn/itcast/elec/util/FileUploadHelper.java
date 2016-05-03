package cn.itcast.elec.util;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FileUploadHelper {

	/***
	 * 使用Dom4j 解析XML格式文件
	 * @param f：传递文件
	 * @param rootStr：XML格式文件根路径
	 * @param filepath：XML格式文件子路径
	 * @return
	 */
	public String  xmlRead(File f,String rootStr,String filepath){
		try{
			SAXReader reader=new SAXReader();
			Document doc=reader.read(f);
			Element root=doc.getRootElement();
			Element foo;
		    String path=null;
		    //遍历根路径
			for(Iterator i = root.elementIterator(rootStr); i.hasNext();) {
				foo=(Element)i.next();
				//查找子路径对应的文件path
				path = foo.elementText(filepath);
			}
			return path;			
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  return null;
		  }		
			
   }
	
	/**
	 * 获取Function.xml文件中的服务器路径，从Xml格式文件获取
	 * @return，返回xml配置的附件服务器地址
	 */
	public String getPath(){
		//查找WebRoot下的Function.xml
		//String path = ServletActionContext.getServletContext().getRealPath("/Function.xml");
		//查找classpath下
		URL url = this.getClass().getClassLoader().getResource("Function.xml");
		String path =url.getPath();
		File f= new File(path);
		//返回Xml配置的附件服务器地址
		String basepath = this.xmlRead(f, "Function", "FunctionFilePath");
		return basepath;
	}
	
	/**获取服务器地址
	public String getServerPath(){
		//查找WebRoot下的Function.xml
		//String path = ServletActionContext.getServletContext().getRealPath("/Function.xml");
		//查找classpath下
		URL url = this.getClass().getClassLoader().getResource("Function.xml");
		String path =url.getPath();
		File f= new File(path);
		//返回Xml配置的附件服务器地址
		String basepath = this.xmlRead(f, "FunctionServer", "FunctionServerFilePath");
		return basepath;
	}*/
}
