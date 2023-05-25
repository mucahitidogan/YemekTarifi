package com.yemektarifi.mapper;

import com.yemektarifi.dto.request.FromUserProfileServiceUpdateAddressRequestDto;
import com.yemektarifi.repository.entity.Address;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAddressMapper {
    IAddressMapper INSTANCE = Mappers.getMapper(IAddressMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address FromUserProfileServiceUpdateAddressRequestDtoToAddressUpdate(final FromUserProfileServiceUpdateAddressRequestDto dto, @MappingTarget Address address);
    Address FromUserProfileServiceUpdateAddressRequestDtoToAddressSave(final FromUserProfileServiceUpdateAddressRequestDto dto);

}
