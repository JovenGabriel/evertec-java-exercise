package com.evertec.ecommerce.utils;

import com.evertec.ecommerce.entities.Product;
import com.evertec.ecommerce.entities.User;
import com.evertec.ecommerce.repositories.UserRepository;
import com.evertec.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Checks the existence of an admin user by email and creates a new admin user
     * with predefined credentials and properties if not present in the system storage.
     *
     * @param args command line arguments passed to the execution context; not used in this implementation
     */
    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = User.builder()
                    .email("admin@admin.cl")
                    .password(passwordEncoder.encode("Admin123"))
                    .build();
            userRepository.save(admin);
        }

        // Add predefined computing products
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                    Product.builder().name("Laptop").description("High-performance laptop").price(1200.00).build(),
                    Product.builder().name("Desktop PC").description("Powerful gaming desktop").price(1500.00).build(),
                    Product.builder().name("Monitor").description("4K UHD Monitor").price(300.00).build(),
                    Product.builder().name("Mechanical Keyboard").description("RGB backlit keyboard").price(100.00).build(),
                    Product.builder().name("Mouse").description("Wireless ergonomic mouse").price(50.00).build(),
                    Product.builder().name("External Hard Drive").description("1TB USB-C Hard Drive").price(120.00).build()
            ));
        }
    }
}
