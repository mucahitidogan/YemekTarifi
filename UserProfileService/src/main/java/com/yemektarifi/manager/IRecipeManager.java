package com.yemektarifi.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        url = "http://localhost:8083/api/v1/recipe",
        name = "userprofile-recipe"
)
public interface IRecipeManager {

    @GetMapping("/is-recipe-exist/{recipeId}")
    public ResponseEntity<Boolean> isRecipeExist(@PathVariable String recipeId);
}
