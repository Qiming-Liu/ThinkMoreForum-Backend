package com.thinkmore.forum.util;

import com.thinkmore.forum.configuration.Config;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

public class Util {

    /**
     * @return [
     * "1f7bdc16-8720-11ec-a661-271721f30666",   UUID.fromString(get(0))
     * "admin",                                  get(1)
     * "{}"                                      get(2)
     * ]
     */
    public static ArrayList<String> getJwtContext() {
        return (ArrayList<String>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String passwordEncoder(String password) {
        return Config.passwordEncoder.encode(password);
    }

    public static boolean match(String rawPassword, String encodedPassword ) {
        return Config.passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
