package com.example.fullyegyptian.Controller;

import com.example.fullyegyptian.JwtUserDetailsServices.JwtUserDetailsServices;
import com.example.fullyegyptian.Model.JwtRequest;
import com.example.fullyegyptian.Model.JwtResponse;
import com.example.fullyegyptian.Config.JwtTokenUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin
@Component(value = "JwtAuthenticationController")
@Data

public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServices userDetailsService;


    @Autowired
    private HttpServletResponse jwtResponse;


    @GetMapping(value = "/bara")
    public @ResponseBody String logout(HttpServletResponse response){
        Cookie deleteServletCookie = new Cookie("access_token", null);
        deleteServletCookie.setMaxAge(0);
        response.addCookie(deleteServletCookie);
        return "You have logged out";
    }
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public @ResponseBody String createAuthenticationToken(HttpServletResponse response,@RequestBody JwtRequest jwtRequest) throws Exception {

        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
//        final String token_Bearer = "Bearer"+ token;
        JwtResponse jwtResponse= new JwtResponse(token);
        // create a cookie
        Cookie cookie = new Cookie("access_token", token);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.setHeader("Access-Control-Allow-Credentials", "true"); //TODO: check if should be set to true or not (production)
        //add cookie to response
        response.addCookie(cookie);
        response.addHeader("Authorization", "Bearer "+ token);
        jwtRequest=new JwtRequest();
        return token;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
