package org.collaborative.cycling.webapp.controllers;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path(UpdatesController.MAPPING)
public class UpdatesController {

    public static final String MAPPING = "/updates";

    public UpdatesController() {
    }

    @GET
    public Integer getLastVersion() throws IOException {
        return 1;
    }
}

