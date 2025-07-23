package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DeletePartValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeletePart {
    String message() default "Part cannot be deleted if used in a product.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
