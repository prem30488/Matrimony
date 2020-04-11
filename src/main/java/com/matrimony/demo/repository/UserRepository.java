package com.matrimony.demo.repository;

import com.matrimony.demo.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    
    Boolean existsByPhoneNumber(String phoneNumber);
    
    @Query("select case when count(c)> 1 then true else false end from User c where lower(c.email) like lower(:email)")
    boolean existsUserLikeCustomQueryEmail(@Param("email") String email);

    @Query("select case when count(c)> 1 then true else false end from User c where lower(c.username) like lower(:username)")
    boolean existsUserLikeCustomQueryUsername(@Param("username") String username);
    
    @Query("select case when count(c)> 1 then true else false end from User c where lower(c.phoneNumber) like lower(:phoneNumber)")
    boolean existsUserLikeCustomQueryPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
