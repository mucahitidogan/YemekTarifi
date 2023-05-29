package com.yemektarifi.controller;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.repository.entity.UserProfile;
import com.yemektarifi.service.UserProfileService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yemektarifi.constants.ApiUrls.*;

@RestController
@RequestMapping(USERPROFILE)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Hidden
    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserProfileRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequestDto dto){
        return ResponseEntity.ok(userProfileService.changePassword(dto));
    }

    @Hidden
    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody FromAuthServiceForgotPasswordUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.forgotPassword(dto));
    }

    @PutMapping(UPDATE+"/{token}")
    public ResponseEntity<UserProfile> update(@PathVariable String token, @RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(token, dto));
    }

    @Hidden
    @GetMapping(ACTIVATE_STATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

    @DeleteMapping(DELETE+"/{token}")
    public ResponseEntity<Boolean> delete(@PathVariable String token){
        return ResponseEntity.ok(userProfileService.delete(token));
    }

    @Hidden
    @GetMapping(GET_USERNAME_FROM_USER_PROFILE_TO_COMMENT+"/{authId}")
    public ResponseEntity<String> getUsernameFromUserProfileToComment(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.getUsernameFromUserProfileToComment(authId));
    }

    @PutMapping(ADD_FAVORITE_RECIPE+"/{token}/{recipeId}")
    public ResponseEntity<Boolean> addFavoriteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.addFavoriteRecipe(token, recipeId));
    }

    @PutMapping(REMOVE_FAVORITE_RECIPE+"/{token}/{recipeId}")
    public ResponseEntity<Boolean> removeFavoriteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.removeFavoriteRecipe(token, recipeId));
    }

    @Hidden
    @PutMapping( REMOVE_FAVORITE_RECIPE+"/{recipeId}")
    public ResponseEntity<Boolean> removeUserProfileFavoriteRecipe(@PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.removeUserProfileFavoriteRecipe(recipeId));
    }

    @Hidden
    @GetMapping(GET_USERNAME_AND_ID_FROM_USER_PROFILE_TO_COMMENT+"/{authId}")
    public ResponseEntity<GetUsernameAndUserIdFromUserProfileRequestDto> getUsernameAndIdFromUserProfileToComment(@PathVariable Long authId){
        return  ResponseEntity.ok(userProfileService.getUsernameAndIdFromUserProfileToComment(authId));
    }

    @Hidden
    @PostMapping( CHECK_FAVORITE_CATEGORY_AND_SEND_MAIL)
    public ResponseEntity<Boolean> checkFavoriteCategorySendMail(@RequestBody FromRecipeNewRecipeCheckFavoriteCategoryRequestDto dto){
        return ResponseEntity.ok(userProfileService.checkFavoriteCategorySendMail(dto));
    }
}
