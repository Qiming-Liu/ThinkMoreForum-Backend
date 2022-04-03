package com.thinkmore.forum.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import com.thinkmore.forum.configuration.SecurityConfig;
import com.thinkmore.forum.configuration.StaticConfig;
import com.thinkmore.forum.entity.JwtUser;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

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
                   .signWith(SecurityConfig.secretKey)
                   .compact();
    }

    public static void createMail(String from, String to, String emailTitle, String emailContent) throws Exception {
        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(new Email(from), emailTitle, new Email(to), content);
        Key key = new SecretKeySpec(Hex.decodeHex(StaticConfig.DecodedKey), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        SendGrid sg = new SendGrid(new String(cipher.doFinal(Hex.decodeHex(StaticConfig.Apikey))));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        log.info(response.toString());
    }

    public static String UrlEncoder(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
    }

    public static void checkPermission(String permissionName){
        JsonObject permissionObject = JsonParser.parseString(getJwtContext().get(2)).getAsJsonObject();
        if (!permissionObject.get(permissionName).getAsBoolean()) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED);
        }
    }
}

