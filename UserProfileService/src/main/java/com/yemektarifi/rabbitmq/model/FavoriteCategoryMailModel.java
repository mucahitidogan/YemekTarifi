package com.yemektarifi.rabbitmq.model;

import com.yemektarifi.dto.request.ToMailSendFavoriteCategoryMailRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteCategoryMailModel implements Serializable {

    private String recipeName;
    private List<String> categoryNameList;
    private List<ToMailSendFavoriteCategoryMailRequestDto> requestDtoList;

}
