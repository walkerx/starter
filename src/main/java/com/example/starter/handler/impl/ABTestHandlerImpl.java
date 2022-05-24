package com.example.starter.handler.impl;

import com.example.starter.handler.ABTestHandler;

import io.vertx.ext.web.RoutingContext;

public class ABTestHandlerImpl implements ABTestHandler {

  @Override
  public void handle(RoutingContext ctx) {
    // TODO Auto-generated method stub
    ctx.next();
  }

}
