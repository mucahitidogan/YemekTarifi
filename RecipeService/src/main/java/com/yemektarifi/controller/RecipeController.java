package com.yemektarifi.controller;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.dto.response.SaveRecipeResponseDto;
import com.yemektarifi.dto.response.UpdateRecipeResponseDto;
import com.yemektarifi.repository.entity.Recipe;
import com.yemektarifi.service.RecipeService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.yemektarifi.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(RECIPE)
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping(CREATE + "/{token}")
    public ResponseEntity<SaveRecipeResponseDto> saveRecipe(@PathVariable String token, @RequestBody SaveRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.saveRecipe(token, dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Recipe>> findAll(){
        return ResponseEntity.ok(recipeService.findAll());
    }

    @GetMapping(FIND_ALL_WITH_CACHE)
    public ResponseEntity<List<Recipe>> findAllWithCache(){
        return ResponseEntity.ok(recipeService.findAllWithCache());
    }

    @PutMapping(UPDATE + "/{token}")
    public ResponseEntity<UpdateRecipeResponseDto> saveRecipe(@PathVariable String token, @RequestBody UpdateRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.updateRecipe(token, dto));
    }

    @DeleteMapping(DELETE_BY_ID + "/{token}/{recipeId}")
     public ResponseEntity<Boolean> deleteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(recipeService.deleteRecipe(token, recipeId));
     }

    @Hidden
    @GetMapping(IS_RECIPE_EXIST+"/{recipeId}")
    public ResponseEntity<Boolean> isRecipeExist(@PathVariable String recipeId){
        return ResponseEntity.ok(recipeService.isRecipeExist(recipeId));
    }

    @Hidden
    @PutMapping(ADD_COMMENT_ID_TO_RECIPE)
    public ResponseEntity<Boolean> addCommentIdToRecipe(@RequestBody FromCommentAddCommentRequestDto dto){
        return ResponseEntity.ok(recipeService.addCommentIdToRecipe(dto));
    }

    @Hidden
    @DeleteMapping(DELETE_COMMENT)
    public ResponseEntity<Boolean> deleteComment(@RequestBody FromCommentDeleteCommentRequestDto dto){
        return ResponseEntity.ok(recipeService.deleteComment(dto));
    }

    @Hidden
    @PutMapping(ADD_POINT_ID_TO_RECIPE)
    public ResponseEntity<Boolean> addPointIdToRecipe(@RequestBody FromPointAddPointIdRequestDto dto){
        return ResponseEntity.ok(recipeService.addPointIdToRecipe(dto));
    }

    @Hidden
    @DeleteMapping(DELETE_POINT_ID)
    public ResponseEntity<Boolean> deletePointIdRecipe(@RequestBody FromPointDeletePointIdRequestDto dto){
        return ResponseEntity.ok(recipeService.deletePointIdRecipe(dto));
    }

    @GetMapping(FIND_RECIPE_BY_CATEGORY)
    public ResponseEntity<List<Recipe>> findRecipeByCategory(CategoryIdListRquestDto dto){
        return ResponseEntity.ok(recipeService.findRecipeByCategory(dto));
    }

    @GetMapping(FIND_RECIPE_BY_INGREDIENT)
    public ResponseEntity<List<Recipe>> findRecipeByIngredient(IngredientNameListRequestDto dto){
        return ResponseEntity.ok(recipeService.findRecipeByIngredient(dto));
    }

    @GetMapping(FIND_RECIPEBY_RECIPE_NAME)
    public ResponseEntity<List<Recipe>> findRecipeByRecipeName(String recipeName){
        return ResponseEntity.ok(recipeService.findRecipeByRecipeName(recipeName));
    }

    @GetMapping(FIND_RECIPE_BY_FILTER)
    public ResponseEntity<List<Recipe>> findRecipeByFilter(FindRecipeByFilterRequestDto dto){
        return ResponseEntity.ok(recipeService.findRecipeByFilter(dto));
    }

    @GetMapping(SORT_RECIPE_BY_CALORIE)
    public ResponseEntity<List<Recipe>> sortRecipeByCalorie(){
        return ResponseEntity.ok(recipeService.sortRecipeByCalorie());
    }
}
