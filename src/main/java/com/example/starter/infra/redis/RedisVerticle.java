package com.example.starter.infra.redis;

import java.util.concurrent.atomic.AtomicBoolean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.serviceproxy.ServiceBinder;

public class RedisVerticle extends AbstractVerticle {

  private static final int MAX_RECONNECT_RETRIES = 16;

  private final RedisOptions options = new RedisOptions();
  private RedisConnection client;
  private final AtomicBoolean CONNECTING = new AtomicBoolean();

  @Override
  public void start() {
    new ServiceBinder(vertx)
      .setAddress("store.redis.service")
      .registerLocal(RedisService.class, new RedisProvider());
    createRedisClient()
      .onSuccess(conn -> {
        // connected to redis!
      });
  }

  /**
   * Will create a redis client and setup a reconnect handler when there is
   * an exception in the connection.
   */
  private Future<RedisConnection> createRedisClient() {
    Promise<RedisConnection> promise = Promise.promise();

    if (CONNECTING.compareAndSet(false, true)) {
      Redis.createClient(vertx, options)
        .connect()
        .onSuccess(conn -> {

          // make sure to invalidate old connection if present
          if (client != null) {
            client.close();
          }

          // make sure the client is reconnected on error
          conn.exceptionHandler(e -> {
            // attempt to reconnect,
            // if there is an unrecoverable error
            attemptReconnect(0);
          });

          // allow further processing
          promise.complete(conn);
          CONNECTING.set(false);
        }).onFailure(t -> {
          promise.fail(t);
          CONNECTING.set(false);
        });
    } else {
      promise.complete();
    }

    return promise.future();
  }

  /**
   * Attempt to reconnect up to MAX_RECONNECT_RETRIES
   */
  private void attemptReconnect(int retry) {
    if (retry > MAX_RECONNECT_RETRIES) {
      // we should stop now, as there's nothing we can do.
      CONNECTING.set(false);
    } else {
      // retry with backoff up to 10240 ms
      long backoff = (long) (Math.pow(2, Math.min(retry, 10)) * 10);

      vertx.setTimer(backoff, timer -> {
        createRedisClient()
          .onFailure(t -> attemptReconnect(retry + 1));
      });
    }
  }
}
