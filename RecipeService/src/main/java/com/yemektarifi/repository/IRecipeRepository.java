package com.yemektarifi.repository;

import com.yemektarifi.repository.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByRecipeNameIgnoreCaseLike(String recipeName);
}
