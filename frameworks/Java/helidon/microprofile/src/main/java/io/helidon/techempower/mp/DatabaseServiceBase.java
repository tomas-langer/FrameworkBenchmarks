package io.helidon.techempower.mp;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.helidon.techempower.mp.database.DatabaseAccess;
import io.helidon.techempower.mp.model.FortuneModel;

import com.github.mustachejava.Mustache;

/**
 * REST endpoint implementing the TechEmpower:
 * <ul>
 *   <li>Single Database Query test</li>
 *   <li>Multiple Database Queries test</li>
 *   <li>Database Updates test</li>
 *   <li>Fortunes test</li>
 * </ul>
 */
public abstract class DatabaseServiceBase {
    private final DatabaseAccess databaseAccess;
    private final Mustache mustache;

    protected DatabaseServiceBase(DatabaseAccess databaseAccess, Mustache mustache) {
        this.databaseAccess = databaseAccess;
        this.mustache = mustache;
    }

    /**
     * Fetch a random single record from the database.
     */
    @GET
    @Path("/single")
    @Produces(MediaType.APPLICATION_JSON)
    public Response singleQuery() {
        return Response.ok()
                .header("Server", "helidon")
                .entity(databaseAccess.randomWorld())
                .build();
    }

    /**
     * Fetch a list of records from the database.
     */
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response multipleQueries(@QueryParam("queries") String queriesParam) {
        int queries = toInt(queriesParam, 1, 500);

        return Response.ok()
                .header("Server", "helidon")
                .entity(databaseAccess.randomWorlds(queries))
                .build();
    }

    /**
     * Update a set of records in the database.
     */
    @GET
    @Path("/updates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response multipleUpdates(@QueryParam("updates") String updatesParam) {
        int updates = toInt(updatesParam, 1, 500);

        return Response.ok()
                .header("Server", "helidon")
                .entity(databaseAccess.updateRandomWorlds(updates))
                .build();
    }

    /**
     * Get the list of fortunes, add to them, sort them and process them using a template.
     */
    @GET
    @Path("/fortunes")
    @Produces(MediaType.TEXT_HTML + "; charset=utf-8")
    public Response fortunes() {
        List<FortuneModel> selected = databaseAccess.allFortunes();

        List<FortuneModel> all = new ArrayList<>(selected.size() + 1);
        all.addAll(selected);
        all.add(new FortuneModel(0, "Additional fortune added at request time."));
        all.sort(Comparator.comparing(FortuneModel::getMessage));

        StringWriter writer = new StringWriter();

        try {
            mustache.execute(writer, Map.of("fortunes", all))
                    .flush();
        } catch (IOException e) {
            return Response.serverError().entity(e).build();
        }

        return Response.ok()
                .header("Server", "helidon")
                .entity(writer.toString())
                .build();
    }

    private int toInt(String queriesParam, int min, int max) {
        try {
            int result = Integer.parseInt(queriesParam);
            if (result < min) {
                return min;
            }
            return Math.min(result, max);
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}