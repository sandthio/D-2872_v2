package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository,
                         ProductRepository productRepository,
                         OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository = outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // seed only if no parts AND no products exist
        if (partRepository.count() == 0
                && outsourcedPartRepository.count() == 0
                && productRepository.count() == 0) {

            // in‑house parts (3)
            InhousePart leftArm = new InhousePart();
            leftArm.setName("Left Arm");
            leftArm.setPrice(5.00);
            leftArm.setInv(20);
            leftArm.setMinInv(5);
            leftArm.setMaxInv(25);

            InhousePart rightArm = new InhousePart();
            rightArm.setName("Right Arm");
            rightArm.setPrice(5.00);
            rightArm.setInv(20);
            rightArm.setMinInv(5);
            rightArm.setMaxInv(25);

            InhousePart torso = new InhousePart();
            torso.setName("Torso");
            torso.setPrice(8.00);
            torso.setInv(20);
            torso.setMinInv(5);
            torso.setMaxInv(25);

            partRepository.saveAll(List.of(leftArm, rightArm, torso));

            // outsourced parts (2)
            OutsourcedPart helmet = new OutsourcedPart();
            helmet.setName("Helmet");
            helmet.setPrice(7.00);
            helmet.setInv(10);
            helmet.setMinInv(5);
            helmet.setMaxInv(15);
            helmet.setCompanyName("ToyTech Ltd.");

            OutsourcedPart cape = new OutsourcedPart();
            cape.setName("Cape");
            cape.setPrice(4.00);
            cape.setInv(10);
            cape.setMinInv(5);
            cape.setMaxInv(15);
            cape.setCompanyName("SuperThreads Inc.");

            outsourcedPartRepository.saveAll(List.of(helmet, cape));

            // products (5)
            productRepository.saveAll(List.of(
                    new Product("SuperBot",    29.99, 10),
                    new Product("MechaKnight", 34.99,  8),
                    new Product("RoboHero",    27.50, 12),
                    new Product("UltraGuard",  32.00,  9),
                    new Product("CyberNinja",  30.00, 10)
            ));
        }

        System.out.println("Bootstrap complete");
        System.out.println("Parts:    " + partRepository.count());
        System.out.println("Products: " + productRepository.count());
    }
}
