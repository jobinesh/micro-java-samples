/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.hr.services;

import com.jobinesh.helidon.mp.demo.hr.entity.Department;
import com.jobinesh.helidon.mp.demo.hr.entity.PersistenceManager;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.enterprise.context.RequestScoped;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jobinesh
 */
@Path("/asynch/departments")
@RequestScoped
public class DepartmentAsynchResource {

    private static final Logger logger = Logger.getLogger(DepartmentAsynchResource.class.getName());
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @GET
    @Path("departments")
    @Produces(MediaType.APPLICATION_JSON)
    public void findAllDepartments(@Suspended AsyncResponse asyncResponse) {

        asyncResponse.setTimeoutHandler(new DeptQueryTimeoutHandler(asyncResponse));
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);
        executorService.execute(new LongRunningDeptQuery(asyncResponse));
    }

    class LongRunningDeptQuery implements Runnable {

         EntityManager entityManagerLocal  = PersistenceManager.INSTANCE.getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = entityManagerLocal.getCriteriaBuilder().createQuery();

        AsyncResponse asyncResponse;

        LongRunningDeptQuery(final AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        public void run() {

            CriteriaQuery cq = entityManagerLocal.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Department.class));
            List<Department> depts = entityManagerLocal.createQuery(cq).getResultList();
            GenericEntity<List<Department>> entity
                    = new GenericEntity<List<Department>>(depts) {
            };
            asyncResponse.resume(Response.ok().entity(entity).build());
        }

    }

    class DeptQueryTimeoutHandler implements TimeoutHandler {

        AsyncResponse asyncResponse;

        DeptQueryTimeoutHandler(final AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
        }

        @Override
        public void handleTimeout(AsyncResponse asyncResponse) {
            asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("Operation time out.").build());
        }

    }
}
