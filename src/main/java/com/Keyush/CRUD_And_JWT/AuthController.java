package com.Keyush.CRUD_And_JWT;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import io.ebean.Ebean;
import io.ebean.Query;
import io.ebean.ExpressionList;

public class AuthController {
  private final Vertx vertx;
  private final JWTAuthProvider jwtAuthProvider;

  public AuthController(Vertx vertx, Router router) {
    this.vertx = vertx;
    this.jwtAuthProvider = new JWTAuthProvider();
    router.post("/login").handler(this::login);
  }

  private void login(RoutingContext context) {
    JsonObject body = context.getBodyAsJson();
    if (body == null) {
      context.response()
        .setStatusCode(400)
        .end("Invalid JSON format");
      return;
    }

    String name = body.getString("name");
    String branch = body.getString("branch");

    if (name == null || branch == null) {
      context.response()
        .setStatusCode(400)
        .end("Missing name or branch");
      return;
    }

    // Validate user against the database
    boolean userValid = validateUser(name, branch);
    if (!userValid) {
      context.response()
        .setStatusCode(401)
        .end("Invalid credentials");
      return;
    }

    try {
      String token = JWTAuthProvider.generateToken(name, branch);

      context.response()
        .setStatusCode(200)
        .putHeader("Content-Type", "application/json")
        .end(new JsonObject().put("token", token).encode());
    } catch (Exception e) {
      context.response()
        .setStatusCode(500)
        .end("Failed to generate token: " + e.getMessage());
    }
  }

  private boolean validateUser(String name, String branch) {
    // Example query to validate user. Adjust according to your database schema.
    ExpressionList<Student> query = Ebean.find(Student.class)
      .where()
      .eq("name", name)
      .eq("branch", branch);

    return query.findOne() != null;
  }
}

