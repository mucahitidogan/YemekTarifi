package com.yemektarifi.mapper;

import com.yemektarifi.dto.request.CreatePointRequestDto;
import com.yemektarifi.dto.response.CreatePointResponseDto;
import com.yemektarifi.repository.entity.Point;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPointMapper {
    IPointMapper INSTANCE = Mappers.getMapper(IPointMapper.class);

    Point fromCreatePointRequestDtoToPoint(final CreatePointRequestDto dto);

    CreatePointResponseDto fromPointToCreatePointResponseDto(final Point point);
}
