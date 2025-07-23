<strong>** DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. ** </strong>

## How to Run

1. Clone the repo and open in your preferred IDE.
2. Ensure dependencies are installed (Spring Boot, Thymeleaf, Hibernate).
3. Run the application from `DemoApplication.java`.
4. Access the app at `http://localhost:8080/mainscreen`.


# WESTERN GOVERNORS UNIVERSITY
## D287 – JAVA FRAMEWORKS

### C. Customize HTML UI (Shop Name & Heading)
- File: `src/main/resources/templates/mainscreen.html`
- Lines: ~14, ~20
- Change: Updated page title and main heading
```html
<!-- before -->
<title>My Bicycle Shop</title>
<!-- after -->
<title>My Toy Shop</title>

<!-- before -->
<h1>Shop</h1>
<!-- after -->
<h1>Inventory</h1>
```

### D. Add “About” Page & Navigation
- File: `src/main/resources/templates/about.html`
- Change: Created About page with company description and back-link
```html
<title>About Us</title>
…
<a th:href="@{/mainscreen}">Back to Main Screen</a>
```

- File: `src/main/resources/templates/mainscreen.html`
- Lines: ~20
- Change: Added nav link to About page
```html
<nav class="nav-item">
    <a class="nav-link" th:href="@{/about}">About</a>
</nav>
```

- File: `src/main/java/com/example/demo/controllers/MainScreenController.java`
- Lines: ~13, ~51
- Change: Added mapping for `/about`

```java
import org.springframework.web.bind.annotation.RequestMapping;
```
@RequestMapping
}
```
```
### E. Seed Sample Inventory
- File: `src/main/java/com/example/demo/controllers/MainScreenController.java`
- Lines: ~13, ~51
- Change: Added mapping for `/about`

- File: `BootStrapData.java`
- Lines: ~32-87
- Change: Combined condition: combined all three counts (partRepository, outsourcedPartRepository, and productRepository) into one if so the seeder only runs when every table is empty.
  Used partRepository.saveAll(...) and outsourcedPartRepository.saveAll(...)
```
```
- File: `Product.java`
- Change: Created 5 products: (SuperBot, MechaKnight, RoboHero, UltraGuard, CyberNinja), Loaded only if productRepository.count() == 0
```
```
- File: Part.java
- Change: Added private int minInv and private int maxInv with @Column(nullable = false, ...) & Annotated class with @ValidInventory and supplied getters/setters.
```


### F. “Buy Now” Button & Purchase Flow

- File: `src/main/resources/templates/mainscreen.html`
- Line: ~92
- Change: Inserted Buy Now link
```html
<a th:href="@{/buyproduct(productID=${tempProduct.id})}" class="btn btn-success btn-sm">Buy Now</a>
```
- File: `src/main/java/com/example/demo/domain/Product.java`
- Line: 108
- Change: Added `buyProduct()` method
```java
public boolean buyProduct() {
    if (inv > 0) { inv--; return true; }
    return false;
}
```
- File: `src/main/java/com/example/demo/controllers/AddProductController.java
- Changes: Implemented the decrement of the inventory of product by one.
```java
    @GetMapping("/buyproduct")
    public String buyproduct(@RequestParam("productID") int theId, Model theModel) {
        // initialize productService bean through spring context
        ProductService productService = context.getBean(ProductServiceImpl.class);
        //creating a product object called product
        Product product = productService.findById(theId);
        //creating a variable to store the value of inventory
        int inv = product.getInv();

         //checking to see if inv value is 0
         if (inv == 0)
         {
             //returning failure.html page
             return "Failure";
         }
         else
         {
             //reduce inv value by 1 and set new value for inventory
             product.setInv(inv - 1);

             //save product object with new inventory value
             productService.save(product);

             //returning success.html page
             return "Success";
         }
    }
```
- Files Created:
    - `Failure.html`
    - `Success.html`


### G. Min/Max Inventory Fields and Validation
Modified the parts to track maximum and minimum inventory:

- File: `src/main/java/com/example/demo/domain/Part.java`  
- Lines: ~23, ~34-35, ~37-38
- Changes:
```java
@Min(value = 0, message = "Min Inventory value must be positive")
int minInv;

@Min(value = 0, message = "Max Inventory value must be positive")
int maxInv;
```

- File: `src/main/resources/templates/InhousePartForm.html`   
- File: `src/main/resources/templates/OutSousePartForm.html`
- Changes to both files:
Added these fields before the submit button:
```html
<p><input type="submit" value="Submit" /></p>
<p>Min Inventory<input type="text" path="minInv" th:field="*{minInv}" placeholder="Min Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('minInv')}" th:errors="*{minInv}">Min Inventory Error</p>

<p>Max Inventory<input type="text" path="maxInv" th:field="*{maxInv}" placeholder="Max Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('maxInv')}" th:errors="*{maxInv}">Max Inventory Error</p>

<div  th:if="${#fields.hasAnyErrors()}">
  <ul>
    <li th:each="err:${#fields.allErrors()}"th:text="${err}">
    </li>
  </ul>
</div>

<p><input type="submit" value="Submit" /></p>

```

- File:** `src/main/java/com/example/demo/config/BootStrapData.java`  
- Changes:
Update the seed data with values for `minInv` and `maxInv` when creating part objects:
lines: ~41-42, ~48-49, ~55-56, ~65-66 ~73-74

- File: `src/main/java/com/example/demo/validators/InventoryValidator.java`  
- Validation Logic: Lines ~14-24, ~26- end
```java
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
```

- File: `src/main/java/com/example/demo/domain/Part.java`  
- change: Added class-level Annotation: line ~19 java , @ValidInventory
``` 
```
- File: `src/main/resources/templates/mainscreen.html`
- Change: Added max and Min to table
- Lines: `41-42, ~51-52
```

```
### H. Inventory Validation Between Min and Max

Added validation to ensure a part's inventory stays within its defined minimum and maximum bounds.

- File: src/main/java/com/example/demo/domain/Part.java
- Line: ~35-38
- Changes:
```java
int minInv;
int maxInv;

@Min(value = 0, message = "Min Inventory value must be positive")
int minInv;

@Min(value = 0, message = "Max Inventory value must be positive")
int maxInv;
```

- File: src/main/java/com/example/demo/validators/InventoryValidator.java
- Line:~10-end
- Changes:
```java
@Override
public boolean isValid(Part part, ConstraintValidatorContext context) {
    boolean isValid = true;

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
```


### I. Unit Tests for Min/Max Inventory Fields

Added two unit tests to verify validation of minInv and maxInv fields.

- File: src/test/java/com/example/demo/domain/PartTest.java
- Lines:~166-end

- Changes:
  • Added validator setup using javax.validation API in setUp() method.
  • Added testMinInventoryCannotBeNegative(): verifies a negative minInv triggers a constraint violation.
  • Added testMaxInventoryCannotBeNegative(): verifies a negative maxInv triggers a constraint violation.

#### Part J: No un-used validators