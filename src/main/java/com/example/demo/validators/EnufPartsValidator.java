package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class EnufPartsValidator implements ConstraintValidator<ValidEnufParts, Product> {
    @Autowired
    private ApplicationContext context;

    public static ApplicationContext myContext;

    @Override
    public void initialize(ValidEnufParts constraintAnnotation) {
        // No setup needed
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext contextValidator) {
        if (context != null) myContext = context;

        Set<Part> parts = product.getParts();
        if (parts == null || parts.isEmpty()) return true;

        int newInv = product.getInv();
        int productId = (int) product.getId();
        int delta = newInv;

        if (productId != 0) {
            try {
                ProductServiceImpl productService = myContext.getBean(ProductServiceImpl.class);
                Product existing = productService.findById(productId);
                if (existing != null) {
                    delta = newInv - existing.getInv();
                }
            } catch (Exception e) {
                return true;
            }
        }

        if (delta > 0) {
            int deductionPerPart = delta / parts.size();
            for (Part p : parts) {
                int projected = p.getInv() - deductionPerPart;
                if (projected < p.getMinInv()) {
                    contextValidator.disableDefaultConstraintViolation();
                    contextValidator
                            .buildConstraintViolationWithTemplate("Part '" + p.getName() +
                                    "' would fall below its minimum inventory of " + p.getMinInv())
                            .addConstraintViolation();
                    return false;
                }
            }
        }

        return true;
    }
}
