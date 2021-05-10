package com.company.infor;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
class XmlVerticleTest {

    private static JsonObject config;

    @BeforeAll
    public static void init(Vertx vertx) {
        URL configURL = XmlVerticleTest.class.getResource("/config.json");
        config = new JsonObject(vertx.fileSystem().readFileBlocking(configURL.getPath()));
        System.out.println(config);
    }

    @Test
    void whenRequestIsOk_Then200Code(Vertx vertx, VertxTestContext testContext) {

        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(com.company.infor.verticle.XmlProxyVerticle.class.getName(),
                new DeploymentOptions().setConfig(config),
                testContext.succeeding(id -> webClient
                        .get(8080, "localhost", "/lost")
                        .expect(ResponsePredicate.SC_OK)
                        .as(BodyCodec.jsonObject())
                        .putHeader("Content-Type", "application/json")
                        .send(testContext.succeeding(resp -> {
                            testContext.verify(() -> {
                                assertThat(resp.statusCode()).isEqualTo(200);
                                testContext.completeNow();
                });
            }))));
    }

    @Test
    void whenNothingFound_Then200Code(Vertx vertx, VertxTestContext testContext) {

        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(com.company.infor.verticle.XmlProxyVerticle.class.getName(),
                new DeploymentOptions().setConfig(config),
                testContext.succeeding(id -> webClient
                        .get(8080, "localhost", "/adsjdhs")
                        .expect(ResponsePredicate.SC_OK)
                        .as(BodyCodec.jsonObject())
                        .putHeader("Content-Type", "application/json")
                        .send(testContext.succeeding(resp -> {
                            testContext.verify(() -> {
                                assertThat(resp.statusCode()).isEqualTo(200);
                                assertThat(resp.body()).isEqualTo(new JsonObject().put("result", "Nothing found"));
                                testContext.completeNow();
                            });
                        }))));
    }
}
