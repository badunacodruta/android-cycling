package org.collaborative.cycling.webapp.controllers;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(VersionController.MAPPING)
public class VersionController {

    public static final String MAPPING = "/version";

    public VersionController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getVersionInfo() throws IOException {
        return "v1.0";
    }

}
