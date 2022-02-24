package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.oauth.OauthGetDto;
import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersService;

    @PostMapping(path = "/signup/{email}/{username}/{password}")
    public ResponseEntity<Boolean> signup(@PathVariable String email, @PathVariable String username, @PathVariable String password) {
        return ResponseEntity.ok(usersService.signup(email, username, password));
    }

    @PostMapping(path = "/third-party-login/{email}/{username}")
    public ResponseEntity<Boolean> thirdPartyLogin(@PathVariable String email, @PathVariable String username) {
        return ResponseEntity.ok(usersService.thirdPartyLogin(email, username));
    }

    @PostMapping(path = "/third-party-login/{username}/{oauthType}/{openid}")
    public ResponseEntity<Boolean> thirdPartyLoginOauth(@PathVariable String username, @PathVariable String oauthType, @PathVariable String openid) {
        return ResponseEntity.ok(usersService.thirdPartyLoginOauth(username, oauthType, openid));
    }
    @GetMapping(path = "/unique-email/{email}")
    public ResponseEntity<Boolean> uniqueEmail(@PathVariable String email) {
        return ResponseEntity.ok(usersService.uniqueEmail(email));
    }

    @GetMapping(path = "/unique-username/{username}")
    public ResponseEntity<Boolean> uniqueUsername(@PathVariable String username) {
        return ResponseEntity.ok(usersService.uniqueUsername(username));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UsersGetDto> findUserById(@PathVariable("user_id") String user_id) {
        UUID userId = UUID.fromString(user_id);
        UsersGetDto usersGetDto = usersService.getUserById(userId);
        return ResponseEntity.ok(usersGetDto);
    }

    @GetMapping("/get-user/{open_id}")
    public ResponseEntity<OauthGetDto> findUserByOpenId(@PathVariable("open_id") String open_id) {
        OauthGetDto oauthGetDto = usersService.getUserByOpenId(open_id);
        return ResponseEntity.ok(oauthGetDto);
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

    @PutMapping("/reset-password/{new_password}")
    public ResponseEntity<Boolean> resetPassword(@PathVariable String new_password) {
        return ResponseEntity.ok(usersService.resetPassword(new_password));
    }

    @PutMapping("set-password/{password}")
    public ResponseEntity<Boolean> setPassword(@PathVariable String password) {
        return ResponseEntity.ok(usersService.setPassword(password));
    }

    @GetMapping("empty-password")
    public ResponseEntity<Boolean> getPassword(){
        return ResponseEntity.ok(usersService.getPassword());
    }
}
