package com.yemektarifi.dto.request;

import com.yemektarifi.repository.entity.Ingredient;
import com.yemektarifi.repository.entity.NutritionalValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveRecipeRequestDto {

    @NotBlank
    private String recipeName;
    @NotBlank
    private List<String> types;
    @NotBlank
    private String preparationTime;
    @NotBlank
    private String cookingTime;
    @NotBlank
    private String recipeInformation;
    @NotNull
    private List<String> images;
    @NotNull
    private List<String> categoryIds;
    @NotNull
    private List<Ingredient> ingredients;
    @NotNull
    private NutritionalValue nutritionalValue;
}
