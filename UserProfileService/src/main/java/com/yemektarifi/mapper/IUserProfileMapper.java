package com.yemektarifi.mapper;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.repository.entity.UserProfile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);


    UserProfile fromCreateUserProfileRequestDtoToUserProfile(final CreateUserProfileRequestDto dto);

    ToAuthServiceChangePasswordRequestDto fromUserProfileToAuthServiceChangePasswordRequestDto(final UserProfile userProfile);

    ToAuthServiceUpdateAuthRequestDto fromUserProfileUpdateRequestDtoToAuthServiceUpdateAuthRequestDto(final UserProfileUpdateRequestDto dto);

    ToAuthServiceUpdateAddressRequestDto fromUserProfileUpdateRequestDtoToAuthServiceUpdateAddressRequestDto(final UserProfileUpdateRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile updateUserProfileFromUserProfileUpdateRequestDto(UserProfileUpdateRequestDto dto, @MappingTarget UserProfile userProfile);

    ToCommentDeleteUserProfileInfoRequestDto fromUserProfileToCommentDeleteUserProfileInfoRequestDto(final UserProfile userProfile);

    ToMailSendFavoriteCategoryMailRequestDto fromUserProfileToMailSendFavoriteCategoryMailrequestDto(final UserProfile userProfile);
}
