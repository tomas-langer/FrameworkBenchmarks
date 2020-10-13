package io.helidon.techempower.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;

import io.helidon.techempower.mp.database.DatabaseAccess;

import com.github.mustachejava.Mustache;

@ApplicationScoped
@Path("/db/raw")
public class DatabaseServiceRaw extends DatabaseServiceBase {
    @Inject
    DatabaseServiceRaw(@Named("raw") DatabaseAccess databaseAccess,
                       @Named("fortunes") Mustache mustache) {
        super(databaseAccess, mustache);
    }
}
