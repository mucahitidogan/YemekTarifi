package com.yemektarifi.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionalValue implements Serializable {

    private String nutritionalName;
    @Min(0)
    private Double calorie;
    @Min(0)
    private Double protein;
    @Min(0)
    private Double carbohydrate;
    @Min(0)
    private Double fat;

}
