package com.example.starter;

import com.example.starter.infra.redis.RedisVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Future<String> redisFuture = vertx.deployVerticle(RedisVerticle.class.getName());
    Future<HttpServer> httpFuture = vertx.createHttpServer()
      .requestHandler(new AppBuilder(vertx).build())
      .listen(8888);

    CompositeFuture.join(redisFuture, httpFuture).onComplete(ar -> {
      if (ar.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(ar.cause());
      }
    });

  }

}
