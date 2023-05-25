package com.yemektarifi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToUserProfileNewRecipeCheckFavoriteCategoryRequestDto {

    private List<String> recipeIdList = new ArrayList<>();
    private List<String> categoryNameList = new ArrayList<>();
    private String recipeName;

}
