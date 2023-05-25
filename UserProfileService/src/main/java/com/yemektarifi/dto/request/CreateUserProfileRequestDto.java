package com.yemektarifi.dto.request;

import com.yemektarifi.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserProfileRequestDto {

    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;
}
