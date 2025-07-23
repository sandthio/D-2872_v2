package com.example.demo.validators;

import com.example.demo.domain.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryValidator implements ConstraintValidator<ValidInventory, Part> {

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        if (part == null) return true;

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (part.getInv() > part.getMaxInv()) {
            context.buildConstraintViolationWithTemplate(
                    "Solution: Fix your Inventory, it is greater than the max inventory"
            ).addConstraintViolation();
            isValid = false;
        }

        if (part.getInv() < part.getMinInv()) {
            context.buildConstraintViolationWithTemplate(
                    "Solution: Raise your Inventory, it is less than the min inventory"
            ).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
