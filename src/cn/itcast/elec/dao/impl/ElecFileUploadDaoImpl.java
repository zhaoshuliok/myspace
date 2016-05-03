package cn.itcast.elec.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.stereotype.Repository;

import cn.itcast.elec.dao.IElecFileUploadDao;
import cn.itcast.elec.domain.ElecFileUpload;
import cn.itcast.elec.util.IKUtils;

@Repository(IElecFileUploadDao.SERVICE_NAME)
public class ElecFileUploadDaoImpl extends CommonDaoImpl<ElecFileUpload> implements IElecFileUploadDao {

	/**获取页面传递的3个查询条件，组织条件先查询索引库，使用主键ID，查询数据库，最后获取List<ElecFileUpload>*/
	public List<ElecFileUpload> findFileUploadListByConditionWithLuceneSearch(
			ElecFileUpload fileUpload) {
		List<ElecFileUpload> list = new ArrayList<ElecFileUpload>();
		try {
			//所属单位： 	
			String projId = fileUpload.getProjId();
			//图纸类别： 	
			String belongTo = fileUpload.getBelongTo();
			//按文件名称和描述搜素： 		
			String queryString = fileUpload.getQueryString();
			
			/**封装查询条件*/
			//用来连接多个查询条件
			BooleanQuery query = new BooleanQuery();
			//所属单位
			if(StringUtils.isNotBlank(projId)){
				/**
				 * 如果查询的一个字段的完成的词，词条Term查询
				 * 参数一：搜索条件要在索引库的哪个字段添加搜索
				 * 参数二：连接的语法
				 *   * Occur.MUST：必须满足，相当于sql语句的and
				 *   * Occur.SHOULD：应该满足，相当于sql语句的or
				 *   * Occur.MUST_NOT：相当于sql语句的not
				 * */
				TermQuery query1 = new TermQuery(new Term("projId",projId));
				query.add(query1, Occur.MUST);
			}
			//图纸类别
			if(StringUtils.isNotBlank(belongTo)){
				TermQuery query2 = new TermQuery(new Term("belongTo",belongTo));
				query.add(query2, Occur.MUST);
			}
			//按文件名称和描述搜素： 		
			if(StringUtils.isNotBlank(queryString)){
				//首先要进行分词进行搜索，而且要在多个字段上完成搜索
				//只能将查询条件在一个字段上进行搜索
				//QueryParser queryParser = new QueryParser(Version.LUCENE_36,"fileName",IKUtils.getAnalyzer());
				//可以将查询条件，在多个字段上进行搜索
				QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_36, new String[]{"fileName","ecomment"}, IKUtils.getAnalyzer());
				Query query3 = queryParser.parse(queryString);
				query.add(query3, Occur.MUST);
			}
			
			/**设置文字高亮begin*/
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'><b>","</b></font>");//使用html标签进行高亮，默认<b></b>
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter,scorer);
			//设置一段摘要（表示摘要的大小20）
			int fragmentSize = 50;
			Fragmenter fragmenter = new SimpleFragmenter(fragmentSize);
			highlighter.setTextFragmenter(fragmenter);
			/**设置文字高亮end*/	
			Session s = this.getHibernateTemplate().getSessionFactory().openSession();
			FullTextSession fullTextSession = Search.getFullTextSession(s);
			/**
			 * 根据查询条件query，查询对应的集合，将集合封装到了ElecFileUpload.class
			 */
			FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, ElecFileUpload.class);
			list = fullTextQuery.list();
			
			if(list!=null && list.size()>0){
				for(ElecFileUpload elecFileUpload:list){
					/**获取文字高亮begin*/
					/**
					 * highlighter.getBestFragment()
					 * * 参数一：分词器
					 * * 参数二：需要在哪个字段上进行高亮，需要指定一个字段（只能指定一个字段）
					 * * 参数三：需要高亮的文本
					 * * 返回值：String，表示高亮后的结果
					 *    * 如果存在高亮后的结果，就返回高亮后的结果
					 *    * 如果不存高亮后的结果，返回null
					 */
					String fileName = highlighter.getBestFragment(IKUtils.getAnalyzer(), "fileName", elecFileUpload.getFileName());
					if(StringUtils.isBlank(fileName)){
						fileName = elecFileUpload.getFileName();
						//没有高亮的结果，产生的摘要从第一个位置开始截取
						if(fileName!=null && fileName.length()>fragmentSize){
							fileName = fileName.substring(0,fragmentSize);
						}
					}
					elecFileUpload.setFileName(fileName);
					String ecomment = highlighter.getBestFragment(IKUtils.getAnalyzer(), "ecomment", elecFileUpload.getEcomment());
					if(StringUtils.isBlank(ecomment)){
						ecomment = elecFileUpload.getEcomment();
						//没有高亮的结果，产生的摘要从第一个位置开始截取
						if(ecomment!=null && ecomment.length()>fragmentSize){
							ecomment = ecomment.substring(0,fragmentSize);
						}
					}
					elecFileUpload.setEcomment(ecomment);
					/**获取文字高亮end*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
}
