package com.ica.stc.ceqa.service.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * ceqa的control,调用service层的服务。
 */

@Controller
@RequestMapping("/")
public class CeqaControl {
	
	
	@RequestMapping("/hello.io")
	public String test() {
		return ("Hello World");
	}

}
