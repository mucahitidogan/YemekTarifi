package com.yemektarifi.manager;

import com.yemektarifi.dto.request.ToRecipeAddCommentRequestDto;
import com.yemektarifi.dto.request.ToRecipeAddPointIdRequestDto;
import com.yemektarifi.dto.request.ToRecipeDeleteCommentRequestDto;
import com.yemektarifi.dto.request.ToRecipeDeletePointIdRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        url = "http://localhost:8083/api/v1/recipe",
        name = "comment-recipe"
)
public interface IRecipeManager {

    @GetMapping("/is-recipe-exist/{recipeId}")
    public ResponseEntity<Boolean> isRecipeExist(@PathVariable String recipeId);

    @PutMapping("/add-comment-id-to-recipe")
    public ResponseEntity<Boolean> addCommentIdToRecipe(@RequestBody ToRecipeAddCommentRequestDto dto);

    @DeleteMapping("/delete-comment")
    public ResponseEntity<Boolean> deleteComment(@RequestBody ToRecipeDeleteCommentRequestDto dto);

    @PutMapping("/add-point-id-to-recipe")
    public ResponseEntity<Boolean> addPointIdToRecipe(@RequestBody ToRecipeAddPointIdRequestDto dto);

    @DeleteMapping("/delete-pointId")
    public ResponseEntity<Boolean> deletePointIdRecipe(@RequestBody ToRecipeDeletePointIdRequestDto dto);
}
