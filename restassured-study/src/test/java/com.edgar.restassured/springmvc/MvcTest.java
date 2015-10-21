package com.edgar.restassured.springmvc;

import org.junit.Test;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static com.jayway.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class MvcTest {

    @Test
    public void testMVC() {
        given().
                standaloneSetup(new GreetingController()).
                param("name", "Johan").
                when().
                get("/greeting").
                then().
                statusCode(200).
                body("id", equalTo(1)).
                body("content", equalTo("Hello, Johan!"));
    }
}