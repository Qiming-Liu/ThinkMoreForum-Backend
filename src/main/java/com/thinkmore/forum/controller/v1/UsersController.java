package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.dto.users.UsersMiniPutDto;
import com.thinkmore.forum.service.UsersService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/open_id")
    public ResponseEntity<Boolean> hasOpenid() {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.hasOpenid(userId));
    }

    @PutMapping("/password_reset")
    public ResponseEntity<Boolean> passwordReset(@RequestBody String new_password) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.resetPassword(userId, new_password));
    }

    @GetMapping(path = "/my_details")
    public ResponseEntity<UsersGetDto> getMyUser() throws Exception {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.getUsersById(userId));
    }

    @GetMapping(path = "/details/{usersId}")
    public ResponseEntity<UsersGetDto> getUserById(@PathVariable String usersId) throws Exception {
        UUID userId = UUID.fromString(usersId);
        return ResponseEntity.ok(usersService.getUsersById(userId));
    }

    @PutMapping("/username/{new_username}")
    public ResponseEntity<Boolean> changeUsername(@PathVariable String new_username) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changeUsername(usersId, new_username));
    }

    @PutMapping("/profileimg/{new_profileimg}")
    public ResponseEntity<Boolean> changeProfileImgUrl(@PathVariable String new_profileimg) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changeProfileImgUrl(usersId, new_profileimg));
    }

    @GetMapping("/email/{new_email}")
    public ResponseEntity<Boolean> sendVerificationEmail(@PathVariable String new_email) throws Exception {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.sendVerificationEmail(usersId, new_email));
    }

    @PutMapping("/email/{new_email}")
    public ResponseEntity<Boolean> changeEmail(@PathVariable String new_email) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changeEmail(usersId, new_email));
    }

    @PutMapping("/password")
    public ResponseEntity<Boolean> changePassword(@RequestBody UsersMiniPutDto usersMiniPostDto) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changePassword(usersId, usersMiniPostDto));
    }

    @PutMapping(path="/roles")
    public ResponseEntity<String> changeUsersRoles(@RequestBody List<UsersGetDto> usersGetDtoList) {
        usersService.changeUsersRoles(usersGetDtoList);
        return ResponseEntity.ok("Done");
    }
}
