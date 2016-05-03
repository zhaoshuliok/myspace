package cn.itcast.elec.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class FileUploadUtils {

	/**
	 * 完成文件上传的同时，返回路径path，相对路径
	 * @param file：上传的文件
	 * @param filename：上传的文件名
	 * @param model：上传的模块名称
	 * @return：上传文件的路径，相对路径
	 */
	/**
	 *    1：将上传的文件统一放置到upload的文件夹下
		  2：将每天上传的文件，使用日期格式的文件夹分开，将每个业务的模块放置统一文件夹下
		  3：上传的文件名要指定唯一，可以使用UUID的方式，也可以使用日期作为文件名
		  4：封装一个文件上传的方法，该方法可以支持多文件的上传，即支持各种格式文件的上传
		  5：保存路径path的时候，使用相对路径进行保存，这样便于项目的可移植性
		  6：将上传的文件放在到附件服务器上，不要上传到项目路径下，该服务器一定要通过XML格式文件配置，不要写到java代码中（dom4j）

	 */
	public static String fileUploadReturnPath(File file, String fileName,String model) {
		//获取图片附件服务器的地址（D:\tomcat\apache-tomcat-7.0.52\webapps\image_elec），放置到xml文件中
		FileUploadHelper fileUploadHelper = new FileUploadHelper();
		//获取服务器地址的路径：D:\tomcat\apache-tomcat-7.0.52\webapps\image_elec
		String path = fileUploadHelper.getPath();
		//将上传的文件放置到upload的文件夹
		String upload = "/upload";
		//将上传的文件使用日期格式的文件夹分开（格式：/yyyy/MM/dd/
		String datePath = DateUtils.dateToStringFile(new Date());
		//上传的文件名使用uuid
		//文件后缀
		String perfix = fileName.substring(fileName.lastIndexOf("."));
		String fileNameUUID = UUID.randomUUID().toString()+perfix; 
		
		//最终的文件路径是（D:\\tomcat\\apache-tomcat-7.0.52\\webapps\\image_elec\\upload\\2015\\04\\21\\用户管理\\uuid.doc）
		//如果文件夹路径不存在，需要创建
		String filePath = path+upload+datePath+model;
		File uploadFile = new File(filePath);
		if(!uploadFile.exists()){
			uploadFile.mkdirs();
		}
		//文件上传
		//目标文件
		File destFile = new File(filePath+"/"+fileNameUUID);
		file.renameTo(destFile);
		//相对路径
		return upload+datePath+model+"/"+fileNameUUID;
	}
	
	
	//文件上传
	public static void main(String[] args) throws IOException {
		File srcFile = new File("F:\\dir\\a.txt");
		File destFile = new File("F:\\dir\\dir2\\a.txt");
		//复制
		//FileUtils.copyFile(srcFile, destFile);
		//剪切
		boolean flag = srcFile.renameTo(destFile);
		System.out.println(flag);
	}

}
