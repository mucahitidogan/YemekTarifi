package com.yemektarifi.service;

import com.yemektarifi.dto.request.*;
import com.yemektarifi.dto.response.SaveRecipeResponseDto;
import com.yemektarifi.dto.response.UpdateRecipeResponseDto;
import com.yemektarifi.exception.ErrorType;
import com.yemektarifi.exception.RecipeManagerException;
import com.yemektarifi.manager.ICommentManager;
import com.yemektarifi.manager.IUserProfileManager;
import com.yemektarifi.mapper.IRecipeMapper;
import com.yemektarifi.repository.IRecipeRepository;
import com.yemektarifi.repository.entity.Category;
import com.yemektarifi.repository.entity.Recipe;
import com.yemektarifi.repository.entity.enums.ERole;
import com.yemektarifi.utility.JwtTokenProvider;
import com.yemektarifi.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService extends ServiceManager<Recipe, String> {

    private final IRecipeRepository recipeRepository;
    private final JwtTokenProvider tokenProvider;
    private final CategoryService categoryService;
    private final ICommentManager commentManager;
    private final IUserProfileManager userProfileManager;

    public RecipeService(IRecipeRepository recipeRepository, JwtTokenProvider tokenProvider, CategoryService categoryService, ICommentManager commentManager, IUserProfileManager userProfileManager) {
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
        this.tokenProvider = tokenProvider;
        this.categoryService = categoryService;
        this.commentManager = commentManager;
        this.userProfileManager = userProfileManager;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAll-with-cache", allEntries = true),
                    @CacheEvict(value = "sort-recipe-by-calorie", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-ingredient", allEntries = true)
            },
            put = {
                    @CachePut(value = "find-recipe-by-recipeName", key = "dto.getRecipeName()"),
                    @CachePut(value = "find-recipe-by-category", key = "dto.getCategoryIds()")
            }
    )
    public SaveRecipeResponseDto saveRecipe(String token, SaveRecipeRequestDto dto) {
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        if (!optionalRole.get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeManagerException(ErrorType.AUTHORIZED_ERROR);
        dto.getCategoryIds().forEach(x -> {
            if (!categoryService.existsByCategoryId(x))
                throw new RecipeManagerException(ErrorType.CATEGORY_NOT_FOUND);
        });
        Recipe recipe = IRecipeMapper.INSTANCE.fromSaveRecipeRequestDtoToRecipe(dto);
        save(recipe);
        List<String> recipeIdList = new ArrayList<>();
        List<String> categoryNameList = new ArrayList<>();
        List<Recipe> recipeList = recipeRepository.findAll();
        recipe.getCategoryIds().forEach(categoryId -> {
            Optional<Category> optionalCategory = categoryService.findById(categoryId);
            categoryNameList.add(optionalCategory.get().getCategoryName());
            recipeList.forEach(list_recipe -> {
                boolean isContain = list_recipe.getCategoryIds().contains(categoryId);
                if (isContain) {
                    if (!recipeIdList.contains(list_recipe.getRecipeId()))
                        recipeIdList.add(list_recipe.getRecipeId());
                }
            });
        });
        ToUserProfileNewRecipeCheckFavoriteCategoryRequestDto favoriteCategoryRequestDto =
                ToUserProfileNewRecipeCheckFavoriteCategoryRequestDto.builder()
                        .recipeIdList(recipeIdList)
                        .categoryNameList(categoryNameList)
                        .recipeName(recipe.getRecipeName())
                        .build();
        userProfileManager.checkFavoriteCategorySendMail(favoriteCategoryRequestDto);
        SaveRecipeResponseDto responseDto = IRecipeMapper.INSTANCE.fromRecipeToSaveRecipeResponseDto(recipe);
        System.out.println(responseDto);
        return responseDto;
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Cacheable(value = "findAll-with-cache")
    public List<Recipe> findAllWithCache(){
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return recipeRepository.findAll();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAll-with-cache", allEntries = true),
                    @CacheEvict(value = "sort-recipe-by-calorie", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-category", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-recipeName", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-ingredient", allEntries = true)
            }
    )
    public UpdateRecipeResponseDto updateRecipe(String token, UpdateRecipeRequestDto dto) {
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        if (!optionalRole.get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeManagerException(ErrorType.AUTHORIZED_ERROR);
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        Recipe recipe = IRecipeMapper.INSTANCE.fromUpdateRecipeRequestDtoToRecipe(dto, optionalRecipe.get());
        recipe.getTypes().removeAll(dto.getRemoveTypes());
        recipe.getTypes().addAll(dto.getAddTypes());
        recipe.getImages().removeAll(dto.getRemoveImages());
        recipe.getIngredients().removeAll(dto.getRemoveIngredients());
        recipe.getIngredients().addAll(dto.getAddIngredients());
        dto.getRemoveCategoryIds().forEach(categoryId -> {
            if (recipe.getCategoryIds().contains(categoryId))
                throw new RecipeManagerException(ErrorType.THIS_CATEGORY_ALREADY_SAVED);
            recipe.getCategoryIds().add(categoryId);
        });
        dto.getAddCategoryIds().forEach(categoryId -> {
            if (!categoryService.existsByCategoryId(categoryId))
                throw new RecipeManagerException(ErrorType.CATEGORY_NOT_FOUND);
            recipe.getCategoryIds().add(categoryId);
        });
        update(recipe);
        UpdateRecipeResponseDto responseDto = IRecipeMapper.INSTANCE.fromRecipeToUpdateRecipeResponseDto(recipe);
        return responseDto;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAll-with-cache", allEntries = true),
                    @CacheEvict(value = "sort-recipe-by-calorie", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-category", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-recipeName", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-ingredient", allEntries = true)
            }
    )
    public Boolean deleteRecipe(String token, String recipeId) {
        Optional<String> optionalRole = tokenProvider.getRoleFromToken(token);
        if (!optionalRole.get().equals(String.valueOf(ERole.ADMIN)))
            throw new RecipeManagerException(ErrorType.AUTHORIZED_ERROR);
        Optional<Recipe> optionalRecipe = findById(recipeId);
        if (optionalRecipe.isEmpty())
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        if (!optionalRecipe.get().getCommentIds().isEmpty())
            commentManager.deleteRecipeComments(optionalRecipe.get().getCommentIds());
        userProfileManager.removeUserProfileFavoriteRecipe(recipeId);
        delete(optionalRecipe.get());
        return true;
    }

    public Boolean isRecipeExist(String recipeId) {
        Optional<Recipe> optionalRecipe = findById(recipeId);
        if (optionalRecipe.isEmpty())
            return false;
        return true;
    }


    @Caching(
            evict = {
                    @CacheEvict(value = "findAll-with-cache", allEntries = true),
                    @CacheEvict(value = "sort-recipe-by-calorie", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-category", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-recipeName", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-ingredient", allEntries = true)
            }
    )
    public Boolean addCommentIdToRecipe(FromCommentAddCommentRequestDto dto) {
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        if (optionalRecipe.isEmpty())
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getCommentIds().add(dto.getCommentId());
        update(optionalRecipe.get());
        return true;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAll-with-cache", allEntries = true),
                    @CacheEvict(value = "sort-recipe-by-calorie", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-category", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-recipeName", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-ingredient", allEntries = true)
            }
    )
    public Boolean deleteComment(FromCommentDeleteCommentRequestDto dto) {
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        if (optionalRecipe.isEmpty())
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getCommentIds().remove(dto.getCommentId());
        update(optionalRecipe.get());
        return true;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAll-with-cache", allEntries = true),
                    @CacheEvict(value = "sort-recipe-by-calorie", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-category", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-recipeName", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-ingredient", allEntries = true)
            }
    )
    public Boolean addPointIdToRecipe(FromPointAddPointIdRequestDto dto) {
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        if (optionalRecipe.isEmpty())
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getPointId().add(dto.getPointId());
        update(optionalRecipe.get());
        return true;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAll-with-cache", allEntries = true),
                    @CacheEvict(value = "sort-recipe-by-calorie", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-category", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-recipeName", allEntries = true),
                    @CacheEvict(value = "find-recipe-by-ingredient", allEntries = true)
            }
    )
    public Boolean deletePointIdRecipe(FromPointDeletePointIdRequestDto dto) {
        Optional<Recipe> optionalRecipe = findById(dto.getRecipeId());
        if (optionalRecipe.isEmpty())
            throw new RecipeManagerException(ErrorType.RECIPE_NOT_FOUND);
        optionalRecipe.get().getPointId().remove(dto.getPointId());
        update(optionalRecipe.get());
        return true;
    }

    @Cacheable(value = "find-recipe-by-category", key = "#categoryIdList")
    public List<Recipe> findRecipeByCategory(List<String> categoryIdList) {
        List<Recipe> recipeList = recipeRepository.findAll();
        Set<Recipe> addRecipeList = new HashSet<>();
        if (!categoryIdList.isEmpty() && !categoryIdList.get(0).isEmpty()) {
            categoryIdList.forEach(categoryId -> {
                recipeList.forEach(recipe -> {
                    if (recipe.getCategoryIds().contains(categoryId))
                        addRecipeList.add(recipe);
                });
            });
        } else {
            return recipeList;
        }
        List<Recipe> finalRecipeList = new ArrayList<>(addRecipeList);
        recipeList.removeAll(finalRecipeList);
        return finalRecipeList;
    }

    @Cacheable(value = "find-recipe-by-ingredient", key = "#ingredientNameList")
    public List<Recipe> findRecipeByIngredient(List<String> ingredientNameList) {
        List<Recipe> recipeList = recipeRepository.findAll();
        List<Recipe> removeRecipeList = new ArrayList<>();
        if (!ingredientNameList.isEmpty()) {
            ingredientNameList.forEach(ingredientName -> {
                recipeList.forEach(recipe -> {
                    recipe.getIngredients().forEach(ingredient -> {
                        if (!ingredientName.isEmpty() && !ingredient.getProductName().equals(ingredientName))
                            removeRecipeList.add(recipe);
                    });
                });
            });
        } else {
            return recipeList;
        }
        recipeList.removeAll(removeRecipeList);
        return recipeList;
    }

    @Cacheable(value = "find-recipe-by-recipeName", key = "#recipeName")
    public List<Recipe> findRecipeByRecipeName(String recipeName) {
        List<Recipe> recipeList = findAll();
        List<Recipe> findByRecipeNameList = recipeRepository.findByRecipeNameIgnoreCaseLike(recipeName);
        if (recipeName.isEmpty()){
            return recipeList;
        }
        else{
            return findByRecipeNameList;
        }
    }

    public List<Recipe> findRecipeByFilter(FindRecipeByFilterRequestDto dto) {
        List<Recipe> finalRecipeList = new ArrayList<>();
        List<Recipe> recipeList = recipeRepository.findAll();
        for (Recipe recipe : recipeList) {
            if (findRecipeByCategory(dto.getCategoryIds()).contains(recipe) &&
                    findRecipeByIngredient(dto.getIngredientNames()).contains(recipe) &&
                    findRecipeByRecipeName(dto.getRecipeName()).contains(recipe))
                finalRecipeList.add(recipe);
        }
        return finalRecipeList;
    }

    @Cacheable(value = "sort-recipe-by-calorie")
    public List<Recipe> sortRecipeByCalorie(){
        List<Recipe> recipeList = recipeRepository.findAll();
        recipeList.sort(
                Comparator.comparing(Recipe::getNutritionalValue, (n1, n2) -> {
                    return n1.getCalorie().compareTo(n2.getCalorie());
                })
        );
        return recipeList;
    }
}













