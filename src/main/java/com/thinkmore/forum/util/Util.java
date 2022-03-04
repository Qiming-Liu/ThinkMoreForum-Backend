package com.thinkmore.forum.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.configuration.Singleton;
import com.thinkmore.forum.entity.JwtUser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class Util {

    /**
     * "1f7bdc16-8720-11ec-a661-271721f30666",   UUID.fromString(Util.getJwtContext().get(0))
     * "admin",                                  Util.getJwtContext().get(1)
     * "{}"                                      Util.getJwtContext().get(2)
     */
    public static ArrayList<String> getJwtContext() {
        return (ArrayList<String>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String generateJwt(JwtUser user) {
        return Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getRoleName())
                .setAudience(user.getPermission())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Singleton.secretKey)
                .compact();
    }

    public static void createMail(String from, String to, String emailTitle, String emailContent) throws Exception {
        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(new Email(from), emailTitle, new Email(to), content);
        Key key = new SecretKeySpec(Hex.decodeHex(Config.DecodedKey), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        SendGrid sg = new SendGrid(new String(cipher.doFinal(Hex.decodeHex(Config.Apikey))));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        log.info(response.toString());
    }
    public static void checkPassword (String password){
        //TODO fix here
//        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$^&+=])(?=\\S+$).{6,16}";
//        if (!password.matches(pattern)){
//            throw new InvalidOldPasswordException("Invalid Password!" );
//        }
    }

    public static String UrlEncoder(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
    }
}

