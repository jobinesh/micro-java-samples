/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.hr.ext.filter;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * An example for ContainerResponseFilter
 * This is Cross-origin resource sharing (CORS) filter which enables cross domain access
 * @author Jobinesh
 */
@Provider
public class ContainerResponseCORSFilter implements ContainerResponseFilter {

    private static final Logger logger = Logger.getLogger(ContainerResponseCORSFilter.class.getName());

    @Override
    public void filter(final ContainerRequestContext requestContext,
            final ContainerResponseContext cres) throws IOException {
        logger.log(Level.INFO, "ContainerRequestContext:" + requestContext);

        cres.getHeaders().add("Access-Control-Allow-Origin", "*");
        cres.getHeaders().add("Access-Control-Allow-Headers", "*");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        // cres.getHeaders().add("Access-Control-Max-Age", "1209600");
    }

}

