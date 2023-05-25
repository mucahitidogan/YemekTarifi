package com.yemektarifi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePointResponseDto {
    private Double point;
    private String userProfileId;
    private String recipeId;
}
