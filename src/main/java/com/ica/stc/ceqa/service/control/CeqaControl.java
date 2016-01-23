package com.ica.stc.ceqa.service.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * ceqa的control,调用service层的服务。
 */

@Controller
public class CeqaControl {

	@ResponseBody
	@RequestMapping(value = "/hello.io", method = RequestMethod.GET)
	public String test() {
		System.out.println("hello request!");
		return ("Hello World");
	}

}
