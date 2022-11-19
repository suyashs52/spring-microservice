package com.micro.webservice.restfulwebservice.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.micro.webservice.restfulwebservice.entity.User;

@Component
public class UserDao {

	// use jpa dao

	static int userCount = 0;
	private static List<User> users = new ArrayList<>();

	static {
		users.add(new User(userCount++, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(userCount++, "Eve", LocalDate.now().minusYears(25)));
		users.add(new User(userCount++, "Jim", LocalDate.now().minusYears(20)));

	}

	public List<User> findAll() {
		return users;
	}

	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId() == id;

		return users.stream().filter(predicate).findFirst().orElse(null);
	}

	public User createUser(User user) {
		user.setId(userCount++);
		users.add(user);
		return user;
	}

	public void deleteUser(int id) {
		Predicate<? super User> predicate = user -> user.getId() == id;
		users.removeIf(predicate);

	}

}
