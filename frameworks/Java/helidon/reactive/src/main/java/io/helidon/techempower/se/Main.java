/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.techempower.se;

import java.util.concurrent.TimeUnit;

import io.helidon.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * TechEmpower benchmark test.
 * Implements all tests except for caching.
 */
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    public static void main(String[] args) {
        LogConfig.initClass();

        Config config = Config.create();

        WebServer.builder()
                .config(config.get("server"))
                .routing(createRouting(config))
                .addMediaSupport(JsonpSupport.create())
                .build()
                .start()
                .await(10, TimeUnit.SECONDS);
    }

    /**
     * Creates new {@link Routing}.
     *
     * @return the new instance
     */
    private static Routing createRouting(Config config) {
        DbClient dbClient = DbClient.create(config.get("dbclient"));

        return Routing.builder()
                .any((req, res) -> {
                    // required header for each responseß
                    res.headers().add("Server", "Helidon");
                    req.next();
                })
                .get("/json", new JsonHandler())
                .get("/plaintext", new PlainTextHandler())
                .register("/db", new DbService(dbClient))
                .get("/fortunes", new FortuneHandler(dbClient, getTemplate()))
                .build();
    }

    private static Mustache getTemplate() {
        MustacheFactory mf = new DefaultMustacheFactory();
        return mf.compile("fortunes.mustache");
    }
}
