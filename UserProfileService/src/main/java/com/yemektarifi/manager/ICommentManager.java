package com.yemektarifi.manager;

import com.yemektarifi.dto.request.ToCommentDeleteUserProfileInfoRequestDto;
import com.yemektarifi.dto.request.ToCommentUpdateUsernameRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        url = "http://localhost:8081/api/v1/comment",
        name = "userprofile-comment"
)
public interface ICommentManager {

    @PutMapping("/update-username")
    public ResponseEntity<Boolean> updateUsername(@RequestBody ToCommentUpdateUsernameRequestDto dto);

    @DeleteMapping("/delete-user-profile-info")
    public ResponseEntity<Boolean> deleteUserProfileInfo(@RequestBody ToCommentDeleteUserProfileInfoRequestDto dto);
}
