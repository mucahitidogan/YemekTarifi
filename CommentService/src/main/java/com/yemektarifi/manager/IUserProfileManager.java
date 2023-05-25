package com.yemektarifi.manager;

import com.yemektarifi.dto.request.GetUsernameAndUserIdFromUserProfileRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        url = "http://localhost:8084/api/v1/user-profile",
        name = "comment-userprofile"
)
public interface IUserProfileManager {
    @GetMapping("/get-username-from-user-profile-to-comment/{authId}")
    public ResponseEntity<String> getUsernameFromUserProfileToComment(@PathVariable Long authId);

    @GetMapping("/get-username-and-id-from-user-profile-to-comment/{authId}")
    public ResponseEntity<GetUsernameAndUserIdFromUserProfileRequestDto> getUsernameAndIdFromUserProfileToComment(@PathVariable Long authId);

}
