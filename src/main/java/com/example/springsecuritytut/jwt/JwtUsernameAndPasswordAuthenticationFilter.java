package com.example.springsecuritytut.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // Authentication manager designates responsibility to handle login requests

   private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager =authenticationManager;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        try{

            UsernamePasswordAuthenticationRequest authenticationRequest = new ObjectMapper()    // authenticate request
                    .readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);
                return authenticate;
        }catch(IOException err){
            throw new RuntimeException(err);
        }

    }

    // this method is only called after the authentication is successful


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
String key = "secureqwertyuiopoiuysasxcvbnmjhgfdsxc ";
       String token = Jwts.builder()
               .setSubject(authResult.getName()) // gets the name of the client from the header
                       .claim("authorities", authResult.getAuthorities()) // verifies the claims, what authorities the client has
                               .setIssuedAt(new Date()) // date  the token is issued
                                       .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2))) // expiry period
                                               .signWith(Keys.hmacShaKeyFor(key.getBytes()))  // signature of token
                                                  .compact();
       response.addHeader("Authorization", "Bearer" + token);
    }
}
