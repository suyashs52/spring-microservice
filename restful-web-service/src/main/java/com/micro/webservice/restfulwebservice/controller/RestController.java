package com.micro.webservice.restfulwebservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.micro.webservice.restfulwebservice.dao.UserDao;
import com.micro.webservice.restfulwebservice.entity.User;
import com.micro.webservice.restfulwebservice.exception.UserNotFoundException;

import jakarta.validation.Valid;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	// check HTTP response status,400: bad request
	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello World!";
	}

	@Autowired
	private MessageSource messageSource;

	@GetMapping("national/hello")
	public String helloWorldInternational() {
		//in header set Accept-Language:fr
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
	}

	@Autowired
	private UserDao userDao;

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
