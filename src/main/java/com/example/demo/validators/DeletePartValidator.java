package com.example.demo.validators;

import com.example.demo.domain.Part;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DeletePartValidator implements ConstraintValidator<ValidDeletePart, Part> {
    @Override
    public void initialize(ValidDeletePart constraintAnnotation) {
        // no-op
    }

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        // Skip validation if this is not a delete attempt
        // (i.e., if we're just saving or updating the part)
        if (part.getId() <= 0) return true;

        // Only fail validation if part has products (meaning it's associated)
        if (!part.getProducts().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Part cannot be deleted if used in a product.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
