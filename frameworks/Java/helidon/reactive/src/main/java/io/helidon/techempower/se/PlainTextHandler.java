package io.helidon.techempower.se;

import java.nio.charset.StandardCharsets;

import io.helidon.common.http.DataChunk;
import io.helidon.common.http.MediaType;
import io.helidon.common.reactive.Single;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

/**
 * Handler for the plain text test.
 */
class PlainTextHandler implements Handler {
    private static final byte[] MESSAGE = "Hello, World!".getBytes(StandardCharsets.UTF_8);

    PlainTextHandler() {
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        res.headers().contentType(MediaType.TEXT_PLAIN);
        res.send(Single.just(DataChunk.create(MESSAGE)));
    }
}
