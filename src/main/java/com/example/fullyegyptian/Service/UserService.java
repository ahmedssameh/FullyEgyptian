package com.example.fullyegyptian.Service;

import com.example.fullyegyptian.Model.User;
import com.example.fullyegyptian.Repo.UserRepo;
import com.example.fullyegyptian.UserRequest.AddUserRequest;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    PasswordValidation passwordValidation;

    @Transactional
    public String Register(AddUserRequest AddedUser){
        List<Rule> rules=passwordValidation.getRules();
        PasswordValidator validator = new PasswordValidator(rules);
        if(passwordValidation.isCorrect(AddedUser.getPassword()).isValid()){
        String encodedPassword = passwordEncoder.encode(AddedUser.getPassword());
        User user= new User(AddedUser.getUsername(),encodedPassword, AddedUser.getEmail(),AddedUser.getPhoneNumber());
        userRepo.save(user);
        return "Registration is done";
        }
        return "Invalid Password: " + validator.getMessages(passwordValidation.isCorrect(AddedUser.getPassword()));
    }


    @Override @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
    }


}
