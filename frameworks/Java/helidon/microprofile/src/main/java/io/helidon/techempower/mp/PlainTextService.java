package io.helidon.techempower.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Implements a class that returns a plaintext response
 */
@ApplicationScoped
@Path("/plaintext")
public class PlainTextService {
  
  /**
   * Returns "Hello, World!" in plaintext format
   * along-with appropriate headers
   * @return Response (Plaintext format)
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response GetPlaintext() {
    return Response.ok("Hello, World!")
            .header("Server", "helidon")
            .build();
  }
}