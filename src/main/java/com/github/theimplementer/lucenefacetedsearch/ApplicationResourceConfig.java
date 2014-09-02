package com.github.theimplementer.lucenefacetedsearch;

import com.github.theimplementer.lucenefacetedsearch.books.BooksResource;
import com.github.theimplementer.lucenefacetedsearch.rest.SimpleResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationResourceConfig extends ResourceConfig {

    public ApplicationResourceConfig() {
        register(new DependencyBinder());
        register(SimpleResource.class);
        register(BooksResource.class);
        register(JacksonFeature.class);
    }
}
