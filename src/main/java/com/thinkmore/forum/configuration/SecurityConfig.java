package com.thinkmore.forum.configuration;

import com.thinkmore.forum.filter.JwtTokenFilter;
import com.thinkmore.forum.filter.JwtUsernameAndPasswordAuthFilter;
import com.thinkmore.forum.service.UsersService;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersService usersService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/**");
    }

    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthFilter(authenticationManager(), secretKey()))
                .addFilterAfter(new JwtTokenFilter(secretKey()), JwtUsernameAndPasswordAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usersService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor("fruue37r7yrfhf87f7876guyggf%$$#$%^&%RTHGhjjkj23456rkkkkdkd".getBytes());
    }
}
