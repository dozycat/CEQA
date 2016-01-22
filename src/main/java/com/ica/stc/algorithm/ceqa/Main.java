package com.ica.stc.algorithm.ceqa;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.log4j.Logger;

import com.ica.stc.algorithm.linking.impl.SimpleLinkImpl;
import com.ica.stc.algorithm.parse.impl.LTPPaser;
import com.ica.stc.algorithm.qtype.impl.SimpleQtype;

public class Main {
	private static LTPPaser parse;
	private static final Logger LOG = Logger.getLogger(Main.class);
	private static final String U = "http://spu.ica.sth.sh.cn#";

	public String answerQuestion(String q) {
		return null;
	}

	public static void main(String[] args) {

		String segPath = args[0];
		String DBUrl = args[1];
		String question = args[2];

		LOG.info("成功加载DB");
		// -------------------- 词性标注 --------------------------
		parse = new LTPPaser();

		SimpleQtype sqt = new SimpleQtype();
		// -------------------- 问题分词 ----------------------------
		// String[] words = sys.getWordSeg().getWords(question); TODO;

		String[] words = parse.getWords(question);
		String[] segs = parse.ParseSetence(question);

		StringBuilder message = new StringBuilder();
		for (String word : words) {
			message.append(word + "\t");
		}

		LOG.info(message);
		// -------------------- 计算问题类别 --------------------------
		int cat = sqt.getQuestionType(words);
		LOG.info("问题类别" + String.valueOf(cat));
		Model model = ModelFactory.createDefaultModel();

		// -------------------- 初始化SPARQL --------------------------
		try {
			InputStream in = new FileInputStream(new File("/Users/duzeyu/Documents/workspace/ZhishiQ/res/rdf_phone"));
			model.read(in, null);
			in.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// -------------------- Link URL --------------------------
		LOG.info(("获取链接关系中...."));
		LOG.info(("获取链接关系结束,结果如下...."));
		SimpleLinkImpl simpleLink = new SimpleLinkImpl();
		String[] linked = simpleLink.getUrl(words, segs);
		for (int i = 0; i < linked.length; i++) {
			if (linked[i] != null) {
				LOG.info(words[i] + "---->" + linked[i]);
			}
		}
		LOG.info(resolvedQuestion(cat, linked, model));
	}

	public static String resolvedQuestion(int qt, String[] linkedQ, Model model) {
		if (qt == SimpleQtype.TYPE_BOOL) {
			String queryString = " SELECT ?x ?y ?z where { ";
			String[] target = new String[2];
			int idx = 0;
			for (int i = 0; i < linkedQ.length; i++) {
				if (linkedQ[i] != null) {
					if (idx >= 2) {
						return "Dont Know";
					}
					target[idx] = linkedQ[i];
					idx++;
				}
			}
			queryString += " " + "<" + target[0] + ">" + " ?y " + "<" + target[1] + "> }";
			// queryString += " <http://spu.ica.sth.sh.cn#KONKA/康佳JL78> ?y ?z
			// }";

			LOG.info(queryString);
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			while (results.hasNext()) {
				QuerySolution qs = results.nextSolution();

				return "Yes." + target[0] + qs.toString() + target[1];
			}
			queryString = " SELECT ?x ?y ?z where { ";
			queryString += " " + "<" + target[0] + "> " + "<" + target[1] + "> ?y }";
			LOG.info(queryString);
			query = QueryFactory.create(queryString);
			qe = QueryExecutionFactory.create(query, model);
			results = qe.execSelect();
			while (results.hasNext()) {
				QuerySolution qs = results.nextSolution();
				return "Yes." + target[0] + qs.toString() + target[1];
			}

			return "No.";
		}
		if (qt == SimpleQtype.TYPE_FACT) {
			String queryString = " SELECT ?x ?y ?z where { ";
			String[] target = new String[2];
			int idx = 0;
			for (int i = 0; i < linkedQ.length; i++) {
				if (linkedQ[i] != null) {
					if (idx >= 2) {
						return "Dont Know";
					}
					target[idx] = linkedQ[i];
					idx++;
				}
			}
			queryString += " " + "<" + target[0] + ">" + " ?y " + "<" + target[1] + "> }";

			LOG.info(queryString);
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			while (results.hasNext()) {
				QuerySolution qs = results.nextSolution();
				return qs.toString();
			}
			queryString = " SELECT ?x ?y ?z where { ";
			queryString += " " + "<" + target[0] + ">" + " <" + target[1] + "> ?y }";
			LOG.info(queryString);
			query = QueryFactory.create(queryString);
			qe = QueryExecutionFactory.create(query, model);
			results = qe.execSelect();
			while (results.hasNext()) {
				QuerySolution qs = results.nextSolution();
				return qs.toString();
			}

			return "dont know.";
		}
		return null;
	}
}
