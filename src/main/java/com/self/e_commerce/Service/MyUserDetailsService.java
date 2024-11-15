package com.self.e_commerce.Service;

import com.self.e_commerce.Entity.UserInfo;
import com.self.e_commerce.Entity.UserPrincipal;
import com.self.e_commerce.Repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo= userInfoRepo.findByUsername(username);
        if(userInfo==null){
            System.out.println("Searching for user: " + username);
            System.out.println("User not found in database");
            throw new UsernameNotFoundException("User Not Found");
        }
        System.out.println("Searching for user: " + username);
        return new UserPrincipal(userInfo);
    }
}
