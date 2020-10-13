package io.helidon.techempower.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * A bean producing the mustache template.
 */
@ApplicationScoped
public class MustacheProducer {
    @Produces
    @Named("fortunes")
    @ApplicationScoped
    public Mustache produceFortunes() {
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        return mustacheFactory.compile("fortunes.mustache");
    }
}
