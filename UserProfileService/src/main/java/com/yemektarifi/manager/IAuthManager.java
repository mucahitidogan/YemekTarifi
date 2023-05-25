package com.yemektarifi.manager;

import com.yemektarifi.dto.request.ToAuthServiceChangePasswordRequestDto;
import com.yemektarifi.dto.request.ToAuthServiceUpdateAddressRequestDto;
import com.yemektarifi.dto.request.ToAuthServiceUpdateAuthRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        url = "http://localhost:8080/api/v1/auth",
        name = "userprofile-auth"
)
public interface IAuthManager {

    @PutMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(@RequestBody ToAuthServiceChangePasswordRequestDto dto);

    @PutMapping("/update-auth")
    public ResponseEntity<Boolean> updateAuth(@RequestBody ToAuthServiceUpdateAuthRequestDto dto);

    @DeleteMapping( "delete-by-authId/{authId}")
    public ResponseEntity<Boolean> deleteByAuthId(@PathVariable Long authId);

}
