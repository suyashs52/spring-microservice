package com.micro.webservice.restfulwebservice.repository;

import com.micro.webservice.restfulwebservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
