package com.self.e_commerce.Service;

import com.self.e_commerce.Entity.UserInfo;
import com.self.e_commerce.Exception.ResourceNotFoundException;
import com.self.e_commerce.Repository.UserInfoRepo;
import com.self.e_commerce.Security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    public String verify(UserInfo userInfo) {
        try {
            System.out.println(userInfo+"1");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userInfo.getUsername(),
                            userInfo.getPassword_hash()));
            System.out.println(userInfo+"2");
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(userInfo.getUsername());
            } else {
                throw new BadCredentialsException("Authentication failed");
            }
        } catch (BadCredentialsException e) {
            log.error("Authentication failed: {}"+ e.getMessage());
            throw e; // Re-throw the exception
        } catch (Exception e) {
            log.error("Authentication failed", e);
            throw e; // Re-throw the exception
        }
    }

    public UserInfo findUserIdByUsername(String username) throws Exception{
        UserInfo userId;
        Optional<UserInfo> user = Optional.ofNullable(userInfoRepo.findByUsername(username));
        if (user.isPresent()) {
            userId = user.get();
        } else {
            throw new ResourceNotFoundException("User not found with id: " + username);
        }
        return userId;
    }
}
