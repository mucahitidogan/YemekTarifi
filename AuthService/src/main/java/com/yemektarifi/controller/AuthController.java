package com.yemektarifi.controller;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.dto.response.RegisterResponseDto;
import com.yemektarifi.repository.entity.Address;
import com.yemektarifi.repository.entity.Auth;
import com.yemektarifi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.yemektarifi.constants.ApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Auth>> findAll(){
        return ResponseEntity.ok(authService.findAll());
    }

    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateStatusRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<Boolean> changePassword(@RequestBody FromUserProfileChangePasswordRequestDto dto){
        return ResponseEntity.ok(authService.changePassword(dto));
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(String email, String username){
        return ResponseEntity.ok(authService.forgotPassword(email, username));
    }

    @PutMapping("/update-auth")
    public ResponseEntity<Boolean> updateAuth(@RequestBody FromUserProfileServiceUpdateAuthRequestDto dto){
        return ResponseEntity.ok(authService.updateAuth(dto));
    }

    @DeleteMapping( "delete-by-authId/{authId}")
    public ResponseEntity<Boolean> deleteByAuthId(@PathVariable Long authId){
        return ResponseEntity.ok(authService.deleteByAuthId(authId));
    }
}
