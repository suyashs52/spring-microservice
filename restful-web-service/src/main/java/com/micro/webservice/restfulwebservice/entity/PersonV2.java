package com.micro.webservice.restfulwebservice.entity;

public class PersonV2 {
    private  Names name;

    public PersonV2(Names name) {
        this.name = name;
    }

    public Names getName() {
        return name;
    }

    public void setName(Names name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonV2{" +
                "name=" + name +
                '}';
    }
}
