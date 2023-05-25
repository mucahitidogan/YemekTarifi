package com.yemektarifi.manager;

import com.yemektarifi.dto.request.CreateUserProfileRequestDto;
import com.yemektarifi.dto.request.ToUserProfileServiceForgotPasswordUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        url = "http://localhost:8084/api/v1/user-profile",
        name = "auth-userprofile"
)
public interface IUserProfileManager {

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserProfileRequestDto dto);

    @PutMapping("/forgot-password")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody ToUserProfileServiceForgotPasswordUpdateRequestDto dto);

    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);
}
