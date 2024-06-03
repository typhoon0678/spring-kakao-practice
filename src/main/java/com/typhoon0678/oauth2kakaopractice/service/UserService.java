package com.typhoon0678.oauth2kakaopractice.service;

import org.springframework.stereotype.Service;

import com.typhoon0678.oauth2kakaopractice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public String getKakaoAccessToken(String code) {

		return null;
	}

}
