package com.yemektarifi.controller;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.repository.entity.UserProfile;
import com.yemektarifi.service.UserProfileService;
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

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserProfileRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @PutMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequestDto dto){
        return ResponseEntity.ok(userProfileService.changePassword(dto));
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody FromAuthServiceForgotPasswordUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.forgotPassword(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<UserProfile> update(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

    @DeleteMapping("/delete/{token}")
    public ResponseEntity<Boolean> delete(@PathVariable String token){
        return ResponseEntity.ok(userProfileService.delete(token));
    }

    @GetMapping("/get-username-from-user-profile-to-comment/{authId}")
    public ResponseEntity<String> getUsernameFromUserProfileToComment(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.getUsernameFromUserProfileToComment(authId));
    }

    @PutMapping("/add-favorite-recipe/{token}/{recipeId}")
    public ResponseEntity<Boolean> addFavoriteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.addFavoriteRecipe(token, recipeId));
    }

    @PutMapping("/remove-favorite-recipe/{token}/{recipeId}")
    public ResponseEntity<Boolean> removeFavoriteRecipe(@PathVariable String token, @PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.removeFavoriteRecipe(token, recipeId));
    }

    @PutMapping( "remove-favorite-recipe/{recipeId}")
    public ResponseEntity<Boolean> removeUserProfileFavoriteRecipe(@PathVariable String recipeId){
        return ResponseEntity.ok(userProfileService.removeUserProfileFavoriteRecipe(recipeId));
    }

    @GetMapping("/get-username-and-id-from-user-profile-to-comment/{authId}")
    public ResponseEntity<GetUsernameAndUserIdFromUserProfileRequestDto> getUsernameAndIdFromUserProfileToComment(@PathVariable Long authId){
        return  ResponseEntity.ok(userProfileService.getUsernameAndIdFromUserProfileToComment(authId));
    }

    @PostMapping( "check-favorite-category-and-send-mail")
    public ResponseEntity<Boolean> checkFavoriteCategorySendMail(@RequestBody FromRecipeNewRecipeCheckFavoriteCategoryRequestDto dto){
        return ResponseEntity.ok(userProfileService.checkFavoriteCategorySendMail(dto));
    }
}
