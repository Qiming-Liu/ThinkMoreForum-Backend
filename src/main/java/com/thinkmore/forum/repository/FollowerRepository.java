package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowerRepository extends JpaRepository<FollowUser, UUID> {

//    @Query("select a from FollowUser a order by a.id desc")
//    List<FollowUser> findAllByOrderByIdDesc();


    List<FollowUser> findAllByUsersId(UUID userId);
}
