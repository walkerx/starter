package com.example.starter.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class ImpressionFilter implements Handler<RoutingContext> {

  @Override
  public void handle(RoutingContext ctx) {
    ctx.next();
  }

}
