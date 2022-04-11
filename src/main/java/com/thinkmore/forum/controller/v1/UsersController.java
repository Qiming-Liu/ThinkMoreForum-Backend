package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.dto.users.UsersImgPutDto;
import com.thinkmore.forum.dto.users.UsersMiniPutDto;
import com.thinkmore.forum.dto.users.UsersPasswordPutDto;
import com.thinkmore.forum.service.MessageService;
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
    private final MessageService messageService;

    @GetMapping(path="/all")
    public  ResponseEntity<List<UsersGetDto>> getAllUsers(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @GetMapping(path = "/search/{string}")
    public ResponseEntity<List<UsersGetDto>> getUserByContainingString(@PathVariable("string") String string) {
        List<UsersGetDto> response = usersService.getUserByContainingString(string);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password_reset")
    public ResponseEntity<Boolean> passwordReset(@RequestBody UsersPasswordPutDto new_password) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.resetPassword(userId, new_password));
    }

    @PutMapping("/password")
    public ResponseEntity<Boolean> changePassword(@RequestBody UsersMiniPutDto usersMiniPutDto) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changePassword(usersId, usersMiniPutDto));
    }

    @PutMapping("/username/{new_username}")
    public ResponseEntity<Boolean> changeUsername(@PathVariable String new_username) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changeUsername(usersId, new_username));
    }

    @PutMapping("/headimg")
    public ResponseEntity<Boolean> changeHeadImg(@RequestBody UsersImgPutDto usersImgPutDto) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changeHeadImgUrl(usersId, usersImgPutDto));
    }

    @PutMapping("/profile_img")
    public ResponseEntity<Boolean> changeProfileImg(@RequestBody UsersImgPutDto usersImgPutDto) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changeProfileImgUrl(usersId, usersImgPutDto));
    }

    @GetMapping("/email/{new_email}")
    public ResponseEntity<Boolean> sendVerificationEmail(@PathVariable String new_email) throws Exception {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(messageService.sendVerificationEmail(usersId, new_email));
    }

    @PutMapping("/email/{new_email}")
    public ResponseEntity<Boolean> changeEmail(@PathVariable String new_email) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(usersService.changeEmail(usersId, new_email));
    }

    @PutMapping(path="/roles")
    public ResponseEntity<String> changeUsersRoles(@RequestBody List<UsersGetDto> usersGetDtoList) {
        Util.checkPermission("adminManagement");
        usersService.changeUsersRoles(usersGetDtoList);
        return ResponseEntity.ok("Done");
    }
}
