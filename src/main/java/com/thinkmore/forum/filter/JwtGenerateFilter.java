package com.thinkmore.forum.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.configuration.StaticConfig;
import com.thinkmore.forum.service.JwtRouterService;
import com.thinkmore.forum.service.UsersService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtGenerateFilter extends UsernamePasswordAuthenticationFilter {
    private final UsersService usersService;
    private final JwtRouterService jwtRouterService;
    private final AuthenticationManager authenticationManager;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //check username and password
        String dataString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject dataObject = JsonParser.parseString(dataString).getAsJsonObject();

        String email = dataObject.get("email").toString().replace("\"", "");
        String password = dataObject.get("password").toString().replace("\"", "");

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        //update last login timestamp
        UsersGetDto usersGetDto = usersService.updateLastLoginTimestamp(authResult.getName());

        //generate jwt
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        String newJwt = Util.generateJwt(jwtUser);
        response.addHeader(HttpHeaders.AUTHORIZATION, StaticConfig.JwtPrefix + jwtRouterService.getFakeJwt(newJwt));

        //return user info
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        response.getWriter().write(objectMapper.writeValueAsString(usersGetDto));
    }
}
