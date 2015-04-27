package org.collaborative.cycling.webapp.controllers;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.webapp.Utils;
import org.collaborative.cycling.webapp.controllers.dto.AllMessages;
import org.collaborative.cycling.webapp.controllers.dto.GroupDetails;
import org.collaborative.cycling.webapp.controllers.dto.Message;
import org.collaborative.cycling.webapp.controllers.dto.PEUserGroups;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path(GroupsController.MAPPING)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupsController {

    public static final String MAPPING = "/group";
    private static AllMessages history = new AllMessages() {{
        this.msgs = new ArrayList<>();
        msgs.add(new Message() {{
            text = "bla";
            userInfo = new User() {{
                setEmail("bla@bla.com");
                setImageUrl("http://static.cinemagia.ro/img/resize/db/actor/15/83/67/gigi-becali-149264l-poza.jpg");
                firstName = "bla";
                lastName = "bla";
            }};
        }});
    }};

    @POST
    public Response save(String errorData, @Context HttpServletRequest request) throws IOException {
        return Response.status(Response.Status.ACCEPTED).build();
    }



    @GET
    public PEUserGroups get(@QueryParam("state") String state,
                            @QueryParam("filter") String filter,
                            @Context HttpServletRequest request) throws IOException {

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

        if (state != null && state.equals("joined")) {
            return mine;
        }


        all.groups = all.groups.subList(filter.length(), all.groups.size());

        return all;
    }

    @DELETE
    @Path("/{groupId}")
    public Response delete() throws IOException {
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/{groupId}/chat")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public AllMessages getHistory(@Context HttpServletRequest request) {
        return history;
    }

    @POST
    @Path("/{groupId}/chat")

    public Response getHistory(final String textReceived, @Context HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        final User user = Utils.getUser(session);

        history.msgs.add(new Message() {{
            this.text = textReceived;
            this.userInfo = user;
        }});

        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/{groupId}/members")
    public List<User> getMembers() {
        return Arrays.asList(
                new User("213@bikeroute.com", "http://static.cinemagia.ro/img/resize/db/actor/15/83/67/gigi-becali-149264l-poza.jpg", "Gigi", "Becali"),
                new User("213@gigi.com", "http://stadasdasdsatic.cinemagia.ro/img/resize/db/actor/15/83/67/gigi-becali-149264l-poza.jpg", "Gigi", "Becali")
        );
    }


    @POST
    @Path("/{groupId}/memeber/{email}")
    public Response sendInvitationToUserForGroup() {
        return Response.status(Response.Status.ACCEPTED).build();
    }


    @GET
    @Path("/{groupId}/request")
    public List<User> getRequests() {
        return Arrays.asList(
                new User("213@bikeroute.com", "http://static.cinemagia.ro/img/resize/db/actor/15/83/67/gigi-becali-149264l-poza.jpg", "Gigi", "Becali"),
                new User("213@gigi.com", "http://stadasdasdsatic.cinemagia.ro/img/resize/db/actor/15/83/67/gigi-becali-149264l-poza.jpg", "Gigi", "Becali")
        );
    }

    @POST
    @Path("/{groupId}/request")
    public Response updateRequests() {
        return Response.status(Response.Status.ACCEPTED).build();
    }

}


