package org.collaborative.cycling.webapp.controllers.wrapper.group;

import org.collaborative.cycling.models.Group;
import org.collaborative.cycling.webapp.controllers.GroupController;
import org.collaborative.cycling.webapp.controllers.GroupRequestsController;
import org.collaborative.cycling.webapp.controllers.wrapper.group.dto.GroupWithUsers;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(GroupWrapperController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupWrapperController {

    public static final String MAPPING = "/wrapper/group";

    @Autowired
    GroupController groupController;

    @Autowired
    GroupRequestsController groupRequestsController;

    public GroupWrapperController() {
    }


    @POST
    public Response createGroupWithUsers(GroupWithUsers groupWithUsers,
                                         @Context HttpServletRequest request) {

        Response response = groupController.create(new Group(null, groupWithUsers.name), request);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            return response;
        }

        Group group = (Group) response.getEntity();

        for (String email : groupWithUsers.users) {
            groupRequestsController.groupInviteUser(group.getId(), email, request);
        }

        return Response.status(Response.Status.OK).build();
    }


}
