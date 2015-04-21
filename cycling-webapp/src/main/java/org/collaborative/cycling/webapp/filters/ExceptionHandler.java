package org.collaborative.cycling.webapp.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        logger.error("An unknown error occurred", exception);
        return Response.noContent().status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).build();
    }
}
