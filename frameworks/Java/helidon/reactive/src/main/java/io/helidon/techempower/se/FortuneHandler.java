package io.helidon.techempower.se;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import io.helidon.techempower.se.models.Fortune;
import io.helidon.common.http.MediaType;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import com.github.mustachejava.Mustache;

/**
 * Handler for the fortunes test.
 */
class FortuneHandler implements Handler {

    private final DbClient dbClient;
    private final Mustache template;

    FortuneHandler(DbClient dbClient, Mustache template) {
        this.dbClient = dbClient;
        this.template = template;
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        dbClient.execute(it -> it.namedQuery("all-fortunes"))
                .map(row -> row.as(this::toFortune))
                .collectList()
                .thenAccept(fortunes -> {
                    List<Fortune> all = new ArrayList<>(fortunes.size() + 1);
                    all.addAll(fortunes);
                    all.add(new Fortune(0, "Additional fortune added at request time."));
                    all.sort(Comparator.comparing(Fortune::getMessage));

                    StringWriter writer = new StringWriter();

                    try {
                        template.execute(writer, Map.of("fortunes", all))
                                .flush();
                        res.headers()
                                .contentType(MediaType.TEXT_HTML.withCharset(StandardCharsets.UTF_8.name()));
                        res.send(writer.toString());
                    } catch (IOException e) {
                        res.send(e);
                    }

                });

    }

    private Fortune toFortune(DbRow dbRow) {
        return new Fortune(dbRow.column(1).as(Integer.class),
                           dbRow.column(2).as(String.class));
    }
}
