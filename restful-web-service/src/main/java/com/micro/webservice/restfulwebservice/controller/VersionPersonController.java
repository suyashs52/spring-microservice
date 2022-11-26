package com.micro.webservice.restfulwebservice.controller;

import com.micro.webservice.restfulwebservice.entity.Names;
import com.micro.webservice.restfulwebservice.entity.PersonV1;
import com.micro.webservice.restfulwebservice.entity.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionPersonController {

	@GetMapping("/v1/person")
	public PersonV1 getPerson() {
		return new PersonV1("Bob Marley");
	}

	@GetMapping("/v2/person")
	public PersonV2 getSecondPersonVersionOfPerson() {
		return new PersonV2(new Names("Bob","Marley"));
	}

	@GetMapping(path = "/person",params = "version=1")
	public PersonV1 getV1PersonUseReqParam(){
		return  new PersonV1("Johny Dep");

	}

	@GetMapping(path = "/person",params = "version=2")
	public PersonV2 getV2PersonUseReqParam(){
		return new PersonV2(new Names("Bob","Marley"));
	}
	@GetMapping(path = "/person/header",headers = "X-API-VERSION=1")
	public PersonV1 getV1PersonUseReqHeader(){
		return  new PersonV1("Johny Dep");

	}

	@GetMapping(path = "/person/header",headers = "X-API-VERSION=2")
	public PersonV2 getV2PersonUseReqHeader(){
		//in header use Accept-Headers
		return new PersonV2(new Names("Bob","Marley"));
	}

	@GetMapping(path = "/person/accept",produces = "application/vnd.app-v1+json")
	public PersonV1 getV1PersonUseMediaType(){
		return  new PersonV1("Johny Dep");

	}

	@GetMapping(path = "/person/accept",produces = "application/vnd.app-v2+json")
	public PersonV2 getV2PersonUseMediaType(){
		return new PersonV2(new Names("Bob","Marley"));
	}
}
