package junit;

import java.io.FileOutputStream;

import org.junit.Test;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class TestPDF {

	@Test
	public void printPDF() throws Exception {
		Document document = new Document(); // 一个pdf文件
		PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
		document.open();
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		document.add(new Paragraph("hello,world!我是HelloWorld",new Font(bfChinese)));
		document.close();
	}

}
