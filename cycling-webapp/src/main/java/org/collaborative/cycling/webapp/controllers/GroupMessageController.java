package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.GroupMessage;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.GroupMessageService;
import org.collaborative.cycling.webapp.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(GroupMessageController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupMessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MAPPING = "/message/group";

    private final GroupMessageService groupMessageService;

    @Autowired
    public GroupMessageController(GroupMessageService groupMessageService) {
        this.groupMessageService = groupMessageService;
    }

    @GET
    @Path("/{groupId}")
    public Response getMessages(@PathParam("groupId") Long groupId,
                                @QueryParam("size") Integer size) {
        if (groupId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<GroupMessage> messageList = groupMessageService.getMessages(groupId, size);

        if (messageList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(messageList).build();
    }

    @POST
    @Path("/{groupId}")
    public Response addToChat(@PathParam("groupId") Long groupId,
                              String textReceived, @Context HttpServletRequest request) {
        if (groupId == null || textReceived == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        GroupMessage groupMessage = groupMessageService.addMessage(groupId, textReceived, currentUser.getId());

        if (groupMessage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(groupMessage).build();
    }
}
