package com.example.starter.handler;

import com.example.starter.handler.impl.ABTestHandlerImpl;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface ABTestHandler extends Handler<RoutingContext> {

  static ABTestHandler create() {
    return new ABTestHandlerImpl();
  }

}

