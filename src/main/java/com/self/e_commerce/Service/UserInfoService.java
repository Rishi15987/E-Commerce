package com.self.e_commerce.Service;

import com.self.e_commerce.Entity.UserInfo;
import com.self.e_commerce.Exception.ResourceNotFoundException;
import com.self.e_commerce.Repository.UserInfoRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepo userInfoRepo;
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
