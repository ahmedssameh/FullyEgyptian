package com.example.fullyegyptian.Controller;

import com.example.fullyegyptian.Service.UserService;
import com.example.fullyegyptian.UserRequest.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userServices;

    @PostMapping(path="/add")
    public @ResponseBody String Register(@Valid @RequestBody AddUserRequest user){
        return userServices.Register(user);
    }

}
