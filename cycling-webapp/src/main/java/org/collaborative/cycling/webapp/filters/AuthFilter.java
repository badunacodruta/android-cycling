package org.collaborative.cycling.webapp.filters;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.services.UserService;
import org.collaborative.cycling.webapp.Utils;
import org.collaborative.cycling.webapp.controllers.ErrorLogController;
import org.collaborative.cycling.webapp.controllers.LoginController;
import org.collaborative.cycling.webapp.controllers.UpdatesController;
import org.collaborative.cycling.webapp.controllers.VersionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements javax.servlet.Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private UserService userService;


    // by default the filter will allow unauthenticated access for login controller calls
    private String excludedMappings[] = new String[]{
            LoginController.MAPPING,
            ErrorLogController.MAPPING,
            UpdatesController.MAPPING,
            VersionController.MAPPING
    };

    @Override
    public void init(FilterConfig config) throws ServletException {
        userService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext())
                .getBean(UserService.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // These APIs are excluded from auth checks
        for (String excludedMapping : this.excludedMappings) {
            if (request.getRequestURI().contains(excludedMapping)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = request.getSession(false);
        if (session == null || Utils.getUser(session) == null) {
            logger.warn("no session for {} for session = {}", request.getRequestURI(), session);

            String NO_IMAGE_URL = "http://kamomecorporation.com/image/default_main_image.jpg";

            String email = request.getHeader("email");
            User user = userService.login(new User(null, email, NO_IMAGE_URL));

            session = request.getSession(true);
            Utils.setUser(session, user);
        }

        session = request.getSession(false);
        if (session == null || Utils.getUser(session) == null) {
            logger.warn("deny access to {} for session = {}", request.getRequestURI(), session);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }
}
