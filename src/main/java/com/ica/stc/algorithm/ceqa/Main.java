package com.ica.stc.algorithm.ceqa;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

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
import com.ica.stc.ceqa.bo.Edges;
import com.ica.stc.ceqa.bo.EntityLink;
import com.ica.stc.ceqa.bo.Graph;
import com.ica.stc.ceqa.bo.Node;

public class Main {
	private static LTPPaser parse;
	private static final Logger LOG = Logger.getLogger(Main.class);
	private static final String U = "http://spu.ica.sth.sh.cn#";
	private static final Random random = new Random(System.currentTimeMillis());
	
	private SimpleLinkImpl link = new SimpleLinkImpl();

	public static String answerQuestion(String q) {
		return "test";
	}

	public Model model;

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
		System.out.println("model ok");
		// -------------------- 初始化SPARQL --------------------------
		try {
			InputStream in = new FileInputStream(new File("d:/data/rdf_phone"));
			model.read(in, null);
			in.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// -------------------- Link URL --------------------------
		// LOG.info(("获取链接关系中...."));
		// LOG.info(("获取链接关系结束,结果如下...."));
		// SimpleLinkImpl simpleLink = new SimpleLinkImpl();
		// String[] linked = simpleLink.getUrl(words, segs);
		// for (int i = 0; i < linked.length; i++) {
		// if (linked[i] != null) {
		// LOG.info(words[i] + "---->" + linked[i]);
		// }
		// }
		// LOG.info(resolvedQuestion(cat, linked, model));
	}

	public void initModel(String path) {
		Model model = ModelFactory.createDefaultModel();

		this.model = model;
		// -------------------- 初始化SPARQL --------------------------
		try {
			InputStream in = new FileInputStream(new File(path));
			model.read(in, null);
			in.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		link.initCacha(model);
		System.out.println("model init ok");
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
	/**
	 * 查询单个实体
	 * @param nl
	 * @return
	 */
	public Graph serchSingelEntity(String nl){
		EntityLink e =  link.getLink(nl);
		if (e!=null){
			return this.searchGraph(e.getUrl(), e.getType());
		}
		return null;
	}

	/**
	 * 根据URL获得graph对象
	 * 
	 * @param url
	 * @param type
	 * @return
	 */
	public Graph searchGraph(String url, String type) {
		String queryString = " SELECT ?x ?y ?z where { ";
		;
		if (type.equals("?x")) {
			queryString += " " + "<" + url + ">" + " ?y ?z}";
		} else if (type.equals("?y")) {
			queryString += " ?x " + "<" + url + ">" + "?z}";
		} else if (type.equals("?z")) {
			queryString += " ?x ?y " + "<" + url + "> }";
		}
		queryString += "LIMIT 100";
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Edges> links = new ArrayList<Edges>();

		if (type.equals("?x")) {
			int nodeidx = 0;
			int linkidx = 0;
			Node node = new Node(0, 25, url.split("#")[1], 1);
			nodes.add(node);
			while (results.hasNext()) {
				QuerySolution qs = results.nextSolution();
				String v = qs.getResource("?y").getURI().split("#")[1];
				String l = qs.getResource("?z").getURI().split("#")[1];
				nodeidx++;
				node = new Node(nodeidx, random.nextFloat() * 15 + 5, l, 2);
				nodes.add(node);
				Edges link = new Edges(linkidx, 0, nodeidx, v);
				linkidx++;
				links.add(link);
			}
			nodeidx++;
			node = new Node(nodeidx, 25, "手机", 0);
			nodes.add(node);
			Edges link = new Edges(linkidx, 0, nodeidx, "属于");
			links.add(link);

			Node[] ns = new Node[nodes.size()];
			Edges[] es = new Edges[links.size()];
			nodes.toArray(ns);
			links.toArray(es);
			Graph g = new Graph(ns, es);
			return g;
		}

		Graph gg = new Graph(null, null);
		return gg;

	}
}
