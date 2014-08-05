package org.collaborative.cycling.webapp.controllers;


import org.collaborative.cycling.User;

import javax.servlet.http.HttpSession;

public class Utils {

    static User getUser(HttpSession session) {
        return (User) session.getAttribute(User.ID);
    }
    static void setUser(HttpSession session, User user) {
        session.setAttribute(User.ID, user);
    }

}
