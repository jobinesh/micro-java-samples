/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.helidon.mp.demo.hr.services;

import javax.ws.rs.FormParam;

/**
 * DepartmentFormBean used as @BeanParam in DepartmentService class
 *
 * @author Jobinesh
 */
public class DepartmentFormBean {

    @FormParam("departmentId")
    private short departmentId;

    @FormParam("departmentName")
    private String departmentName;

    /**
     * @return the departmentId
     */
    public short getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(short departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
