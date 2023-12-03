package com.meche.api;

import com.meche.model.CategoryType;
import com.meche.service.CategoryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.*;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("/api/v1/hair/category")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class CategoryTypeAPi {
    private final CategoryTypeService categoryTypeService;

    @GetMapping
    public ResponseEntity< List<CategoryType>> getAllCategoryTypes() {
        List<CategoryType> categoryList = categoryTypeService.getCategoryList();
        return new ResponseEntity<List<CategoryType>>(categoryList, OK);
    }

    @PostMapping
    public ResponseEntity<CategoryType> createCategoryType(@RequestBody CategoryType categoryType) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new ResponseEntity<>(categoryTypeService.save(categoryType),CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryType(@PathVariable("id") Long id) {
        categoryTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
