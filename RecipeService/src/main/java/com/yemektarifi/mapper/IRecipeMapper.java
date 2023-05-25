package com.yemektarifi.mapper;

import com.yemektarifi.dto.request.SaveRecipeRequestDto;
import com.yemektarifi.dto.request.UpdateRecipeRequestDto;
import com.yemektarifi.dto.response.SaveRecipeResponseDto;
import com.yemektarifi.dto.response.UpdateRecipeResponseDto;
import com.yemektarifi.repository.entity.Recipe;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRecipeMapper {
    IRecipeMapper INSTANCE = Mappers.getMapper(IRecipeMapper.class);

    Recipe fromSaveRecipeRequestDtoToRecipe(final SaveRecipeRequestDto dto);
    SaveRecipeResponseDto fromRecipeToSaveRecipeResponseDto(final Recipe recipe);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Recipe fromUpdateRecipeRequestDtoToRecipe(final UpdateRecipeRequestDto dto, @MappingTarget Recipe recipe);
    UpdateRecipeResponseDto fromRecipeToUpdateRecipeResponseDto(final Recipe recipe);
}
