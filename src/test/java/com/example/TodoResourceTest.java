package com.example;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.quarkus.demo.todolist.todos.Todo;
import org.quarkus.demo.todolist.todos.TodoResource;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode;

@QuarkusTest
@TestHTTPEndpoint(TodoResource.class)
@TestMethodOrder(OrderAnnotation.class)
public class TodoResourceTest {

    @Test
    @Order(1)
    public void testGetAll() {
        final List<Todo> expectedResult = new ArrayList<>();
        var todo = new Todo("Buy tickets for the concert", false);
        todo.setId(1L);
        expectedResult.add(todo);

        todo = new Todo("Learn how to make a cheesecake", true);
        todo.setId(2L);
        expectedResult.add(todo);

        todo = new Todo("Replace the bicycle light batteries", false);
        todo.setId(3L);
        expectedResult.add(todo);

        List<Todo> result = given()
                .when().get()
                .then()
                .statusCode(StatusCode.OK)
                .body("size()", greaterThan(0))
                .extract().body().jsonPath().getList("", Todo.class);

        assertThat(result, containsInAnyOrder(expectedResult.toArray()));
    }

    @Test
    @Order(2)
    public void testGetById() {
        var expected = new Todo("Buy tickets for the concert", false);
        expected.setId(1L);

        Todo result = given()
                .pathParam("id", 1)
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(StatusCode.OK)
                .extract().body().as(Todo.class);

        assertThat(expected, is(result));
    }

    @Test
    @Order(3)
    public void testCreateTodo() {
        var todo = new Todo("Created in test", false);

        given()
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .post()
                .then()
                .statusCode(StatusCode.CREATED);
    }

    @Test
    @Order(4)
    public void testPutResource() {
        var todo = new Todo("Todo 1 modified", true);
        todo.setId(1L);

        Todo result = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(todo)
                .when()
                .put("/{id}")
                .then()
                .statusCode(StatusCode.OK)
                .extract().body().as(Todo.class);

        assertThat(result, equalTo(todo));
    }

    @Test
    @Order(5)
    public void testPatch() {
        var todo = new Todo("patched title", true);
        todo.setId(1L);

        Todo result = given()
                .pathParam("id", 1)
                .contentType("application/merge-patch+json")
                .body("{ \"title\" : \"patched title\"}")
                .when()
                .patch("/{id}")
                .then()
                .statusCode(StatusCode.OK)
                .extract().body().as(Todo.class);

        assertThat(result, equalTo(todo));
    }

    @Test
    @Order(6)
    public void testDelete() {
        given()
                .pathParam("id", 1)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(StatusCode.NO_CONTENT);
    }
}