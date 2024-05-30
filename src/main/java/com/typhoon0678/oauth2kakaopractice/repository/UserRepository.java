package com.typhoon0678.oauth2kakaopractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.typhoon0678.oauth2kakaopractice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
