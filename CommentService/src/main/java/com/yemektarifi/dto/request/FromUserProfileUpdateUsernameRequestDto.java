package com.yemektarifi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FromUserProfileUpdateUsernameRequestDto {
    private String userProfileId;
    private String oldUsername;
    private String newUsername;
}
