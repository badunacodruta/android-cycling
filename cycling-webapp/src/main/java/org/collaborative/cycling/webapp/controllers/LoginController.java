package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.UserService;
import org.collaborative.cycling.webapp.Utils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(LoginController.MAPPING)
public class LoginController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    public static final String MAPPING = "/login";
    public static final String MAPPING_VERSION = "/";

    public LoginController() {
    }

    @POST
    @Path(MAPPING_VERSION)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user, @Context HttpServletRequest request) {
        logger.debug("login -- email:{}", user.getEmail());

        User loggedUser = userService.login(user);

        HttpSession session = request.getSession(true);
        Utils.setUser(session, loggedUser);

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path(MAPPING_VERSION)
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();

        return Response.status(Response.Status.OK).build();
    }

//    TODO: investigate how to redirect all pages to login page if not logged user
}
