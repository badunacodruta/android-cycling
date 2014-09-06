package org.collaborative.cycling.webapp.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.collaborative.cycling.models.Coordinates;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Path(TrackingController.MAPPING)
public class TrackingController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TrackingController.class);

    @Autowired
    private UserActivityService userActivityService;

    public static final String MAPPING = "/tracking";
    public static final String MAPPING_VERSION = "/";

    public TrackingController() {
    }

    //    TODO: update path and params
    @POST
    @Path(MAPPING_VERSION + "position/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updatePosition(@PathParam("activityId") long activityId,
                                  Coordinates coordinates,
                                  @Context HttpServletRequest request) {
        logger.debug("update position -- activityId:{}, position:{}", activityId, coordinates);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return userActivityService.updatePosition(user, activityId, coordinates);
    }

    static int index = 0;

    @GET
    @Path("/{lat}/{lng}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<UserMapDetails> getJoinedRides(
        @PathParam("lat") double lat,
        @PathParam("lng") double lng,
        @Context HttpServletRequest request) {

        //TODO: this is the list of rides: MY rides + All rides I`ve clicked join ( accepted or not )


        index ++;
        return Arrays.asList(
            new UserMapDetails("email1", 10 + index, 10, 1000, 1000),
            new UserMapDetails("email1", 11 + index, 10, 1000, 1000),
            new UserMapDetails("email1", 12 + index, 10, 1000, 1000),
            new UserMapDetails("email1", 13 + index, 10, 1000, 1000),
            new UserMapDetails("email1", 14 + index, 10, 1000, 1000)
        );
    }
    @GET
    @Path("/finish")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void finish(){


        //TODO: ignore any new tracking calls ? or do nothing

    }


    private class UserMapDetails {

        public String email;
        public double lat;
        public double lng;
        public long distanceInMeters;
        public long distanceInTime;

        private UserMapDetails() {
        }

        private UserMapDetails(String email, double lat, double lng, long distanceInMeters, long distanceInTime) {
            this.email = email;
            this.lat = lat;
            this.lng = lng;
            this.distanceInMeters = distanceInMeters;
            this.distanceInTime = distanceInTime;
        }
    }
}
