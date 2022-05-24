package com.example.starter.infra.redis;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;

@ProxyGen
@VertxGen
public interface RedisService {

  static RedisService createProxy(Vertx vertx, String address) {
    return new RedisServiceVertxEBProxy(vertx, address);
  }
}
