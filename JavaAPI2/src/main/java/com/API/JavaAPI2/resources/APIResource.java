package com.API.JavaAPI2.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.API.JavaAPI2.api.saying;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class APIResource {

	private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public APIResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new saying(counter.incrementAndGet(), value);
    }
}