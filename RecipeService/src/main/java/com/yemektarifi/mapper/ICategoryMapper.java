package com.yemektarifi.mapper;

import com.yemektarifi.dto.request.SaveCategoryRequestDto;
import com.yemektarifi.dto.request.UpdateCategoryRequestDto;
import com.yemektarifi.dto.response.SaveCategoryResponseDto;
import com.yemektarifi.dto.response.UpdateCategoryResponseDto;
import com.yemektarifi.repository.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryMapper {
    ICategoryMapper INSTANCE = Mappers.getMapper(ICategoryMapper.class);

    Category fromSaveCategoryRequestDtoToCategory(final SaveCategoryRequestDto dto);
    SaveCategoryResponseDto fromCategoryToSaveCategoryResponseDto(final Category category);

    Category fromUpdateCategoryRequestDtoToCategory(final UpdateCategoryRequestDto dto);
    UpdateCategoryResponseDto fromCategoryToUpdateCategoryResponseDto(final Category category);
}
