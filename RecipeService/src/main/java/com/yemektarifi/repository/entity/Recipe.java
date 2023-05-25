package com.yemektarifi.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class Recipe extends Base implements Serializable {

    @Id
    private String recipeId;
    @NotBlank
    private String recipeName = null;
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
    private List<String> commentIds = new ArrayList<>();
    private List<String> pointId = new ArrayList<>();

}
