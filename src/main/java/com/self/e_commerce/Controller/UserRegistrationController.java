package com.self.e_commerce.Controller;

import com.self.e_commerce.Entity.UserInfo;
import com.self.e_commerce.Exception.ResourceNotFoundException;
import com.self.e_commerce.Repository.UserInfoRepo;
import com.self.e_commerce.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserRegistrationController {
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("/registration")
    public ResponseEntity<String> UserRegistration(@Valid @RequestBody UserInfo userInfo, BindingResult result) throws Exception{
        System.out.println("Received updates: " + userInfo);
        if(result.hasErrors()){
            String errorMessage = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMessage);
        }

        UserInfo save = userInfoRepo.save(userInfo);
        return ResponseEntity.ok("User Registered Succesfully");
    }
    @PatchMapping("/updateProfileData/{id}")
    public ResponseEntity<String> updateUserDetails(@PathVariable int id,@RequestBody Map<String,Object> updates ) throws Exception{

        UserInfo byId;
        Optional<UserInfo> user = userInfoRepo.findById(id);
        if (user.isPresent()) {
             byId = user.get();
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        System.out.println("Received updates: " + updates);
        StringBuilder errors = new StringBuilder();
        updates.forEach((field,value)->{
            try{
            switch (field){
                case "email":
                    String email = (String) value;
                    if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        errors.append("Invalid email format. ");
                    } else {
                        byId.setEmail(email);
                    }
                    break;
                case "full_name":
                    String fullName = (String) value;
                    if (fullName == null || fullName.isEmpty()) {
                        errors.append("Full name cannot be empty. ");
                    } else {
                        byId.setFull_name(fullName);
                    }
                    break;
                case "phone_number":
                    String phoneNumber = (String) value;
                    if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
                        errors.append("Phone number must be 10 digits. ");
                    } else {
                        byId.setPhone_number(phoneNumber);
                    }
                    break;

                default:
                    System.out.println("Modification not allowed for field: " + field);
            }
            } catch (ClassCastException e) {
                errors.append("Invalid data type for field ").append(field).append(". ");
            }
        });

        if (errors.length() > 0) {
            return ResponseEntity.badRequest().body(errors.toString());
        }
        userInfoRepo.save(byId);
        return ResponseEntity.ok("Data updated");
    }
    @PatchMapping("/updateProfileData/{username}")
    public ResponseEntity<String> updateUserDetailsByUsername(@PathVariable String username, @RequestBody Map<String,Object> updates) throws Exception{

        UserInfo user = userInfoService.findUserIdByUsername(username);
        StringBuilder errors = new StringBuilder();
        updates.forEach((field,value)->{
            try{
                switch (field){
                    case "email":
                        String email = (String) value;
                        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                            errors.append("Invalid email format. ");
                        } else {
                            user.setEmail(email);
                        }
                        break;
                    case "full_name":
                        String fullName = (String) value;
                        if (fullName == null || fullName.isEmpty()) {
                            errors.append("Full name cannot be empty. ");
                        } else {
                            user.setFull_name(fullName);
                        }
                        break;
                    case "phone_number":
                        String phoneNumber = (String) value;
                        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
                            errors.append("Phone number must be 10 digits. ");
                        } else {
                            user.setPhone_number(phoneNumber);
                        }
                        break;

                    default:
                        System.out.println("Modification not allowed for field: " + field);
                }
            } catch (ClassCastException e) {
                errors.append("Invalid data type for field ").append(field).append(". ");
            }
        });

        if (errors.length() > 0) {
            return ResponseEntity.badRequest().body(errors.toString());
        }
        userInfoRepo.save(user);
        return ResponseEntity.ok("Data updated");
    }

    @DeleteMapping("/deleteProfile")
    public ResponseEntity<String> deleteUserProfile(@RequestParam(required = false) Integer id,
                                                    @RequestParam(required = false) String username) throws Exception {
        if (id == null && username == null) {
            return ResponseEntity.badRequest().body("Please provide either 'id' or 'username' to delete the profile.");
        }

        if (id != null) {
            Optional<UserInfo> user = userInfoRepo.findById(id);
            if(user.isPresent()) {
                userInfoRepo.deleteById(id);
            }
            else{
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Deleted");
        } else {
            UserInfo user = userInfoService.findUserIdByUsername(username);
            userInfoRepo.delete(user);
            return ResponseEntity.ok("Deleted");
        }
    }

}
