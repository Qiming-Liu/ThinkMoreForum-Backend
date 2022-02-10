package com.thinkmore.forum.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.entity.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
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

    public static boolean match(String rawPassword, String encodedPassword) {
        return Config.passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static void createMail(String from, String to, String emailTitle, String emailContent) throws IOException {
        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(new Email(from), emailTitle, new Email(to), content);
        SendGrid sg = new SendGrid(Util.decode(Config.DecodedKey, Config.Apikey));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        log.info(response.toString());
    }

    public static String decode(String thisKey, String data) {
        try {
            Key key = new SecretKeySpec(Hex.decodeHex(thisKey), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Hex.decodeHex(data));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createJwtToken(Users user) {
        return Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getRole().getRoleName())
                .setAudience(user.getRole().getPermission())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(Config.ExpireTime))
                .signWith(Keys.hmacShaKeyFor(Config.JwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
