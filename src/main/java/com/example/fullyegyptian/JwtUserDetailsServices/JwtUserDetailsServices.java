package com.example.fullyegyptian.JwtUserDetailsServices;


import java.util.ArrayList;
import java.util.Collection;


import com.example.fullyegyptian.Repo.RoleRepo;
import com.example.fullyegyptian.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsServices implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Override @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.fullyegyptian.Model.User user = userRepo.findUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
    }
}

