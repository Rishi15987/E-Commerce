package com.self.e_commerce.Controller;

import com.self.e_commerce.Entity.Role;
import com.self.e_commerce.Entity.UserInfo;
import com.self.e_commerce.Repository.RoleInfoRepo;
import com.self.e_commerce.Repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/user")
@RestController
public class UserAuthContoller {

    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private RoleInfoRepo roleInfoRepo;
    @PatchMapping("/promoteToAdmin/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> promoteUserToAdmin(@PathVariable String username) {
        UserInfo user= userInfoRepo.findByUsername(username);
        if(user==null){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }

        Role adminRole = roleInfoRepo.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
        if (!user.getRoles().contains(adminRole)) {
            user.getRoles().add(adminRole);
        }
        userInfoRepo.save(user);

        return ResponseEntity.ok("User promoted to admin");
    }

}
