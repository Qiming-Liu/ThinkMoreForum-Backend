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
import com.thinkmore.forum.exception.InvalidOldPasswordException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Hex;
import org.passay.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static void sendMail(Email email, String jwtToken) throws IOException {
        String url = Config.url + jwtToken;
        Content content = new Content("text/plain", "Hi, please click the link to reset your password: " + url);
        Mail mail = new Mail(Config.senderEmail, Config.emailSubject,email, content);
//        mail.setTemplateId(Config.templateId);
        createMail(mail);
    }

    public static void createMail(Mail mail) throws IOException {
        SendGrid sg = new SendGrid(Util.decode(Config.decodedKey, Config.key));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }

    public static String decode(String thisKey, String data) {
        try {
            Key key = new SecretKeySpec(Hex.decodeHex(thisKey),"AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Hex.decodeHex(data));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createJwtToken(Users user){
        return Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getRole().getRoleName())
                .setAudience(user.getRole().getPermission())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(Config.ExpireTime))
                .signWith(Keys.hmacShaKeyFor(Config.JwtSecretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public static void sendVerificationEmail(Email email, String jwtToken) throws IOException {
        String url = Config.emailUrl + jwtToken;
        Content content = new Content("text/plain", "Please click the link to verify your new email address: " + "\n" + url);
        Mail mail = new Mail(Config.senderEmail, Config.verifyEmailSubject, email, content);
        createMail(mail);
    }
    public static void checkPassword (String password){
        List<Rule> rules = new ArrayList<>();
        //Rule 1: Password length should be in between
        //8 and 16 characters
        rules.add(new LengthRule(8, 16));
        //Rule 2: No whitespace allowed
        rules.add(new WhitespaceRule());
        //Rule 3.a: At least one Upper-case character
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        //Rule 3.b: At least one Lower-case character
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        //Rule 3.c: At least one digit
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        //Rule 3.d: At least one special character
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordValidator validator = new PasswordValidator(rules);
        PasswordData passwordData = new PasswordData(password);
        RuleResult result = validator.validate(passwordData);
        if (!result.isValid()){
            throw new InvalidOldPasswordException("Invalid Password: " + validator.getMessages(result));
        }
    }
}
