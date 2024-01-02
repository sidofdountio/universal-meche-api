package com.meche.api;

import com.meche.model.CategoryType;
import com.meche.service.CategoryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("/api/v1/hair/category")
@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders = "*")
@RequiredArgsConstructor
public class CategoryTypeAPi {
    private final CategoryTypeService categoryTypeService;

    @GetMapping
    public ResponseEntity< List<CategoryType>> getAllCategoryTypes() {
        return new ResponseEntity<>(categoryTypeService.getCategoryList(), OK);
    }

    @PostMapping
    public ResponseEntity<CategoryType> createCategoryType(@RequestBody CategoryType categoryType) {
        try {
            CategoryType savedCategoryType = categoryTypeService.save(categoryType);
            return ResponseEntity.status(CREATED).body(savedCategoryType);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryType(@PathVariable("id") Long id) {
        categoryTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
