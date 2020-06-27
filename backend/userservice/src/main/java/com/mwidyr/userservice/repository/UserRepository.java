package com.mwidyr.userservice.repository;

import com.mwidyr.userservice.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    List<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.mobileNumber) = LOWER(:mobileNumber)")
    List<User> findByMobileNumber(@Param("mobileNumber") String mobileNumber);

    List<User> findAll();
}
