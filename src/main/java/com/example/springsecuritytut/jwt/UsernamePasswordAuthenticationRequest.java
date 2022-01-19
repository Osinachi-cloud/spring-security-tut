package com.example.springsecuritytut.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsernamePasswordAuthenticationRequest {

    private String username;
    private String password;

}
