package com.hoang.springapijwt.repository;

import com.hoang.springapijwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select distinct user from User user")
    List<User> getAllUser();

    @Query("select user from User user where user.username=:username")
    User findByUsername(@Param("username") String username);
}
