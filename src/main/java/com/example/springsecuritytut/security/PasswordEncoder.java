package com.example.springsecuritytut.security;

import org.springframework.context.annotation.Configuration;


@Configuration
public interface PasswordEncoder{
    String encode(CharSequence var1);  // method takes in a series of character and encode it
    boolean matches(CharSequence var1, String var2);
    default boolean upgradeEncoding(String encodedPassword){
            return false;
        }
}
