package com.yemektarifi.service;

import com.yemektarifi.dto.request.CreatePointRequestDto;
import com.yemektarifi.dto.request.GetUsernameAndUserIdFromUserProfileRequestDto;
import com.yemektarifi.dto.request.ToRecipeAddPointIdRequestDto;
import com.yemektarifi.dto.request.ToRecipeDeletePointIdRequestDto;
import com.yemektarifi.dto.response.CreatePointResponseDto;
import com.yemektarifi.exception.CommentManagerException;
import com.yemektarifi.exception.ErrorType;
import com.yemektarifi.manager.IRecipeManager;
import com.yemektarifi.manager.IUserProfileManager;
import com.yemektarifi.mapper.IPointMapper;
import com.yemektarifi.repository.IPointRepository;
import com.yemektarifi.repository.entity.Point;
import com.yemektarifi.repository.entity.enums.ERole;
import com.yemektarifi.utility.JwtTokenProvider;
import com.yemektarifi.utility.ServiceManager;
import org.springframework.boot.web.server.PortInUseException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointService extends ServiceManager<Point, String> {

    private final IPointRepository pointRepository;
    private final JwtTokenProvider tokenProvider;
    private final IRecipeManager recipeManager;
    private final IPointMapper pointMapper;
    private final IUserProfileManager userProfileManager;

    public PointService(IPointRepository pointRepository, JwtTokenProvider tokenProvider, IRecipeManager recipeManager, IPointMapper pointMapper, IUserProfileManager userProfileManager) {
        super(pointRepository);
        this.pointRepository = pointRepository;
        this.tokenProvider = tokenProvider;
        this.recipeManager = recipeManager;
        this.pointMapper = pointMapper;
        this.userProfileManager = userProfileManager;
    }

    public CreatePointResponseDto createPoint(String token, CreatePointRequestDto dto){
        Optional<Long> optionalAuthId = tokenProvider.getIdFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        if (!recipeManager.isRecipeExist(dto.getRecipeId()).getBody())
            throw new CommentManagerException(ErrorType.RECIPE_NOT_FOUND);
        Point point = pointMapper.INSTANCE.fromCreatePointRequestDtoToPoint(dto);
        GetUsernameAndUserIdFromUserProfileRequestDto getDto = userProfileManager.getUsernameAndIdFromUserProfileToComment(optionalAuthId.get()).getBody();
        point.setUserProfileId(getDto.getUserProfileId());
        Optional<Point> optionalPoint = pointRepository.findByRecipeIdAndUserProfileId(point.getRecipeId(), point.getUserProfileId());
        if(optionalPoint.isEmpty()){
            save(point);
            ToRecipeAddPointIdRequestDto addPointDto = ToRecipeAddPointIdRequestDto.builder()
                    .pointId(point.getPointId())
                    .recipeId(point.getRecipeId())
                    .build();
            recipeManager.addPointIdToRecipe(addPointDto);
        }
        else{
            optionalPoint.get().setPoint(dto.getPoint());
            update(optionalPoint.get());
        }
        CreatePointResponseDto responseDto = pointMapper.INSTANCE.fromPointToCreatePointResponseDto(point);
        return responseDto;
    }

    public List<Point> findAll(){
        return findAll();
    }

    public List<Point> findByUserProfileId(String userProfileId) {
        return pointRepository.findByUserProfileId(userProfileId);
    }

    public Boolean deletePoint(String token, String pointId){
        Optional<Long> optionalAuthId = tokenProvider.getIdFromToken(token);
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        Optional<Point> optionalPoint = findById(pointId);
        if(optionalPoint.isEmpty())
            throw new CommentManagerException(ErrorType.POINT_NOT_FOUND);
        GetUsernameAndUserIdFromUserProfileRequestDto getUserIdDto =  userProfileManager.getUsernameAndIdFromUserProfileToComment(optionalAuthId.get()).getBody();
        if(optionalPoint.get().getUserProfileId().equals(getUserIdDto.getUserProfileId()) || optionalRole.get().equals(String.valueOf(ERole.ADMIN))){
            deleteById(pointId);
            ToRecipeDeletePointIdRequestDto deletePointIdDto = ToRecipeDeletePointIdRequestDto.builder()
                    .recipeId(optionalPoint.get().getRecipeId())
                    .pointId(optionalPoint.get().getPointId())
                    .build();
            recipeManager.deletePointIdRecipe(deletePointIdDto);
            return true;
        }else {
            throw new CommentManagerException(ErrorType.AUTHORIZED_ERROR);
        }
    }
}














