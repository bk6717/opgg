package com.cos.opgg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspTestController {
	
	@GetMapping("/jsp/test")
	public String test() {
		return "viewTest";
	}
}
