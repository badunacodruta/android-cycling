package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.JoinRequest;
import org.collaborative.cycling.models.JoinRequestType;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(JoinRequestController.MAPPING)
public class JoinRequestController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JoinRequestController.class);

    @Autowired
    private UserActivityService userActivityService;

    public static final String MAPPING = "/requests";
    public static final String MAPPING_VERSION = "/";

    public JoinRequestController() {
    }

    @POST
    @Path(MAPPING_VERSION + "accept")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean acceptJoinRequest(long joinRequestId, @Context HttpServletRequest request) {
        logger.debug("accept join request -- joinRequestId:{}", joinRequestId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return userActivityService.acceptJoinRequest(user, joinRequestId);
    }

    @POST
    @Path(MAPPING_VERSION + "decline")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean declineJoinRequest(long joinRequestId, @Context HttpServletRequest request) {
        logger.debug("decline join request -- joinRequestId:{}", joinRequestId);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        return userActivityService.declineJoinRequest(user, joinRequestId);
    }

    @GET
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JoinRequest> getJoinRequests(@QueryParam("joinRequestType") JoinRequestType joinRequestType,
                                             @Context HttpServletRequest request) {
        logger.debug("get join requests - type:{}", joinRequestType);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);

        switch (joinRequestType) {
            case SENT:
                return userActivityService.getJoinRequestsCreatedByUser(user);
            case RECEIVED:
                return userActivityService.getJoinRequestsForUser(user);
        }

        return null;
    }
}
