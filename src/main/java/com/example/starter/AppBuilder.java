package com.example.starter;

import com.example.starter.handler.ABTestHandler;
import com.example.starter.handler.BidHandler;
import com.example.starter.handler.ClickFilter;
import com.example.starter.handler.ImpressionFilter;
import com.example.starter.handler.RtaHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

public class AppBuilder {
  private final Router router;
  private final Vertx vertx;

  public AppBuilder(Vertx vertx) {
    this.vertx = vertx;
    router = Router.router(vertx);
  }

  public Router build() {
    this.router.route()
      .handler(TimeoutHandler.create(100))
      .handler(CorsHandler.create("*"));

    this.router.route("/bid")
      .handler(new ImpressionFilter())
      .handler(new ClickFilter())
      .handler(ABTestHandler.create())
      .handler(new RtaHandler())
      .handler(new BidHandler());

    this.router.route().handler(ErrorHandler.create(vertx));
    return this.router;
  }

  // public void resetRouter() {
  //   this.router.getRoutes().clear();
  // }

}
