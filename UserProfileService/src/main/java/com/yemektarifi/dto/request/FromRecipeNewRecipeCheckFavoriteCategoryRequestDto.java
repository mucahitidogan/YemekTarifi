package com.yemektarifi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FromRecipeNewRecipeCheckFavoriteCategoryRequestDto {

    private List<String> recipeIdList;
    private List<String> categoryNameList;
    private String recipeName;

}
