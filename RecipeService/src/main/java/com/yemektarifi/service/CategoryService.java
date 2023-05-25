package com.yemektarifi.service;

import com.yemektarifi.dto.request.SaveCategoryRequestDto;
import com.yemektarifi.dto.request.UpdateCategoryRequestDto;
import com.yemektarifi.dto.response.SaveCategoryResponseDto;
import com.yemektarifi.dto.response.UpdateCategoryResponseDto;
import com.yemektarifi.exception.ErrorType;
import com.yemektarifi.exception.RecipeManagerException;
import com.yemektarifi.mapper.ICategoryMapper;
import com.yemektarifi.repository.ICategoryRepository;
import com.yemektarifi.repository.entity.Category;
import com.yemektarifi.repository.entity.enums.ERole;
import com.yemektarifi.utility.JwtTokenProvider;
import com.yemektarifi.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends ServiceManager<Category, String> {

    private final ICategoryRepository categoryRepository;
    private final JwtTokenProvider tokenProvider;

    public CategoryService(ICategoryRepository categoryRepository, JwtTokenProvider tokenProvider) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.tokenProvider = tokenProvider;
    }

    public SaveCategoryResponseDto saveCategory(String token, SaveCategoryRequestDto dto){
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        if(!optionalRole.get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeManagerException(ErrorType.AUTHORIZED_ERROR);
        if(categoryRepository.existsByCategoryNameIgnoreCase(dto.getCategoryName()))
            throw new RecipeManagerException(ErrorType.THIS_CATEGORY_ALREADY_SAVED);
        Category category = ICategoryMapper.INSTANCE.fromSaveCategoryRequestDtoToCategory(dto);
        save(category);
        SaveCategoryResponseDto  responseDto = ICategoryMapper.INSTANCE.fromCategoryToSaveCategoryResponseDto(category);
        return responseDto;
    }
    public List<Category> findAll(){
        return findAll();
    }
    public Boolean deleteCategoryById(String token, String categoryId){
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        if(!optionalRole.get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeManagerException(ErrorType.AUTHORIZED_ERROR);
        if(!categoryRepository.existsByCategoryId(categoryId))
            throw new RecipeManagerException(ErrorType.CATEGORY_NOT_FOUND);
        deleteById(categoryId);
        return true;
    }

    public UpdateCategoryResponseDto updateCategory(String token, UpdateCategoryRequestDto dto){
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        if(!optionalRole.get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeManagerException(ErrorType.AUTHORIZED_ERROR);
        Optional<Category> optionalCategory = findById(dto.getCategoryId());
        if(optionalCategory.isEmpty())
            throw new RecipeManagerException(ErrorType.CATEGORY_NOT_FOUND);
        Category category = ICategoryMapper.INSTANCE.fromUpdateCategoryRequestDtoToCategory(dto);
        if(categoryRepository.existsByCategoryNameIgnoreCase(category.getCategoryName()))
            throw new RecipeManagerException(ErrorType.THIS_CATEGORY_ALREADY_SAVED);
        update(category);
        UpdateCategoryResponseDto responseDto = ICategoryMapper.INSTANCE.fromCategoryToUpdateCategoryResponseDto(category);
        return responseDto;
    }

    public Boolean existsByCategoryId(String categoryId){
        return categoryRepository.existsByCategoryId(categoryId);
    }
}














