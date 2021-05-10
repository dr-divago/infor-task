package com.company.infor;


import com.company.infor.verticle.XmlProxyVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.DeploymentOptions;
import io.vertx.reactivex.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTask2 {

    private static final Logger logger = LoggerFactory.getLogger(MainTask2.class);

    public static void main(String...arg) {
        var vertx = Vertx.vertx();

        var retriever = ConfigRetriever.create(vertx.getDelegate());
        retriever.getConfig( ar ->{
            if (ar.failed()) {
                logger.error("Error reading config file");
            }
            else {
                logger.info("Config file correctly loaded");
                vertx.rxDeployVerticle(new XmlProxyVerticle(), new DeploymentOptions().setConfig(ar.result()))
                        .subscribe(
                                ok -> logger.info("XmlProxyVerticle running on port {}", ar.result().getString("http.port")),
                                error -> logger.error("Error starting XmlProxyVerticle {}", error)
                        );
            }
        });




    }
}
