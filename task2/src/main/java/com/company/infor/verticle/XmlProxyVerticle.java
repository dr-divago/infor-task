package com.company.infor.verticle;

import com.company.infor.model.Movie;
import com.company.infor.parser.XmlParser;
import io.reactivex.Completable;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.client.HttpRequest;
import io.vertx.reactivex.ext.web.client.WebClient;
import io.vertx.reactivex.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class XmlProxyVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(XmlProxyVerticle.class);

    private WebClient client;
    private XmlParser parser;
    private String API_KEY;
    private String BASE_URL;

    @Override
    public Completable rxStart() {
        client = WebClient.create(vertx);
        parser = new XmlParser();

        API_KEY = config().getString("apikey");
        BASE_URL = config().getString("baseUrl");

        var router = Router.router(vertx);
        router.get("/:title").handler(this::getTitle);

        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(8080)
                .ignoreElement();
    }

    private void getTitle(RoutingContext ctx) {
        if (!isValidRequest(ctx.pathParam("title"))) {
            ctx.response().setStatusCode(400).end();
        }

        String titleParam = ctx.pathParam("title");
        String endpoint = "/?t="+titleParam+"&r=xml&apikey="+ API_KEY;

        HttpRequest<String> request = client
                .get(443, BASE_URL, endpoint )
                .ssl(true)
                .as(BodyCodec.string());

        request
                .rxSend()
                .timeout(1, TimeUnit.SECONDS)
                .retry(3)
                .subscribe(
                        ok -> {
                            Optional<Movie> maybeMovieInfo = parser.parse(ok.body());
                            maybeMovieInfo.ifPresentOrElse(
                                    movie -> ctx.response().setStatusCode(200).end(movie.toJson()),
                                    () -> {
                                        JsonObject noFound = new JsonObject().put("result", "Nothing found");
                                        ctx.response().setStatusCode(200).end(noFound.encode());
                                    }
                            );

                            },
                        err -> ctx.response().setStatusCode(502).end()
                );
    }

    private boolean isValidRequest(String param) {
        return !param.isEmpty();
    }
}