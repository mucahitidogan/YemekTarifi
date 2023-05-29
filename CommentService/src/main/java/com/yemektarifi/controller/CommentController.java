package com.yemektarifi.controller;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.dto.response.CreateCommentResponseDto;
import com.yemektarifi.dto.response.UpdateCommentResponseDto;
import com.yemektarifi.repository.entity.Comment;
import com.yemektarifi.service.CommentService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yemektarifi.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
public class CommentController {
    private final CommentService commentService;

    @PostMapping(CREATE+"/{token}")
    public ResponseEntity<CreateCommentResponseDto> createComment(@PathVariable String token, @RequestBody CreateCommentRequestDto dto){
        return ResponseEntity.ok(commentService.createComment(token, dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Comment>> findAll(){
        return ResponseEntity.ok(commentService.findAll());
    }

    @DeleteMapping(DELETE_COMMENT+"/{token}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable String token, @RequestBody DeleteCommentRequestDto dto){
        return ResponseEntity.ok(commentService.deleteComment(token, dto));
    }

    @PutMapping(UPDATE_COMMENT+"/{token}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(@PathVariable String token, @RequestBody UpdateCommentRequestDto dto){
        return ResponseEntity.ok(commentService.updateComment(token, dto));
    }


    @Hidden
    @DeleteMapping( DELETE_RECIPE_COMMENTS)
    public ResponseEntity<Boolean> deleteRecipeComments(@RequestBody List<String> commentList){
        return ResponseEntity.ok(commentService.deleteRecipeComments(commentList));
    }

    @Hidden
    @PutMapping(UPDATE_USERNAME)
    public ResponseEntity<Boolean> updateUsername(@RequestBody FromUserProfileUpdateUsernameRequestDto dto){
        return ResponseEntity.ok(commentService.updateUsername(dto));
    }

    @Hidden
    @DeleteMapping(DELETE_USER_PROFILE_INFO)
    public ResponseEntity<Boolean> deleteUserProfileInfo(@RequestBody FromUserProfileDeleteUserProfileInfoRequestDto dto){
        return ResponseEntity.ok(commentService.deleteUserProfileInfo(dto));
    }
}
