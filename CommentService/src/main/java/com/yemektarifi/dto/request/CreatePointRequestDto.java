package com.yemektarifi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePointRequestDto {
    private String recipeId;
    @Max(10)
    @Min(1)
    private Double point;
}
