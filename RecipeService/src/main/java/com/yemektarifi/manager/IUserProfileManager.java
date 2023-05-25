package com.yemektarifi.manager;

import com.yemektarifi.dto.request.ToUserProfileNewRecipeCheckFavoriteCategoryRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        url = "http://localhost:8084/api/v1/user-profile",
        name = "recipe-userprofile"
)
public interface IUserProfileManager {

    @PutMapping( "remove-favorite-recipe/{recipeId}")
    public ResponseEntity<Boolean> removeUserProfileFavoriteRecipe(@PathVariable String recipeId);

    @PostMapping( "check-favorite-category-and-send-mail")
    public ResponseEntity<Boolean> checkFavoriteCategorySendMail(@RequestBody ToUserProfileNewRecipeCheckFavoriteCategoryRequestDto dto);
}
