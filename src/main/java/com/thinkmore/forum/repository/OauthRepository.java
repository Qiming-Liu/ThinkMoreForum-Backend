package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OauthRepository extends JpaRepository<Oauth, UUID> {

    Optional<Oauth> findByOpenid(String openid);

}
