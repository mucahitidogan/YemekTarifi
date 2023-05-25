package com.yemektarifi.mapper;

import com.yemektarifi.dto.request.CreateCommentRequestDto;
import com.yemektarifi.dto.request.ToRecipeDeleteCommentRequestDto;
import com.yemektarifi.dto.request.UpdateCommentRequestDto;
import com.yemektarifi.dto.response.CreateCommentResponseDto;
import com.yemektarifi.dto.response.UpdateCommentResponseDto;
import com.yemektarifi.repository.entity.Comment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICommentMapper {
    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);

    Comment fromCreateCommentRequestDtoToComment(final CreateCommentRequestDto dto);
    CreateCommentResponseDto fromCommentToCreateCommentResponseDto(final Comment comment);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment fromUpdateCommentRequestDtoToComment(final UpdateCommentRequestDto dto, @MappingTarget Comment comment);
    UpdateCommentResponseDto fromCommentToUpdateCommentResponseDto(final Comment Comment);
    ToRecipeDeleteCommentRequestDto fromCommentToRecipeDeleteCommentRequestDto(final Comment Comment);
}
