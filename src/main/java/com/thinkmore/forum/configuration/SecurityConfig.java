package com.thinkmore.forum.configuration;

import com.thinkmore.forum.filter.JwtGenerateFilter;
import com.thinkmore.forum.filter.JwtCheckFilter;
import com.thinkmore.forum.service.UsersService;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersService usersService;

    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtGenerateFilter(usersService, authenticationManager(), secretKey()))
                .addFilterAfter(new JwtCheckFilter(secretKey()), JwtGenerateFilter.class)
                .authorizeRequests()
                .antMatchers(Config.ignoreUrl).permitAll()
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
        return Keys.hmacShaKeyFor(Config.JwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
}
