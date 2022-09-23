package com.example.fullyegyptian.UserRequest;

import lombok.Data;

@Data
public class AddUserRequest {
    private String Username;
    private String Password;
    private String Email;
    private String PhoneNumber;

}
