package com.yemektarifi.mapper;

import com.yemektarifi.dto.request.CreateUserProfileRequestDto;
import com.yemektarifi.dto.request.FromUserProfileServiceUpdateAuthRequestDto;
import com.yemektarifi.dto.request.RegisterRequestDto;
import com.yemektarifi.dto.response.RegisterResponseDto;
import com.yemektarifi.rabbitmq.model.ActivateCodeMailModel;
import com.yemektarifi.repository.entity.Auth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth fromRegisterRequestDtoToAuth(final RegisterRequestDto dto);

    RegisterResponseDto fromAuthToRegisterResponseDto(final Auth auth);

    CreateUserProfileRequestDto fromAuthToCreateUserProfile(final Auth auth);

    ActivateCodeMailModel fromAuthToActivateCodeMailProducer(final Auth auth);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Auth fromUserProfileServiceUpdateAuthRequestDtoToAuth(final FromUserProfileServiceUpdateAuthRequestDto dto, @MappingTarget Auth auth);
}
