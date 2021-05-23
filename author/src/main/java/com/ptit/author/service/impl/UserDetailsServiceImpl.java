package com.ptit.author.service.impl;

import com.ptit.author.entity.CustomUserDetails;
import com.ptit.author.entity.User;
import com.ptit.author.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User staff=userRepository.findByUsername(username).orElse(null);
        if(staff == null){
            throw new UsernameNotFoundException("User not found");
        }
        return  new CustomUserDetails(staff);
    }

}
