/*
 * Copyright © 2015 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.jobinesh.helidon.mp.demo.hr.ext.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom validation constraint annotation for demonstrating the API usage
 *
 * @author Jobinesh
 */
@Constraint(validatedBy = {ValidDepartmentValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidDepartment {

    //The message to return when the instance of DepartmentValidator
    //fails the validation.
    String message() default "{com.jobinesh.helidon.mp.demo.hr.validation.deptrule}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
