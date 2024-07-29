package com.Keyush.CRUD_And_JWT;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
;

import java.util.List;

public class StudentController implements Handler<RoutingContext> {

  private final StudentService studentService;

  public StudentController(Vertx vertx, Router router) {
    studentService = new StudentService();

    router.post("/students").handler(this::createStudent);
    router.get("/students/:id").handler(this::getStudent);
    router.get("/students").handler(this::getAllStudents);
    router.put("/students/:id").handler(this::updateStudent);
    router.delete("/students/:id").handler(this::deleteStudent);
  }


  private void createStudent(RoutingContext context) {
    try {
      // Decode the JSON body to a Student object
      Student student = Json.decodeValue(context.getBodyAsString(), Student.class);

      if (student.getName() == null || student.getBranch() == null) {
        throw new IllegalArgumentException("Missing parameters");
      }

      studentService.createStudent(student);
      context.response()
        .setStatusCode(201)
        .end(Json.encodePrettily(student));
    } catch (IllegalArgumentException e) {
      context.response()
        .setStatusCode(400)
        .end("Invalid input data: " + e.getMessage());
    } catch (Exception e) {
      context.response()
        .setStatusCode(500)
        .end("Internal Server Error: " + e.getMessage());
    }
  }

  private void getStudent(RoutingContext context) {
    int id = Integer.parseInt(context.pathParam("id"));
    Student student = studentService.getStudent(id);
    if (student != null) {
      context.response()
        .setStatusCode(200)
        .end(Json.encodePrettily(student));
    } else {
      context.response()
        .setStatusCode(404)
        .end();
    }
  }

  private void getAllStudents(RoutingContext context) {
    List<Student> students = studentService.getAllStudents();
    context.response()
      .setStatusCode(200)
      .end(Json.encodePrettily(students));
  }

  private void updateStudent(RoutingContext context) {
    int id = Integer.parseInt(context.pathParam("id"));
    Student student = Json.decodeValue(context.getBodyAsString(), Student.class);
    student.setId(id);
    studentService.updateStudent(student);
    context.response()
      .setStatusCode(200)
      .end(Json.encodePrettily(student));
  }

  private void deleteStudent(RoutingContext context) {
    int id = Integer.parseInt(context.pathParam("id"));
    studentService.deleteStudent(id);
    context.response()
      .setStatusCode(204)
      .end();
  }

  @Override
  public void handle(RoutingContext event) {
  }
}

