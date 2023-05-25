package com.yemektarifi.dto.request;

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
public class UpdateRecipeRequestDto {

    private String recipeId;
    private String recipeName;
    private List<String> removeTypes = null;
    private List<String> addTypes = null;
    private String preparationTime;
    private String cookingTime;
    private String recipeInformation;
    private List<String> removeImages = null;
    private List<String> addImages = null;
    private List<String> removeCategoryIds = null;
    private List<String> addCategoryIds = null;
    private List<Ingredient> removeIngredients = null;
    private List<Ingredient> addIngredients = null;
    private NutritionalValue NutritionalValue;

}
