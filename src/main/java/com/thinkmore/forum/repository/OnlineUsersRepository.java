package com.thinkmore.forum.repository;

import java.util.List;
import java.util.Optional;

import com.thinkmore.forum.entity.redis.OnlineUser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineUsersRepository extends CrudRepository<OnlineUser, String> {

    List<OnlineUser> findAll();

    Optional<OnlineUser> findById(String Id);

    Optional<OnlineUser> findByUsername(String username);
}