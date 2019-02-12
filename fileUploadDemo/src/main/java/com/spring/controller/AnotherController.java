package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnotherController {

	@RequestMapping("/")
	public String getHomePage() {
		return "index";
	}
	
	@RequestMapping("/display")
	public String displayStudent() {
		return "display";
	}
}
