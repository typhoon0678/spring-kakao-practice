package com.typhoon0678.oauth2kakaopractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	@GetMapping
	public String index() {
		return "index";
	}
}
