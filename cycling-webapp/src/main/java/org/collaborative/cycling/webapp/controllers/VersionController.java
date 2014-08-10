package org.collaborative.cycling.webapp.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path(VersionController.MAPPING)
public class VersionController {

    public static final String MAPPING = "/";
    public static final String MAPPING_VERSION = "version";

    public VersionController() {
    }

    @GET
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    public String getVersionInfo() throws IOException {
        return "v1.0";
    }

}
