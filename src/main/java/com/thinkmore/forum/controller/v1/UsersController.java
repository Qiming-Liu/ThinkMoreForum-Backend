package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.oauth.OauthGetDto;
import com.thinkmore.forum.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersService;

    @PostMapping(path = "/register/{email}/{username}/{password}")
    public ResponseEntity<Boolean> register(@PathVariable String email, @PathVariable String username, @PathVariable String password) {
        return ResponseEntity.ok(usersService.register(email, username, password));
    }

    @PostMapping(path = "/third-party-login/{email}/{username}/{oauthType}/{openid}")
    public ResponseEntity<Boolean> thirdPartyLogin(@PathVariable String email, @PathVariable String username, @PathVariable String oauthType, @PathVariable String openid) {
        return ResponseEntity.ok(usersService.thirdPartyLogin(email, username, oauthType, openid));
    }

    @GetMapping("/get-openid/{username}")
    public ResponseEntity<Boolean> hasOpenid(@PathVariable String username) {
        return ResponseEntity.ok(usersService.hasOpenid(username));
    }

    @GetMapping(path = "/unique-email/{email}")
    public ResponseEntity<Boolean> uniqueEmail(@PathVariable String email) {
        return ResponseEntity.ok(usersService.uniqueEmail(email));
    }

    @GetMapping(path = "/unique-username/{username}")
    public ResponseEntity<Boolean> uniqueUsername(@PathVariable String username) {
        return ResponseEntity.ok(usersService.uniqueUsername(username));
    }

    @PutMapping("/username/{new_username}")
    public ResponseEntity<Boolean> changeUsername(@PathVariable String new_username) {
        return ResponseEntity.ok(usersService.changeUsername(new_username));
    }

    @GetMapping("/email/{new_email}")
    public ResponseEntity<Boolean> sendVerificationEmail(@PathVariable String new_email) throws Exception {
        return ResponseEntity.ok(usersService.sendVerificationEmail(new_email));
    }

    @PutMapping("/email/{new_email}")
    public ResponseEntity<Boolean> changeEmail(@PathVariable String new_email) {
        return ResponseEntity.ok(usersService.changeEmail(new_email));
    }

    @PutMapping("/password/{old_password}/{new_password}")
    public ResponseEntity<Boolean> changePassword(@PathVariable String old_password, @PathVariable String new_password) {
        return ResponseEntity.ok(usersService.changePassword(old_password, new_password));
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<Boolean> sendResetPasswordEmail(@PathVariable String email) throws Exception {
        return ResponseEntity.ok(usersService.sendResetPasswordEmail(email));
    }

    @PutMapping("/password-reset/{new_password}")
    public ResponseEntity<Boolean> resetPassword(@PathVariable String new_password) {
        return ResponseEntity.ok(usersService.resetPassword(new_password));
    }

    @PutMapping("/set-password/{password}")
    public ResponseEntity<Boolean> setPassword(@PathVariable String password) {
        return ResponseEntity.ok(usersService.setPassword(password));
    }
}
