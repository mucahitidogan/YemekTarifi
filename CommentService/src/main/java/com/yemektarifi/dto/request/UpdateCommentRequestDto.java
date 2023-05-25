package com.yemektarifi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCommentRequestDto {
    @NotBlank
    private String commentId;
    @NotBlank
    private String recipeId;
    @NotBlank
    private String commentText;
}
