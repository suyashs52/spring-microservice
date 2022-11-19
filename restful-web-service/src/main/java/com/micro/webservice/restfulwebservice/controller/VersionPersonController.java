package com.micro.webservice.restfulwebservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionPersonController {

	@GetMapping("/v1/person")
	public Person getPerson() {
		return new PersonV1("Bob Marley");
	}
}
