package com.example.starter.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class BidHandler implements Handler<RoutingContext>{

  @Override
  public void handle(RoutingContext ctx) {
    HttpServerResponse response = ctx.response();
    response.putHeader("content-type", "text/plain");
    // response.putHeader("content-encoding", "gzip");
    response.end("Hello World from Vert.x-Web!");
  }

}
