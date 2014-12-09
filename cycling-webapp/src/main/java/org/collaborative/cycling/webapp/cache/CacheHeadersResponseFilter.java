package org.collaborative.cycling.webapp.cache;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CacheHeadersResponseFilter implements ContainerResponseFilter {

  public static final HashMap<String, String> NO_CACHE_HEADERS = new HashMap<String, String>() {{
    put("Cache-Control", "no-cache, no-store, must-revalidate");
    put("Pragma", "no-cache");
    put("Expires", "0");
  }};

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

    for (String headerName : NO_CACHE_HEADERS.keySet()) {
      responseContext.getHeaders().add(headerName, NO_CACHE_HEADERS.get(headerName));
    }
  }
}
