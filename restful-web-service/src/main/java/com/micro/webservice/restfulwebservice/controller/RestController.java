package com.micro.webservice.restfulwebservice.controller;

import com.micro.webservice.restfulwebservice.dao.UserDao;
import com.micro.webservice.restfulwebservice.entity.User;
import com.micro.webservice.restfulwebservice.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    //http://localhost:8080/actuator
    //http://localhost:8080/actuator/metrics/http.server.requests
    //http://localhost:8080/h2-console
    //spring security user is : user, password is in console
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserDao userDao;

    // check HTTP response status,400: bad request
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("national/hello")
    public String helloWorldInternational() {
        //in header set Accept-Language:fr
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
    }

    // add header: Accept: application/xml
    @GetMapping("/users")
    public List<User> getAllUser() {
        return userDao.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("User not found with id - " + id);

        return user;

    }

    @GetMapping("/users/e/{id}")
    public EntityModel<User> getUserHatoas(@PathVariable int id) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("User not found with id - " + id);
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());
        entityModel.add(link.withRel("all-users"));
        return entityModel;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userDao.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable int id) {
        User user = userDao.findOne(id);
        if (user == null)
            throw new UserNotFoundException("User not found with id - " + id);
        userDao.deleteUser(id);
        return user;

    }
}
