package com.self.e_commerce.Controller;

import com.self.e_commerce.Repository.UserInfoRepo;
import com.self.e_commerce.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserLoginController {
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private UserInfoService userInfoService;


}
