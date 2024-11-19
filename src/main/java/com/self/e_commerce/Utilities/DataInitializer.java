package com.self.e_commerce.Utilities;

import com.self.e_commerce.Entity.Role;
import com.self.e_commerce.Entity.UserInfo;
import com.self.e_commerce.Repository.RoleInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleInfoRepo roleInfoRepo;

    @Override
    public void run(String... args) throws Exception {
        if (roleInfoRepo.findByName("ROLE_USER").isEmpty()) {
            roleInfoRepo.save(new Role("ROLE_USER"));
        }
        if (roleInfoRepo.findByName("ROLE_ADMIN").isEmpty()) {
            roleInfoRepo.save(new Role("ROLE_ADMIN"));
        }
    }
}
