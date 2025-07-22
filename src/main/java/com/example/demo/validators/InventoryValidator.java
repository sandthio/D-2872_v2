// src/main/java/com/example/demo/validators/InventoryValidator.java
package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryValidator implements ConstraintValidator<ValidInventory, Part> {
    @Autowired
    private ApplicationContext context;

    public static  ApplicationContext myContext;

    @Override
    public void initialize(ValidInventory constraintAnnotation) {
        //ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {

        //create code to make sure that inventory is between min and max value
        if (part.getInv() > part.getMaxInv()) {
            //display error message when inventory is greater than the max inventory
            constraintValidatorContext.buildConstraintViolationWithTemplate("Solution: Fix your Inventory, it is greater than the max inventory").addConstraintViolation();
            return false;
        } else if (part.getInv() < part.getMinInv()) {
            //display error message when inventory is less than the min inventory
            constraintValidatorContext.buildConstraintViolationWithTemplate("Solution: Raise your Inventory, it is less than the min inventory").addConstraintViolation();
            return false;
        } else {
            return true;
        }

    }
}
