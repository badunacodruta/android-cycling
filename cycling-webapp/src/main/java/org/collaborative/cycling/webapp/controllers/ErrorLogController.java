package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.ErrorService;
import org.collaborative.cycling.webapp.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.IOException;


@Path(ErrorLogController.MAPPING)
public class ErrorLogController {

    public static final String MAPPING = "/error";

    private final ErrorService errorService;

    @Autowired
    public ErrorLogController(ErrorService errorService) {
        this.errorService = errorService;
    }

    @POST
    public void getDeviceError(String errorData, @Context HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? new User() : Utils.getUser(session);

        errorService.save(user.getId(), errorData);
    }
}

