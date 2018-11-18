/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobinesh.hr.service.mp.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

/**
 * JAX-RS REST client generated for REST resource:HRService [hr]<br>
 *
 *
 * @author Jobinesh
 */
public class HRServiceJAXRSClient {

    private static final String BASE_URI = "http://localhost:8080";

    public HRServiceJAXRSClient() {

    }

    public void createDepartment() throws ClientErrorException {
        Client client = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI).path("hr");
        Form form = new Form();
        form.param("departmentId", "40");
        form.param("departmentName", "Revenue");
        webTarget.path("departments").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(form, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        client.close();
    }

    public static void main(String arg[]) {
        try {
            new HRServiceJAXRSClient().createDepartment();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
