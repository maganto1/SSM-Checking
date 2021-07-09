package com.spring.service;

import com.spring.dao.Login;
import com.spring.pojo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userDetailsServicelmpl implements UserDetailsService {

    @Autowired
    private Login login;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userTest = login.Login(username);
        System.out.println(userTest);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(userTest.password);
        System.out.println(encode);
        if (userTest!= null){
            return userTest;
        }
        throw new UsernameNotFoundException("User"+username+"mot found");
    }
}
