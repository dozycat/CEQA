package com.ica.stc.ceqa.service.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ica.stc.ceqa.bo.Edges;
import com.ica.stc.ceqa.bo.Graph;
import com.ica.stc.ceqa.bo.Node;
import com.ica.stc.ceqa.bo.Question;

/*
 * ceqa的control,调用service层的服务。
 */

@Controller
public class CeqaControl {

	@ResponseBody
	@RequestMapping(value = "/hello.io", method = RequestMethod.GET)
	public Object test() {
		Node node = new Node(0, 100, "test");
		Node node2 = new Node(1, 50, "test2");
		Node[] nodes = new Node[] { node, node2 };

		Edges edge = new Edges(0, 0, 1, "test edges");
		Edges[] edges = new Edges[] { edge };

		Graph g = new Graph(nodes, edges);
		System.out.println("request!");
		return (g);
	}

	@ResponseBody
	@RequestMapping(value = "/qa.io", method = RequestMethod.GET)
	public Object qa(Question q) {
		
		return null;
	}

}
