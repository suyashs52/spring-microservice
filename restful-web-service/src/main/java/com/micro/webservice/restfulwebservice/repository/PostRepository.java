package com.micro.webservice.restfulwebservice.repository;

import com.micro.webservice.restfulwebservice.entity.Post;
import com.micro.webservice.restfulwebservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
