package com.self.e_commerce.Controller;

import com.self.e_commerce.Entity.UserInfo;
import com.self.e_commerce.Repository.UserInfoRepo;
import com.self.e_commerce.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserLoginController {
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/login")
    public String userLogin(@Valid @RequestBody UserInfo userInfo) throws Exception{
        return userInfoService.verify(userInfo);
    }
}
