package com.edgar.restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

public class DouBanTest {
    @Before
    public void setUP() {
        //指定 URL 和端口号
        RestAssured.baseURI = "http://api.douban.com/v2/book";
        RestAssured.port = 80;
    }

    @Test
    public void testGETBook() {
        get("/1220562").then().statusCode(200).body("title", equalTo("满月之夜白鲸现"));
    }

    @Test
    public void testSearchBook(){
        //带参数的请求
        given().param("q", "java8").when().get("/search").then().body("count", equalTo(2));

        //指定 header、cookie、content type
        given().cookie("name", "xx").when().get("/xxx").then();//.body();
        given().header("name", "xx").when().get("/xxx").then();//.body();
        given().contentType("application/json").when().get("/xxx").then();//.body();
    }

    //解析 JSON
    @Test
    public void testJson() {
        get("/1220562").then()
                //取顶级属性“title”
                .body("title", equalTo("满月之夜白鲸现"))
                        //取下一层属性
                .body("rating.max", equalTo(10))
                        //调用数组方法
                .body("tags.size()", is(8))
                        //取数组第一个对象的“name”属性
                .body("tags[0].name", equalTo("片山恭一"))
                        //判断数组元素
                .body("author", hasItems("[日] 片山恭一"));
    }

    //身份验证
    @Test
    public void testAuth() {
//        given().auth().basic(username, password).when().get("/secured").then().statusCode(200);
//        given().auth().oauth(..);
//        given().auth().oauth2(..);
    }

    //Serialization
    @Test
    public void testSerialization() {
        Message message = new Message();
        message.setMessage("My messagee");
        given().
                contentType("application/json").
                body(message).
                when().
                post("/message");

//        You can also serialize the Message instance as a form parameter:
        given().
                contentType("application/json; charset=UTF-16").
                formParam("param1", message).
                when().
                post("/message");

        //Create JSON from a HashMap
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("firstName", "John");
        jsonAsMap.put("lastName", "Doe");

        given().
                contentType("application/json").
                body(jsonAsMap).
                when().
                post("/somewhere").
                then().
                statusCode(200);

        //Using an Explicit Serializer
        given().
                body(message, ObjectMapperType.JAXB).
                when().
                post("/message");
    }
    //Deserialization
    @Test
    public void testDeserialization() {
        Message message = get("/message").as(Message.class);

//        Using an Explicit Deserializer

        message = get("/message").as(Message.class, ObjectMapperType.GSON);
    }
}