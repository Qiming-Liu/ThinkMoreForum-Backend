package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowerRepository extends JpaRepository<FollowUser, UUID> {

//    @Query("select a from FollowUser a order by a.id desc")
//    List<FollowUser> findAllByOrderByIdDesc();


    List<FollowUser> findAllByUsersId(UUID userId);

//    List<FollowUser> findAllByFollowedUsersId(UUID FollowedUsersId);

    //@Query("select a from FollowUser a where a.followed_users_id = ?1")
    List<FollowUser> findAllByFollowedUsersId(UUID FollowedUsersId);

}
