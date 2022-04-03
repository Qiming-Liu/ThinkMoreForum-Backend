package com.thinkmore.forum.repository;

import java.util.Optional;

import com.thinkmore.forum.entity.redis.JwtRouter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JwtRouterRepository extends CrudRepository<JwtRouter, String> {
    Optional<JwtRouter> findById(String fakeJwt);
}
