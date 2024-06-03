package com.typhoon0678.oauth2kakaopractice.config;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.typhoon0678.oauth2kakaopractice.service.OAuth2UserService;
import com.typhoon0678.oauth2kakaopractice.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

	private final OAuth2UserService oauth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(config -> config.anyRequest().permitAll())
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
			.oauth2Login(oauth2Configurer -> oauth2Configurer
				.loginPage("/login")
				.successHandler(successHandler())
				.failureHandler(failureHandler())
				.userInfoEndpoint(userInfoEndpointConfig ->
					userInfoEndpointConfig.userService(oauth2UserService))
			).build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {

		return ((request, response, authentication) -> {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();

			String id = defaultOAuth2User.getAttributes().get("id").toString();

			String body = """
				{"id":"%s"}
				""".formatted(id);

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());

			PrintWriter writer = response.getWriter();
			writer.println(body);
			writer.flush();
		});
	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {

		return ((request, response, authentication) -> {

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());

			PrintWriter writer = response.getWriter();
			writer.println(authentication.getMessage());
			writer.flush();
		});
	}
}
