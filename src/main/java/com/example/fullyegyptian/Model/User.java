package com.example.fullyegyptian.Model;

import com.example.fullyegyptian.JwtUserDetailsServices.Provider;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NotNull
    @Size(min = 3,max = 25)
    @Column(unique=true)
    private String Username;


    @Enumerated(EnumType.STRING)
    private Provider provider;


    @NotNull
    @Size(min =8)
    private String Password;


    @NotNull
    @Email(message = "Enter correct email (example@example.com)")
    private String Email;

    @Size(min = 11,max = 11)
    @NotNull
    private String PhoneNumber;

    @ManyToOne(fetch= FetchType.EAGER)
    @Autowired
    private Role role ;

    public User(String username, String password, String email, String phoneNumber) {
        Username = username;
        Password = password;
        Email = email;
        PhoneNumber = phoneNumber;
    }






}

