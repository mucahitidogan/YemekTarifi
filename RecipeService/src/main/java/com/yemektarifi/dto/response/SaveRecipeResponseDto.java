package com.yemektarifi.dto.response;

import com.yemektarifi.repository.entity.Ingredient;
import com.yemektarifi.repository.entity.NutritionalValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveRecipeResponseDto {

    private String recipeName;
    private List<String> types;
    private String preparationTime;
    private String cookingTime;
    private String recipeInformation;
    private List<String> images;
    private List<String> categoryIds;
    private List<Ingredient> ingredients;
    private NutritionalValue nutritionalValue;
}
