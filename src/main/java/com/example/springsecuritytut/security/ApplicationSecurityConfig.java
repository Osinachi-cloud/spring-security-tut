package com.example.springsecuritytut.security;

import com.example.springsecuritytut.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.springsecuritytut.jwt.JwtTokenVerifier;

import static com.example.springsecuritytut.security.ApplicationUserRole.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true )
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
//                .csrf().disable() // neccesary to disable cross site request forgery to avoid mallicious activity by third parties, disable recommended to be used in apps that uses the  browsers only
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// to make it stateless that is no database storage
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager())) // to add filter before going to the controller
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests() // call authorize functionality
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()         // allow url patterns like this to the general (public)
//                .antMatchers("/api/**").hasRole(STUDENT.name())  // allow patterns like this to specified roles
//                .antMatchers(HttpMethod.DELETE, "management/api/vi/students").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "management/api/vi/students").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "management/api/vi/students").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "management/api/vi/students").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
//                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest() // allow anyone only on a condition
                .authenticated() // authenticate based on username and password
                .and() // and
//                .httpBasic()  // using basic http method using basic auth. cannot log out
                .formLogin() // for using real forms. can log out
                .loginPage("/login").permitAll() // for adding custom login html and permiting it from being blocked by spring security
                .defaultSuccessUrl("/courses", true) // redirects to a url after login so that it doesn'nt go to the public url
                .and()
                .rememberMe();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {  // determines how you retrieve data from the datatbase
        UserDetails annaSmittUser = User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("password"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();



        UserDetails linderUser = User.builder()
                .username("linderUser")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails tomUser = User.builder()
                .username("tomUser")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();


        return new InMemoryUserDetailsManager(

                annaSmittUser,
                linderUser,
                tomUser
        );
    }

}
