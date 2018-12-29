/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.hr.services;

import com.jobinesh.helidon.mp.demo.hr.entity.Department;
import com.jobinesh.helidon.mp.demo.hr.entity.PersistenceManager;
import com.jobinesh.helidon.mp.demo.hr.ext.dynamic.RequestLogger;
import com.jobinesh.helidon.mp.demo.hr.ext.validation.DeprtmentNotFoundBusinessException;
import com.jobinesh.helidon.mp.demo.hr.ext.validation.ValidDepartment;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jobinesh
 */
@Path("/departments")
@RequestScoped
public class DepartmentResource {

    private static final Logger logger = Logger.getLogger(DepartmentResource.class.getName());

    @GET
    @Path("batch")
    @Produces("application/csv")
    @RequestLogger
    public List<Department> findAllDepartmnetsInBatch() {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
        List<Department> departments = em.createQuery(cq).getResultList();
        logger.log(Level.INFO, "departments:" + departments);
        return departments;
    }

    @POST
    @Path("batch")
    @Consumes("application/csv")
    public void createInBatch(List<Department> entities) {

        logger.log(Level.INFO, entities.toString());
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();

        for (Department entity : entities) {
            em.persist(entity);
        }

        commitTxn(em);
    }

    @PUT
    @Path("batch")
    @Consumes("application/csv")
    public void updateInBatch(List<Department> entities) {

        logger.log(Level.INFO, entities.toString());
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();

        for (Department entity : entities) {
            em.merge(entity);
        }
        commitTxn(em);
    }

    /* public DepartmentResource(@Named("example") final DataSource dataSource) {
        super();
        this.dataSource = Objects.requireNonNull(dataSource);
    }*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> getAllDepartments() {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Department.class));
        List<Department> departments = em.createQuery(cq).getResultList();
        logger.log(Level.INFO, "departments:" + departments);
        return departments;
    }

    /**
     * Creates Department entity
     *
     * @param entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createDepartment(@ValidDepartment Department entity) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        commitTxn(em);
    }

    /**
     * Get total departments count
     *
     * @return
     */
    @GET
    @Path("count")
    @Produces("text/plain")
    public int countREST() {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Department> rt = cq.from(Department.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

    /**
     * Creates a department. This method demonstrates @FormParam
     *
     * @param departmentId
     * @param departmentName
     */
    @POST
    @Path("form")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createDepartmnet(
            @FormParam("departmentId") short departmentId,
            @FormParam("departmentName") String departmentName) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        Department entity = new Department();
        entity.setDepartmentId(departmentId);
        entity.setDepartmentName(departmentName);
        em.getTransaction().begin();
        em.persist(entity);
        commitTxn(em);
    }

    /**
     * Creates a department This method demonstrates @BeanParam
     *
     * @param deptBean
     */
    @POST
    @Path("form/bean")
    public void createDepartmnet(@BeanParam DepartmentFormBean deptBean) {
        createDepartmnet(deptBean.getDepartmentId(),
                deptBean.getDepartmentName());
    }

    /**
     * Modifies department
     *
     * @param id
     * @param entity
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editDepartment(@PathParam("id") Short id, Department entity) {
        logger.log(Level.INFO, "Departments: " + entity.toString());

        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        commitTxn(em);
    }

    /**
     * Deletes a department
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void removeDepartment(@PathParam("id") Short id) throws DeprtmentNotFoundBusinessException {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        Department entity = em.find(Department.class, id);
        if (entity == null) {
            throw new DeprtmentNotFoundBusinessException("Department is missing in store");

        }
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        commitTxn(em);

    }

    /**
     * Finds a department by name
     *
     * @param name
     * @return
     */
    @GET
    @Path("{name: [a-zA-Z][a-zA-Z_0-9]}")
    @Produces(MediaType.APPLICATION_JSON)
    public Department findDepartmentByName(@PathParam("name") String name) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        Query queryDepartmentsByName = em.createNamedQuery("Departments.findByDepartmentName");
        queryDepartmentsByName.setParameter("departmentName", name);
        Department department = (Department) queryDepartmentsByName.getSingleResult();
        return department;
    }

    /**
     * Finds a department by id
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Department findDepartment(@PathParam("id") Short id) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        return em.find(Department.class, id);
    }

    /**
     * Returns list of departments
     *
     * @param name
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("query")
    public List<Department> findAllDepartmentsWithQueryParam(@QueryParam("name") String name) {
        //Find all departments from the data store
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        logger.log(Level.INFO, "findAllDepartmentsWithQueryParam name:" + name);
        Query queryDepartmentsByName = em.createNamedQuery("Departments.findByDepartmentName");
        queryDepartmentsByName.setParameter("departmentName", name);
        List<Department> departments = queryDepartmentsByName.getResultList();
        logger.log(Level.INFO, departments.toString());
        return departments;
    }

    /**
     * Returns list of departments. This method demonstrates @MatrixParam
     *
     * @param name
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("matrix")
    public List<Department> findAllDepartmentsWithMatrixParam(@MatrixParam("name") String name) {
        //Find all departments from the data store
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        Query queryDepartmentsByName = em.createNamedQuery("Departments.findByDepartmentName");
        queryDepartmentsByName.setParameter("departmentName", "%" + name + "%");
        List<Department> departments = queryDepartmentsByName.getResultList();

        logger.log(Level.INFO, departments.toString());

        return departments;
    }

    private JsonObject createResponse() {
        //query();
        try {
            EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Department.class));
            List<Department> departments = em.createQuery(cq).getResultList();
            logger.log(Level.INFO, "departments:" + departments);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return Json.createObjectBuilder()
                .add("message", "Helloooo")
                .build();
    }

    private void commitTxn(EntityManager em) {
        em.getTransaction().commit();
        em.close();

    }

}
