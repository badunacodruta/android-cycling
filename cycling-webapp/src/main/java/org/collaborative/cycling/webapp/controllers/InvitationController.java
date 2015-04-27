package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.webapp.controllers.dto.GroupDetails;
import org.collaborative.cycling.webapp.controllers.dto.PEUserGroups;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;

@Path(InvitationController.MAPPING)
public class InvitationController {

    public static final String MAPPING = "/invitation";

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInvitation(@Context HttpServletRequest request) throws IOException {
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PEUserGroups get(@Context HttpServletRequest request) throws IOException {

        final GroupDetails group1 = new GroupDetails() {{
            this.id = 1;
            this.name = "Group 1";
        }};

        final GroupDetails group2 = new GroupDetails() {{
            this.id = 2;
            this.name = "Group 2";
        }};
        final GroupDetails group3 = new GroupDetails() {{
            this.id = 3;
            this.name = "Group 3";
        }};


        PEUserGroups mine = new PEUserGroups() {{
            this.groups = Arrays.asList(group1);
        }};

        PEUserGroups all = new PEUserGroups() {{
            this.groups = Arrays.asList(group1, group2, group3);
        }};

        return all;
    }


}

