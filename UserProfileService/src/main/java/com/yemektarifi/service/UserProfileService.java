package com.yemektarifi.service;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.exception.ErrorType;
import com.yemektarifi.exception.UserManagerException;
import com.yemektarifi.manager.IAddressManager;
import com.yemektarifi.manager.IAuthManager;
import com.yemektarifi.manager.ICommentManager;
import com.yemektarifi.manager.IRecipeManager;
import com.yemektarifi.mapper.IUserProfileMapper;
import com.yemektarifi.rabbitmq.model.FavoriteCategoryMailModel;
import com.yemektarifi.rabbitmq.producer.FavoriteCategoryMailProducer;
import com.yemektarifi.repository.IUserProfileRepository;
import com.yemektarifi.repository.entity.UserProfile;
import com.yemektarifi.repository.enums.EStatus;
import com.yemektarifi.utility.JwtTokenProvider;
import com.yemektarifi.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {

    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final IAuthManager authManager;
    private final IAddressManager addressManager;
    private final ICommentManager commentManager;
    private final IRecipeManager recipeManager;
    private final FavoriteCategoryMailProducer favoriteCategoryMailProducer;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenProvider tokenProvider,
                              PasswordEncoder passwordEncoder, IAuthManager authManager, IAddressManager addressManager,
                              ICommentManager commentManager, IRecipeManager recipeManager, FavoriteCategoryMailProducer favoriteCategoryMailProducer){
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.addressManager = addressManager;
        this.commentManager = commentManager;
        this.recipeManager = recipeManager;
        this.favoriteCategoryMailProducer = favoriteCategoryMailProducer;
    }

    public Boolean createUser(CreateUserProfileRequestDto dto){
        try {
            save(IUserProfileMapper.INSTANCE.fromCreateUserProfileRequestDtoToUserProfile(dto));
            return true;
        }catch (Exception e){
            throw new RuntimeException("Beklenmeyen hata oluştu!");
        }
    }

    public List<UserProfile> findAll(){
        return findAll();
    }

    public Boolean changePassword(ChangePasswordRequestDto dto){
        Optional<Long> authId = tokenProvider.getIdFromToken(dto.getToken());
        if(authId.isEmpty())
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId.get());
        if(optionalUserProfile.isPresent()){
            if(passwordEncoder.matches(dto.getOldPassword(), optionalUserProfile.get().getPassword())){
                optionalUserProfile.get().setPassword(passwordEncoder.encode(dto.getNewPassword()));
                userProfileRepository.save(optionalUserProfile.get());
                authManager.changePassword(IUserProfileMapper.INSTANCE.fromUserProfileToAuthServiceChangePasswordRequestDto(optionalUserProfile.get()));
                return true;
            }else{
                throw new UserManagerException(ErrorType.OLD_PASSWORD_NOT_CORRECT);
            }
        }else{
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    public Boolean forgotPassword(FromAuthServiceForgotPasswordUpdateRequestDto dto) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(dto.getAuthId());
        if(optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        optionalUserProfile.get().setPassword(dto.getPassword());
        update(optionalUserProfile.get());
        return true;
    }

    public UserProfile update(UserProfileUpdateRequestDto dto){
        Optional<Long> authId = tokenProvider.getIdFromToken(dto.getToken());
        if(authId.isEmpty())
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId.get());
        ToCommentUpdateUsernameRequestDto toCommentDto = ToCommentUpdateUsernameRequestDto.builder()
                .userProfileId(optionalUserProfile.get().getUserProfileId())
                .oldUsername(optionalUserProfile.get().getUsername())
                .build();
        if(optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        ToAuthServiceUpdateAuthRequestDto updateAuthDto = IUserProfileMapper.INSTANCE.fromUserProfileUpdateRequestDtoToAuthServiceUpdateAuthRequestDto(dto);
        updateAuthDto.setAuthId(authId.get());
        authManager.updateAuth(updateAuthDto);
        ToAuthServiceUpdateAddressRequestDto updateAddressDto = IUserProfileMapper.INSTANCE.fromUserProfileUpdateRequestDtoToAuthServiceUpdateAddressRequestDto(dto);
        updateAddressDto.setAuthId(authId.get());
        addressManager.updateAddress(updateAddressDto);
        UserProfile userProfile = save(IUserProfileMapper.INSTANCE.updateUserProfileFromUserProfileUpdateRequestDto(dto, optionalUserProfile.get()));
        toCommentDto.setNewUsername(userProfile.getUsername());
        if(!toCommentDto.getOldUsername().equals(toCommentDto.getNewUsername()))
            commentManager.updateUsername(toCommentDto);
        return  userProfile;
    }

    public Boolean delete(String token){
        Optional<Long> authId = tokenProvider.getIdFromToken(token);
        if (authId.isEmpty())
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId.get());
        if (optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        ToCommentDeleteUserProfileInfoRequestDto userProfileInfoDto = IUserProfileMapper.INSTANCE
                .fromUserProfileToCommentDeleteUserProfileInfoRequestDto(optionalUserProfile.get());
        commentManager.deleteUserProfileInfo(userProfileInfoDto);
        optionalUserProfile.get().setStatus(EStatus.DELETED);
        update(optionalUserProfile.get());
        authManager.deleteByAuthId(authId.get());
        return true;
    }

    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId);
        if(optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        optionalUserProfile.get().setStatus(EStatus.ACTIVE);
        update(optionalUserProfile.get());
        return true;
    }

    public String getUsernameFromUserProfileToComment(Long authId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId);
        if(optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        return optionalUserProfile.get().getUsername();
    }

    public Boolean addFavoriteRecipe(String token, String recipeId){
        Optional<Long> authId = tokenProvider.getIdFromToken(token);
        if (authId.isEmpty())
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId.get());
        if (optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        if(optionalUserProfile.get().getFavoriteRecipeIds().contains(recipeId))
            throw new UserManagerException(ErrorType.THIS_RECIPE_IS_ALREADY_ADDED_YOUR_FAVORITELIST);
        if(!recipeManager.isRecipeExist(recipeId).getBody())
            throw new UserManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalUserProfile.get().getFavoriteRecipeIds().add(recipeId);
        update(optionalUserProfile.get());
        return true;
    }

    public Boolean removeFavoriteRecipe(String token, String recipeId) {
        Optional<Long> authId = tokenProvider.getIdFromToken(token);
        if (authId.isEmpty())
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId.get());
        if (optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        if(optionalUserProfile.get().getFavoriteRecipeIds().isEmpty())
            throw new UserManagerException(ErrorType.YOUR_FAVORITELIST_IS_EMPTY);
        optionalUserProfile.get().getFavoriteRecipeIds().remove(recipeId);
        update(optionalUserProfile.get());
        return true;
    }

    public Boolean removeUserProfileFavoriteRecipe(String recipeId){
        List<UserProfile> userProfileList = findAll();
        if(userProfileList.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        userProfileList.forEach(userProfile -> {
            userProfile.getFavoriteRecipeIds().remove(recipeId);
            update(userProfile);
        });
        return true;
    }

    public GetUsernameAndUserIdFromUserProfileRequestDto getUsernameAndIdFromUserProfileToComment(Long authId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findOptionalByAuthId(authId);
        if(optionalUserProfile.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        GetUsernameAndUserIdFromUserProfileRequestDto getDto = GetUsernameAndUserIdFromUserProfileRequestDto.builder()
                .username(optionalUserProfile.get().getUsername())
                .userProfileId(optionalUserProfile.get().getUserProfileId())
                .build();
        return getDto;
    }


    public Boolean checkFavoriteCategorySendMail(FromRecipeNewRecipeCheckFavoriteCategoryRequestDto dto) {
        List<String> sendMailRecipeId = new ArrayList<>();
        List<ToMailSendFavoriteCategoryMailRequestDto> sendMailList = new ArrayList<>();
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        dto.getRecipeIdList().forEach(recipeId ->{
            userProfileList.forEach(userProfile -> {
                if(userProfile.getFavoriteRecipeIds().contains(recipeId)){
                    if(!sendMailRecipeId.contains(userProfile.getUserProfileId())){
                        sendMailRecipeId.add(userProfile.getUserProfileId());
                        sendMailList.add(IUserProfileMapper.INSTANCE.fromUserProfileToMailSendFavoriteCategoryMailrequestDto(userProfile));
                    }
                }
            });
        });
        FavoriteCategoryMailModel model = FavoriteCategoryMailModel.builder()
                .requestDtoList(sendMailList)
                .categoryNameList(dto.getCategoryNameList())
                .recipeName(dto.getRecipeName())
                .build();
        System.out.println(model + "userservice");
        favoriteCategoryMailProducer.sendFavoriteRecipeCategory(model);
        System.out.println(model + "userservice222");
        return true;
    }
}
