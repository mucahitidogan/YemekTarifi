package com.yemektarifi.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        url = "http://localhost:8081/api/v1/comment",
        name = "recipe-comment"
)
public interface ICommentManager {

    @DeleteMapping( "delete-recipe-comments")
    public ResponseEntity<Boolean> deleteRecipeComments(@RequestBody List<String> commentList);

}
