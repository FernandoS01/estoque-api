package com.fernando.estoque_api.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    PasswordEncoder passwordEncoder;
    public PasswordService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String password){
        return passwordEncoder.encode(password);
    }
    public boolean verifyPassword(String password,String passwordHash){
        return passwordEncoder.matches(password,passwordHash);
    }
}
