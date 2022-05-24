package com.example.starter.route;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

public class CommonRoute {

  public Route build(Router router, Vertx vertx) {
    return router.route()
      .handler(TimeoutHandler.create(100))
      .handler(CorsHandler.create("*"))
      .handler(ErrorHandler.create(vertx));
  }
}
