package com.yemektarifi.repository;

import com.yemektarifi.dto.request.SaveCategoryRequestDto;
import com.yemektarifi.repository.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends MongoRepository<Category, String> {

    Boolean existsByCategoryNameIgnoreCase(String categoryName);
    Boolean existsByCategoryId(String categoryId);

}
