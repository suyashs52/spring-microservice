package com.micro.webservice.restfulwebservice.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"field2"})
@JsonFilter("SomeBeanFilter")
public class SomeBean {
    String field1;
    @JsonIgnore //this is called static filtering
    String field2;
    String field3;

    public SomeBean(String value1, String value2, String value3) {
        this.field1=value1;
        field2=value2;
        field3=value3;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }


    @Override
    public String toString() {
        return "SomeBean{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                '}';
    }
}
