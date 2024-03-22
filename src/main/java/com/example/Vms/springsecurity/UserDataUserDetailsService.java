package com.example.Vms.springsecurity;

import com.example.Vms.entities.User;
import com.example.Vms.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = repository.findByName(username);
//        return user.map(UserDataUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
return null;
    }
}
