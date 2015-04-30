package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.UserService;
import org.collaborative.cycling.webapp.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;

@Path(LoginController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MAPPING = "/login";

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response login(User user, @Context HttpServletRequest request) throws NoSuchAlgorithmException {
        if (user.getEmail() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        logger.debug("login -- email:{}", user.getEmail());

        User loggedUser = userService.login(user);

        HttpSession session = request.getSession(true);
        Utils.setUser(session, loggedUser);

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();

        return Response.status(Response.Status.OK).build();
    }

//    TODO: investigate how to redirect all pages to login page if not logged user
//    TODO: add validation annotations for the models and set up the validation for controllers
}
