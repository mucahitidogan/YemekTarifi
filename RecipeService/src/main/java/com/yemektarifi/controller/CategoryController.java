package com.yemektarifi.controller;

import com.yemektarifi.dto.request.SaveCategoryRequestDto;
import com.yemektarifi.dto.request.UpdateCategoryRequestDto;
import com.yemektarifi.dto.response.SaveCategoryResponseDto;
import com.yemektarifi.dto.response.UpdateCategoryResponseDto;
import com.yemektarifi.repository.entity.Category;
import com.yemektarifi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yemektarifi.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(CREATE + "/{token}")
    public ResponseEntity<SaveCategoryResponseDto> saveCategory(@PathVariable String token, @RequestBody SaveCategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.saveCategory(token, dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PutMapping(UPDATE + "/{token}")
    public ResponseEntity<UpdateCategoryResponseDto> updateCategory(@PathVariable String token, @RequestBody UpdateCategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.updateCategory(token, dto));
    }


}
