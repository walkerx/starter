package com.example.starter.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

public class RtaHandler implements Handler<RoutingContext>{

  @Override
  public void handle(RoutingContext ctx) {
    HttpServerRequest request = ctx.request();
    ctx.next();
  }

}
