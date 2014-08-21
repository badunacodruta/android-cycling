package org.collaborative.cycling.webapp.controllers;

import java.util.Arrays;
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

import org.collaborative.cycling.models.JoinRequest;
import org.collaborative.cycling.models.JoinRequestType;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Path(TrackingController.MAPPING)
public class TrackingController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TrackingController.class);

    @Autowired
    private UserActivityService userActivityService;

    public static final String MAPPING = "/requests";
    public static final String MAPPING_VERSION = "/";

    public TrackingController() {
    }


// return list of:
//    public String email;
//    public double lat;
//    public double lng;
//    public long distanceInMeters;
//    public long distanceInTime;


}

