package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.services.ActivityService;
import org.collaborative.cycling.services.UserActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;


@Path(ErrorLogController.MAPPING)
public class ErrorLogController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogController.class);

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private ActivityService activityService;

    public static final String MAPPING = "/error";

    public ErrorLogController() {
    }

    @POST
    public void getDeviceError(String errorData, @Context HttpServletRequest request) throws IOException {
        logger.debug(errorData);
        System.out.println(errorData);
    }
}

