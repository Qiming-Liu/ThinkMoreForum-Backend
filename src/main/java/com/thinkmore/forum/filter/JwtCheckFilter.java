package com.thinkmore.forum.filter;

import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.configuration.Singleton;
import com.thinkmore.forum.util.Util;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class JwtCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (stringIsNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(Config.JwtPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(Config.JwtPrefix, "");

        try {
            //check jwt
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Singleton.secretKey)
                    .build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();

            ArrayList<String> principal = new ArrayList<>();
            principal.add(body.getId());
            principal.add(body.getSubject());
            principal.add(body.getAudience());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    null
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //update jwt
            JwtUser jwtUser = new JwtUser(principal);

            response.addHeader(HttpHeaders.AUTHORIZATION, Config.JwtPrefix + Util.generateJwt(jwtUser));


        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            log.info(String.valueOf(e));
            return;
        }

        filterChain.doFilter(request, response);
    }

    static boolean stringIsNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
