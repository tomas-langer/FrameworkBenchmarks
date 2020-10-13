package io.helidon.techempower.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST endpoint implementing the TechEmpower JSON Serialization test.
 */
@ApplicationScoped
@Path("/json")
public class JsonService {
    private static final HelloWorld HELLO_WORLD = new HelloWorld();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response jsonHelloWorld() {
        return Response.ok(HELLO_WORLD)
                .header("Server", "helidon")
                .build();
    }

    /**
     * Model used as a transfer object (through JSON serialization).
     */
    public static class HelloWorld {
        public String getMessage() {
            return "Hello, World!";
        }
    }
}