package com.micro.webservice.restfulwebservice.controller;

import com.micro.webservice.restfulwebservice.entity.Post;
import com.micro.webservice.restfulwebservice.entity.User;
import com.micro.webservice.restfulwebservice.exception.UserNotFoundException;
import com.micro.webservice.restfulwebservice.repository.PostRepository;
import com.micro.webservice.restfulwebservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/jpa")
public class UsersJPARestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;
    // add header: Accept: application/xml
    @GetMapping("/users")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found with id - " + id);

        return user.get();

    }

    @GetMapping("/users/e/{id}")
    public EntityModel<User> getUserHatoas(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found with id - " + id);
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());
        entityModel.add(link.withRel("all-users"));
        return entityModel;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found with id - " + id);
        userRepository.deleteById(id);
        return user.get();

    }


    @GetMapping("/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found with id - " + id);

        return user.get().getPost();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createUserPosts(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found with id - " + id);
        post.setUser(user.get());
        Post savePost=postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savePost.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }
}
