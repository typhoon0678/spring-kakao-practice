package com.typhoon0678.oauth2kakaopractice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import com.typhoon0678.oauth2kakaopractice.domain.User;
import com.typhoon0678.oauth2kakaopractice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final Logger logger = LoggerFactory.getLogger(OAuth2UserService.class);
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		Map<String, Object> attributes = oAuth2User.getAttributes();
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			logger.info("usrInfo key {}", entry.getKey());
			logger.info("usrInfo value {}", entry.getValue());
		}

		// Role generate
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");

		// nameAttributeKey
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		// DB 저장로직이 필요하면 추가
		Map<String, String> properties = (Map<String, String>) attributes.get("properties");
		String nickname = properties.get("nickname");
		if (!userRepository.existsByUsername(nickname)) {
			userRepository.save(User.builder().username(nickname).build());
		}

		return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
	}
}

