package cn.itcast.elec.util;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKUtils {

	//分词器
	private static Analyzer analyzer;
	
	static{
		try {
			/**词库分词*/
			analyzer = new IKAnalyzer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Analyzer getAnalyzer() {
		return analyzer;
	}
	
}
