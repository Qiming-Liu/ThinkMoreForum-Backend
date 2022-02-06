package com.thinkmore.forum.filter;

import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class JwtCheckFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (stringIsNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(Config.JwtPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(Config.JwtPrefix, "");

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
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
        } catch (JwtException e) {
            throw new InvalidJwtException(String.format("Token invalid: %s", token));
        }

        filterChain.doFilter(request, response);
    }

    static boolean stringIsNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
