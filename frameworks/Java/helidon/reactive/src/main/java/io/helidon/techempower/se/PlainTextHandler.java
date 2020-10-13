package io.helidon.techempower.se;

import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

/**
 * Handler for the plain text test.
 */
class PlainTextHandler implements Handler {
    PlainTextHandler() {
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        res.send("Hello, World!");
    }
}
