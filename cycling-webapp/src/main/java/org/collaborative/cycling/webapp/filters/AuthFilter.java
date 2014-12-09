package org.collaborative.cycling.webapp.filters;

import org.collaborative.cycling.webapp.Utils;
import org.collaborative.cycling.webapp.controllers.LoginController;
import org.collaborative.cycling.webapp.controllers.VersionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

      Enumeration<String> headerNames = request.getHeaderNames();
      while(headerNames.hasMoreElements()) {
        String name = headerNames.nextElement();
        logger.debug("{}={}", name, request.getHeader(name));
      }


      HttpSession session = request.getSession(false);

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
