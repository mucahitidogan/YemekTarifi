package com.yemektarifi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentResponseDto {

    private String username;
    private String commentText;
    private Long commentDate;
}
