package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.Utilities;
import org.collaborative.cycling.models.Track;
import org.collaborative.cycling.services.track.graph.TrackService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path(TrackController.MAPPING)
public class TrackController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TrackController.class);

    @Autowired
    private TrackService trackService;

    public static final String MAPPING = "/track";
    public static final String MAPPING_VERSION = "/";

    public TrackController() {
    }

    @POST
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Track generateTrack(Track startingPoints, @Context HttpServletRequest request) {
        logger.debug("generate track -- starting points :{}", startingPoints);

        Utilities.setThreadLocalVariable(System.currentTimeMillis());

        return trackService.getTrack(startingPoints);
    }
}
