/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jobinesh.helidon.mp.demo.hr.main;

import com.jobinesh.helidon.mp.demo.hr.ext.dynamic.DynamicFeatureRegister;
import com.jobinesh.helidon.mp.demo.hr.ext.filter.ContainerRequestLoggerFilter;
import com.jobinesh.helidon.mp.demo.hr.ext.filter.ContainerResponseCORSFilter;
import com.jobinesh.helidon.mp.demo.hr.services.DepartmentResource;
import com.jobinesh.helidon.mp.demo.hr.ext.validation.DepartmentNotFoundExceptionMapper;
import com.jobinesh.helidon.mp.demo.hr.ext.interceptor.CSVMessageBodyReader;
import com.jobinesh.helidon.mp.demo.hr.ext.interceptor.CSVMessageBodyWriter;
import com.jobinesh.helidon.mp.demo.hr.ext.validation.ValidDepartment;
import com.jobinesh.helidon.mp.demo.hr.ext.validation.ValidDepartmentValidator;
import com.jobinesh.helidon.mp.demo.hr.services.DepartmentAsynchResource;
import com.jobinesh.helidon.mp.demo.hr.services.DepartmentCachedResource;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.helidon.common.CollectionsHelper;

/**
 * Simple Application that produces a greeting message.
 */
@ApplicationScoped
@ApplicationPath("/")
public class HRServiceApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return CollectionsHelper.setOf(
                DepartmentResource.class,
                DepartmentAsynchResource.class,
                DepartmentCachedResource.class,
                ContainerResponseCORSFilter.class,
                DynamicFeatureRegister.class,
                CSVMessageBodyReader.class,
                CSVMessageBodyWriter.class,
                DepartmentNotFoundExceptionMapper.class,
                ValidDepartment.class,
                ValidDepartmentValidator.class
        );
    }
}
