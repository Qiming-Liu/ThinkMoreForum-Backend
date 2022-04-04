package com.thinkmore.forum.service;

import java.util.Optional;

import com.thinkmore.forum.entity.redis.JwtRouter;
import com.thinkmore.forum.repository.JwtRouterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtRouterService {
    private final JwtRouterRepository jwtRouterRepository;

    @Transactional
    public String getRealJwt(String fakeJwt) {
        Optional<JwtRouter> jwtRouter = jwtRouterRepository.findById(fakeJwt);
        return jwtRouter.map(JwtRouter::getRealJwt).orElse(null);
    }

    @Transactional
    public String getFakeJwt(String realJwt) {
        String md5 = DigestUtils.md5Hex(realJwt + Math.random());
        String fakeJwt = md5 + Math.random();

        JwtRouter jwtRouter = new JwtRouter();
        jwtRouter.setFakeJwt(fakeJwt);
        jwtRouter.setRealJwt(realJwt);
        jwtRouterRepository.save(jwtRouter);
        return fakeJwt;
    }
}
