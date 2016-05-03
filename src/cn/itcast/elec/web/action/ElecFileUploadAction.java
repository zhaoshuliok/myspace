package cn.itcast.elec.web.action;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.elec.domain.ElecFileUpload;
import cn.itcast.elec.domain.ElecSystemDDL;
import cn.itcast.elec.service.IElecFileUploadService;
import cn.itcast.elec.service.IElecSystemDDLService;
import cn.itcast.elec.util.FileUploadHelper;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

@Controller("elecFileUploadAction")
@Scope("prototype")
public class ElecFileUploadAction extends BaseAction<ElecFileUpload> {

	private ElecFileUpload elecFileUpload = this.getModel();

	/**资料图纸管理Service*/
	@Resource(name=IElecFileUploadService.SERVICE_NAME)
	private IElecFileUploadService elecFileUploadService;
	
	/**数据字典Service*/
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	/**资料图纸管理首页*/
	public String home(){
		//1：初始化所属单位和图纸类别的下拉菜单（从数据字典）
		this.initSystemDDL();
		return "home";
	}

	/**初始化所属单位和图纸类别的下拉菜单（从数据字典）*/
	private void initSystemDDL() {
		List<ElecSystemDDL> jctList = elecSystemDDLService.findSystemDDLListByKeyword("所属单位");
		List<ElecSystemDDL> picList = elecSystemDDLService.findSystemDDLListByKeyword("图纸类别");
		request.setAttribute("jctList", jctList);
		request.setAttribute("picList", picList);
	}
	
	/**跳转到新增页面*/
	public String add(){
		//1：初始化所属单位和图纸类别的下拉菜单（从数据字典）
		this.initSystemDDL();
		return "add";
	}
	
	/**提交表单*/
	public String save(){
		elecFileUploadService.saveFileUpload(elecFileUpload);
		//重定向
		return add();
	}
	
	/**使用所属单位和图纸类别查询对应的数据*/
	public String addList(){
		//组织所属单位和图纸类别作为查询条件，查询对应的集合，将集合返回页面上
		List<ElecFileUpload> list = elecFileUploadService.findFileUploadListByCondition(elecFileUpload);
		request.setAttribute("list", list);
		return "addList";
	}
	
	/**文件下载，使用struts2的方式*/
	public String download() throws Exception{
		//附件ID
		Integer fileID = elecFileUpload.getSeqId();
		//使用附件ID，查询附件
		ElecFileUpload fileUpload = elecFileUploadService.findFileByID(fileID);
		//获取路径path（相对路径）
		String path = fileUpload.getFileUrl();
		//查找文件的绝对路径
		FileUploadHelper fileUploadHelper = new FileUploadHelper();
		//路径：D:\\tomcat\\apache-tomcat-7.0.52\\webapps\\image_elec\\upload\\2015\\04\\21\\用户管理
		String basePath = fileUploadHelper.getPath()+path;
		//获取文件
		File baseFile = new File(basePath);
			
		/**设置头信息*/
		//设置下载的数据格式类型
		//获取文件名
		String fileName = fileUpload.getFileName();
		//中文
		fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
		//使用文件名获取类型
		String contentType = ServletActionContext.getServletContext().getMimeType(fileName);
		request.setAttribute("contentType", contentType);
		request.setAttribute("fileName", fileName);
			
		//输入流
		InputStream in = new FileInputStream(baseFile);
		//将文件的输入流放置到栈顶
		elecFileUpload.setInputStream(in);
		return "success";
	}
	
	/**获取页面传递的3个查询条件，组织条件先查询索引库，使用主键ID，查询数据库，最后获取List<ElecFileUpload>*/
	public String luceneHome(){
		List<ElecFileUpload> list = elecFileUploadService.findFileUploadListByConditionWithLuceneSearch(elecFileUpload);
		request.setAttribute("list", list);
		return home();
	}
	
	/**执行删除资料图纸管理*/
	public String delete(){
		//获取主键ID
		Integer seqId = elecFileUpload.getSeqId();
		//删除数据库，同时删除索引库，同时删除文件
		elecFileUploadService.deleteFileUploadById(seqId);
		return home();
	}
	
	/**导出PDF的格式*/
	public String exportPDF() throws Exception{
		//使用查询条件，查找资料图纸信息
		List<ElecFileUpload> list = elecFileUploadService.findFileUploadListByCondition(elecFileUpload);
		//使用itext生成pdf
		Table table = new Table(5); // 建立5列表格
		//设置表格属性
		table.setBorder(1);//设置边框大小
		table.setWidth(80);//设置表格宽度
		table.setBorderColor(new Color(0, 0, 255)); //将边框的颜色设置为蓝色
	    table.setPadding(5);//设置表格与字体间的间距
	    //table.setSpacing(5);//设置表格上下的间距
		//table.setAlignment(Element.ALIGN_CENTER);//设置字体显示居中样式
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); //水平对齐方式
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP); //垂直对齐方式

		//支持中文
		BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont);
		//添加列名
		table.addCell(new Phrase("所属单位", font)); //默认大小、样式、颜色
		table.addCell(new Phrase("图纸类别", font));
		table.addCell(new Phrase("文件名称", font));
		table.addCell(new Phrase("文件描述", font));
		table.addCell(new Phrase("上传时间", font));

		if(list!=null && list.size()>0){
			//添加数据
			for (ElecFileUpload elecFileUpload : list) {
				table.addCell(new Phrase(elecFileUpload.getProjId(), font)); //默认大小、样式、颜色
				table.addCell(new Phrase(elecFileUpload.getBelongTo(), font));
				table.addCell(new Phrase(elecFileUpload.getFileName(), font));
				table.addCell(new Phrase(elecFileUpload.getEcomment() + "", font));
				table.addCell(new Phrase(elecFileUpload.getProgressTime() + "", font));
			}
		}
		//一个PDF文档，其实就是一个Document对象
		Document document = new Document();

		//提供下载
		//设置头信息
		String filename = "电力公司资料图纸目录.pdf";
		filename = new String(filename.getBytes("gbk"),"iso-8859-1");
		response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setBufferSize(1024);
		//将pdf，写到输出流的对象中，使用itext的API
		PdfWriter.getInstance(document, response.getOutputStream());
		
		
		document.open();
		document.add(table);
		document.close();

		return NONE;
	}

}
