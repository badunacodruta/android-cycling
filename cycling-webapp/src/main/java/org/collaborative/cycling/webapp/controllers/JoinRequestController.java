package org.collaborative.cycling.webapp.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.collaborative.cycling.models.*;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


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

//    TODO: maybe add pagination for join requests
    @GET
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JoinRequest> getJoinRequests(@QueryParam("joinRequestType") JoinRequestType joinRequestType,
                                             @Context HttpServletRequest request) {
        logger.debug("get join requests - type:{}", joinRequestType);

        HttpSession session = request.getSession(true);
        User user = Utils.getUser(session);


//        TODO: remove mock
//        ============= mock =============

        List<JoinRequest> joinRequestList = new ArrayList<>();
        joinRequestList.add(new JoinRequest(1, user, "1", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(2, user, "2", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(3, user, "3", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "4", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "5", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "6", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "7", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "8", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "9", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "10", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "11", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "12", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "13", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "14", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "15", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "16", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "17", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "18", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "19", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "20", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "21", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "22", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "23", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "24", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "25", JoinedStatus.PENDING, new Date()));
        joinRequestList.add(new JoinRequest(4, user, "26", JoinedStatus.PENDING, new Date()));

        return joinRequestList;

//        ============= mock =============


//        switch (joinRequestType) {
//            case SENT:
//                return userActivityService.getPendingJoinRequestsCreatedByUser(user);
//            case RECEIVED:
//                return userActivityService.getPendingJoinRequestsForUser(user);
//        }
//
//        return null;
    }
}

