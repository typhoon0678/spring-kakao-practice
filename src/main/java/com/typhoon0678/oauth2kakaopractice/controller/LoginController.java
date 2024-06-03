package com.typhoon0678.oauth2kakaopractice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.typhoon0678.oauth2kakaopractice.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private final UserService userService;
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("")
	public String login() {
		return "login";
	}

	@GetMapping("/oauth2/code/kakao")
	public String loginKakao(@RequestParam("code") String code, HttpSession session) {

		String accessToken = userService.getKakaoAccessToken(code);
		logger.info("access token: {}", accessToken);

		return "redirect:/";
	}
}
