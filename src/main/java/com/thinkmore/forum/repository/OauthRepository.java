package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Oauth;
import com.thinkmore.forum.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OauthRepository extends JpaRepository<Oauth, UUID> {
    Optional<Oauth> findByUsers(Users users);
    Optional<Oauth> findByOpenid(String openid);
}
