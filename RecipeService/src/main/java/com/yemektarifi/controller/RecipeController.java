package com.yemektarifi.controller;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.dto.response.SaveRecipeResponseDto;
import com.yemektarifi.dto.response.UpdateRecipeResponseDto;
import com.yemektarifi.repository.entity.Recipe;
import com.yemektarifi.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(FIND_ALL+"with-cache")
    public ResponseEntity<List<Recipe>> findAllWithCache(){
        return ResponseEntity.ok(recipeService.findAll());
    }

    @PutMapping(UPDATE + "/{token}")
    public ResponseEntity<UpdateRecipeResponseDto> saveRecipe(@PathVariable String token, @RequestBody UpdateRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.updateRecipe(token, dto));
    }

    @DeleteMapping(DELETE_BY_ID + "/{token}/{recipeId}")
     public ResponseEntity<Boolean> deleteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(recipeService.deleteRecipe(token, recipeId));
     }

    @GetMapping("/is-recipe-exist/{recipeId}")
    public ResponseEntity<Boolean> isRecipeExist(@PathVariable String recipeId){
        return ResponseEntity.ok(recipeService.isRecipeExist(recipeId));
    }

    @PutMapping("/add-comment-id-to-recipe")
    public ResponseEntity<Boolean> addCommentIdToRecipe(@RequestBody FromCommentAddCommentRequestDto dto){
        return ResponseEntity.ok(recipeService.addCommentIdToRecipe(dto));
    }

    @DeleteMapping("/delete-comment")
    public ResponseEntity<Boolean> deleteComment(@RequestBody FromCommentDeleteCommentRequestDto dto){
        return ResponseEntity.ok(recipeService.deleteComment(dto));
    }

    @PutMapping("/add-point-id-to-recipe")
    public ResponseEntity<Boolean> addPointIdToRecipe(@RequestBody FromPointAddPointIdRequestDto dto){
        return ResponseEntity.ok(recipeService.addPointIdToRecipe(dto));
    }

    @DeleteMapping("/delete-pointId")
    public ResponseEntity<Boolean> deletePointIdRecipe(@RequestBody FromPointDeletePointIdRequestDto dto){
        return ResponseEntity.ok(recipeService.deletePointIdRecipe(dto));
    }

    @GetMapping("/find-recipe-by-category")
    public ResponseEntity<List<Recipe>> findRecipeByCategory(@RequestBody List<String> categoryIdList){
        return ResponseEntity.ok(recipeService.findRecipeByCategory(categoryIdList));
    }

    @GetMapping("/find-recipe-by-ingredient")
    public ResponseEntity<List<Recipe>> findRecipeByIngredient(List<String> ingredientNameList){
        return ResponseEntity.ok(recipeService.findRecipeByIngredient(ingredientNameList));
    }

    @GetMapping("/find-recipe-by-recipe-name")
    public ResponseEntity<List<Recipe>> findRecipeByRecipeName(String recipeName){
        return ResponseEntity.ok(recipeService.findRecipeByRecipeName(recipeName));
    }

    @GetMapping("/find-recipe-by-filter")
    public ResponseEntity<List<Recipe>> findRecipeByFilter(FindRecipeByFilterRequestDto dto){
        return ResponseEntity.ok(recipeService.findRecipeByFilter(dto));
    }

    @GetMapping("/sort-recipe-by-calorie")
    public ResponseEntity<List<Recipe>> sortRecipeByCalorie(){
        return ResponseEntity.ok(recipeService.sortRecipeByCalorie());
    }









}
