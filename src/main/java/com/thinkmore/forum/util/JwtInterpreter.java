package com.thinkmore.forum.util;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This is the temporary method for userId extraction from JWT.
 * getUserId() function returns a UUID.
 */

public class JwtInterpreter {

    public static UUID getUserId(Object userInfoObj) {
        ArrayList<String> userInfoToString = (ArrayList<String>) userInfoObj;
        UUID userId = UUID.fromString(userInfoToString.get(0));
        return userId;
    }
}
