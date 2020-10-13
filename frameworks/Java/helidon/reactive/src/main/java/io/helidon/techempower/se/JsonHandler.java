package io.helidon.techempower.se;

import javax.json.Json;
import javax.json.JsonObject;

import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

/**
 * Handler for the json test.
 */
class JsonHandler implements Handler {
    private static final JsonObject RESPONSE = Json.createObjectBuilder()
            .add("message", "Hello, World!")
            .build();

    JsonHandler() {
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        res.send(RESPONSE);
    }
}
