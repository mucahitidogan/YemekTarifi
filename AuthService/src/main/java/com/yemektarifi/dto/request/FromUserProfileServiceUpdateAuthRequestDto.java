package com.yemektarifi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FromUserProfileServiceUpdateAuthRequestDto {

    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;
}
