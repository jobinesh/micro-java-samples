/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * This filter will be registered dynamically from DynamicFeatureRegister
 *
 * @author Jobinesh
 */

public class ContainerRequestLoggerFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(ContainerRequestLoggerFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.log(Level.INFO, "----Requested---" + requestContext.getUriInfo().toString());
    }

}