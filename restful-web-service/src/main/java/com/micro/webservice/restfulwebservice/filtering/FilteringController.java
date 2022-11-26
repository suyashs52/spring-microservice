package com.micro.webservice.restfulwebservice.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    @GetMapping("/filtering")
    public SomeBean filtering() {
        return new SomeBean("Value1", "Value2", "Value3");
    }


    @GetMapping("/filtering-list")
    public List<SomeBean> filteringList() {

        return Arrays.asList(new SomeBean("Value1", "Value2", "Value3"),
                new SomeBean("Value4", "Value5", "Value6"), new SomeBean("Value7", "Value8", "Value9"));

    }

    @GetMapping("/filtering-d-list")
    public MappingJacksonValue filteringDynmList() {
        SomeBean bean=new SomeBean("Value1", "Value2", "Value3");
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(bean);
        SimpleBeanPropertyFilter filter=  SimpleBeanPropertyFilter.filterOutAllExcept("field1");
        FilterProvider filters=new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

}
