package org.collaborative.cycling.webapp;


import org.collaborative.cycling.models.User;

import javax.servlet.http.HttpSession;

public class Utils {

    public static User getUser(HttpSession session) {
        return (User) session.getAttribute(User.ID);
    }

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(User.ID, user);
    }

}
