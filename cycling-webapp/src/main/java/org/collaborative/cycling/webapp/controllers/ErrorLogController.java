package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.ErrorService;
import org.collaborative.cycling.webapp.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;


@Path(ErrorLogController.MAPPING)
public class ErrorLogController {

  private static final Logger logger = LoggerFactory.getLogger(ErrorLogController.class);

  @Autowired
  ErrorService errorService;

  public static final String MAPPING = "/error";

  public ErrorLogController() {
  }

  @POST
  public void getDeviceError(String errorData, @Context HttpServletRequest request) throws IOException {
    HttpSession session = request.getSession(false);
    User user = session == null ? new User() : Utils.getUser(session);

    errorService.save(user.getEmail(), errorData);
  }
}

