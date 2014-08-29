package com.github.theimplementer.lucenefacetedsearch.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("rest/test")
public class SimpleResource {

    @GET
    public String test() {
        return "test";
    }
}
