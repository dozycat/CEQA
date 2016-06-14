package com.ica.stc.ceqa.service.control;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ica.stc.ceqa.bo.Edges;
import com.ica.stc.ceqa.bo.Graph;
import com.ica.stc.ceqa.bo.Node;
import com.ica.stc.ceqa.bo.Question;
import com.ica.stc.ceqa.service.CeqaService;

/*
 * ceqa的control,调用service层的服务。
 */

@Controller
public class CeqaControl {

	@Autowired
	private CeqaService ceqaService;

	@ResponseBody
	@RequestMapping(value = "/hello.io", method = RequestMethod.GET)
	public Object test() {
		System.out.println("Work Well Page!Hello CEQA!");
		return (ceqaService.getGraph("http://spu.ica.sth.sh.cn#UTStarcom/UT斯达康UT620", "?x"));
	}
	
	@ResponseBody
	@RequestMapping(value = "/question.io", method = RequestMethod.GET)
	public Object question(Question q){
		try {
			String str = new String(q.getQuestion().getBytes("iso8859-1"), "UTF-8");
			q.setQuestion(str);
		} catch (UnsupportedEncodingException e) {
			q.setQuestion(null);
		}
		return ceqaService.getResult(q.getQuestion());
	}
	
	@ResponseBody
	@RequestMapping(value = "/qa.io", method = RequestMethod.GET)
	public Graph qa(Question q) {
		/**
		 * 编码遗留问题。
		 */
		try {
			String str = new String(q.getQuestion().getBytes("iso8859-1"), "UTF-8");
			
			q.setQuestion(str);
		} catch (UnsupportedEncodingException e) {
			q.setQuestion(null);
		}
		System.out.println(q.getQuestion());
		// 问题为空时加载default内容
		if (q.getQuestion() != null) {
			if (q.getQuestion().equals("first")) {

				Node node = new Node(0, 50, "康佳JL78", 1);
				Node node2 = new Node(1, 25, "手机", 0);
				Node node3 = new Node(2, 22, "2.6英寸", 2);
				Node node4 = new Node(3, 22, "2008年", 2);
				Node node5 = new Node(4, 22, "GSM", 2);
				Node node6 = new Node(5, 22, "直板", 2);

				Node[] nodes = new Node[] { node, node2, node3, node4, node5, node6 };

				Edges edge = new Edges(0, 0, 1, "属于");
				Edges edge2 = new Edges(0, 0, 2, "尺寸");
				Edges edge3 = new Edges(0, 0, 3, "出厂年份");
				Edges edge4 = new Edges(0, 0, 4, "网络类型");
				Edges edge5 = new Edges(0, 0, 5, "版型");

				Edges[] edges = new Edges[] { edge, edge2, edge3, edge4, edge5 };

				Graph g = new Graph(nodes, edges);
				return g;
			}else{
				Graph g = ceqaService.getEntityGraph(q.getQuestion());
				return g;
			}
		}
		System.out.println("request!");
		return (null);
	}

}
