package com.yemektarifi.service;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.dto.response.CreateCommentResponseDto;
import com.yemektarifi.dto.response.UpdateCommentResponseDto;
import com.yemektarifi.exception.CommentManagerException;
import com.yemektarifi.exception.ErrorType;
import com.yemektarifi.manager.IRecipeManager;
import com.yemektarifi.manager.IUserProfileManager;
import com.yemektarifi.mapper.ICommentMapper;
import com.yemektarifi.repository.ICommentRepository;
import com.yemektarifi.repository.entity.Comment;
import com.yemektarifi.repository.entity.Point;
import com.yemektarifi.repository.entity.enums.ERole;
import com.yemektarifi.utility.JwtTokenProvider;
import com.yemektarifi.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment, String> {

    private final ICommentRepository commentRepository;
    private final JwtTokenProvider tokenProvider;
    private final IUserProfileManager userProfileManager;
    private final IRecipeManager recipeManager;
    private final PointService pointService;

    public CommentService(ICommentRepository commentRepository, JwtTokenProvider tokenProvider, IUserProfileManager userProfileManager, IRecipeManager recipeManager, PointService pointService) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.tokenProvider = tokenProvider;
        this.userProfileManager = userProfileManager;
        this.recipeManager = recipeManager;
        this.pointService = pointService;
    }

    public CreateCommentResponseDto createComment(String token, CreateCommentRequestDto dto) {
        Optional<Long> authId = tokenProvider.getIdFromToken(token);
        GetUsernameAndUserIdFromUserProfileRequestDto getDto = userProfileManager.getUsernameAndIdFromUserProfileToComment(authId.get()).getBody();
        if (getDto.getUsername().equals(null))
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        Comment comment = ICommentMapper.INSTANCE.fromCreateCommentRequestDtoToComment(dto);
        comment.setUsername(getDto.getUsername());
        comment.setUserProfileId(getDto.getUserProfileId());
        if (!recipeManager.isRecipeExist(dto.getRecipeId()).getBody())
            throw new CommentManagerException(ErrorType.RECIPE_NOT_FOUND);
        comment.setRecipeId(dto.getRecipeId());
        save(comment);
        ToRecipeAddCommentRequestDto toRecipeAddCommentRequestDto = ToRecipeAddCommentRequestDto.builder()
                .recipeId(dto.getRecipeId())
                .commentId(comment.getCommentId())
                .build();
        recipeManager.addCommentIdToRecipe(toRecipeAddCommentRequestDto);
        CreateCommentResponseDto responseDto = ICommentMapper.INSTANCE.fromCommentToCreateCommentResponseDto(comment);
        return responseDto;
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public Boolean deleteComment(String token, DeleteCommentRequestDto dto) {
        Optional<Long> authId = tokenProvider.getIdFromToken(token);
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        String username = userProfileManager.getUsernameFromUserProfileToComment(authId.get()).getBody();
        Optional<Comment> optionalComment = findById(dto.getCommentId());
        if (optionalComment.isEmpty())
            throw new CommentManagerException(ErrorType.COMMENT_NOT_FOUND);
        if (!recipeManager.isRecipeExist(dto.getRecipeId()).getBody())
            throw new CommentManagerException(ErrorType.RECIPE_NOT_FOUND);
        if (username.equals(optionalComment.get().getUsername())  || optionalRole.get().equals(String.valueOf(ERole.ADMIN))){
            ToRecipeDeleteCommentRequestDto deleteDto = ICommentMapper.INSTANCE.fromCommentToRecipeDeleteCommentRequestDto(optionalComment.get());
            recipeManager.deleteComment(deleteDto);
            deleteById(dto.getCommentId());
            return true;
        }else{
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    public UpdateCommentResponseDto updateComment(String token, UpdateCommentRequestDto dto) {
        Optional<Long> optionalAuthId = tokenProvider.getIdFromToken(token);
        if (optionalAuthId.isEmpty())
            throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
        if (!recipeManager.isRecipeExist(dto.getRecipeId()).getBody())
            throw new CommentManagerException(ErrorType.RECIPE_NOT_FOUND);
        Optional<Comment> optionalComment = findById(dto.getCommentId());
        if (optionalComment.isEmpty())
            throw new CommentManagerException(ErrorType.COMMENT_NOT_FOUND);
        Comment comment = ICommentMapper.INSTANCE.fromUpdateCommentRequestDtoToComment(dto, optionalComment.get());
        comment.setCommentDate(System.currentTimeMillis());
        update(comment);
        UpdateCommentResponseDto responseDto = ICommentMapper.INSTANCE.fromCommentToUpdateCommentResponseDto(comment);
        return responseDto;
    }

    public Boolean deleteRecipeComments(List<String> commentList) {
        commentList.forEach(commentId -> {
            deleteById(commentId);
        });
        return true;
    }

    public Boolean updateUsername(FromUserProfileUpdateUsernameRequestDto dto) {
        List<Comment> commentList = commentRepository.findByUserProfileId(dto.getUserProfileId());
        if (!commentList.isEmpty()) {
            commentList.forEach(comment -> {
                comment.setUsername(dto.getNewUsername());
                update(comment);
            });
        }
        return true;
    }

    public Boolean deleteUserProfileInfo(FromUserProfileDeleteUserProfileInfoRequestDto dto) {
        List<Comment> commentList = commentRepository.findByUserProfileId(dto.getUserProfileId());
        if(!commentList.isEmpty())
            commentList.forEach(comment -> {
                deleteById(comment.getCommentId());
                recipeManager.deleteComment(ICommentMapper.INSTANCE.fromCommentToRecipeDeleteCommentRequestDto(comment));
            });
        List<Point> pointList = pointService.findByUserProfileId(dto.getUserProfileId());
        if(!pointList.isEmpty())
            pointList.forEach(point -> {
                pointService.deleteById(point.getPointId());
                ToRecipeDeletePointIdRequestDto toRecipeDeletePointIdRequestDto = ToRecipeDeletePointIdRequestDto.builder()
                        .recipeId(point.getRecipeId())
                        .pointId(point.getPointId())
                        .build();
                recipeManager.deletePointIdRecipe(toRecipeDeletePointIdRequestDto);
            });
        return true;
    }


}














