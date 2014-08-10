package org.collaborative.cycling.webapp.filters;

import org.collaborative.cycling.models.User;
import org.collaborative.cycling.webapp.controllers.LoginController;
import org.collaborative.cycling.webapp.controllers.VersionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements javax.servlet.Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    // by default the filter will allow unauthenticated access for login controller calls
    private String excludedMappings[] = new String[]{
            LoginController.MAPPING,
            VersionController.MAPPING
    };

    @Override
    public void init(FilterConfig config) throws ServletException {
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
        Object user = null;

        if (session == null || (user = session.getAttribute(User.ID)) == null) {
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
