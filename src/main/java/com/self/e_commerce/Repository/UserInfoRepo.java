package com.self.e_commerce.Repository;

import com.self.e_commerce.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo,Integer> {
    UserInfo findByUsername(String username);

}
