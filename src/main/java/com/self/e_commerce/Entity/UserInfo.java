package com.self.e_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Entity(name="users")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int user_id;
    @NotNull
    @Column(unique = true)
    private String username;

    private String password_hash;

    @NotNull
//    @JsonProperty("full_name")
    private String full_name;

    @Email    @NotNull
    private String email;
    private String phone_number;

    @CreationTimestamp
    private LocalDateTime created_at;


    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password_hash='" + password_hash + '\'' +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
