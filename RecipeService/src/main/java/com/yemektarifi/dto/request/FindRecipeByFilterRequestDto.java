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
public class FindRecipeByFilterRequestDto {

    private List<String> categoryIds;
    private String recipeName;
    private List<String> ingredientNames;

}
