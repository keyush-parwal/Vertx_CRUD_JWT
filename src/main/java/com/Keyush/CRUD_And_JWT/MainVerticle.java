package com.Keyush.CRUD_And_JWT;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    DBConfig.setup(); // Initialize the database

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    JWTAuthProvider jwtAuthProvider = new JWTAuthProvider();
    JWTAuth jwtAuth = jwtAuthProvider.getJwtAuth(vertx);

    // Add JWTAuthHandler to the router for secured routes
    router.route("/students/*").handler(JWTAuthHandler.create(jwtAuth));

    // Auth
    new AuthController(vertx, router);

    // CRUD
    new StudentController(vertx, router);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }
}
