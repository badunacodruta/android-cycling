package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.Group;
import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.GroupService;
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

@Path(GroupController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MAPPING = "/group";

    private final GroupService groupService;

    //TODO error handling
    //TODO javax.validation on the models and params

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @POST
    public Response create(Group group, @Context HttpServletRequest request) {
        if (group.getId() != null || group.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (groupService.exists(group.getName())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        User currentUser = Utils.getUser(request.getSession(false));
        group = groupService.save(group, currentUser.getId());

        return Response.status(Response.Status.CREATED).entity(group).build();
    }

    @GET
    public Response get(@QueryParam("filter") String filter,
                        @Context HttpServletRequest request) {
        List<Group> groupList = groupService.getFiltered(filter);

        if (groupList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(groupList).build();
    }

    @GET
    @Path("/joined")
    public Response getForCurrentUser(@Context HttpServletRequest request) {
        User currentUser = Utils.getUser(request.getSession(false));
        Long userId = currentUser.getId();

        List<Group> groupList = groupService.getForUser(userId);

        if (groupList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(groupList).build();
    }

    @GET
    @Path("/{groupId}/members")
    public Response getMembers(@PathParam("groupId") Long groupId) {
        if (groupId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<User> userList = groupService.getUsers(groupId);

        if (userList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(userList).build();
    }
}
