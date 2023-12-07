package com.meche.service;

import com.meche.model.CategoryType;
import com.meche.repo.CategoryTypeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryTypeService {
    private final CategoryTypeRepo categoryTypeRepo;

    public List<CategoryType> getCategoryList() {
        return categoryTypeRepo.findAll();
    }

    public CategoryType save(CategoryType categoryType) {
        Optional<CategoryType> productCategoryExist = categoryTypeRepo.findByName(categoryType.getName());
        if (productCategoryExist.isPresent()) {
            throw new IllegalStateException("");
        }
        return categoryTypeRepo.save(categoryType);
    }

    public void delete(Long id) {
        categoryTypeRepo.deleteById(id);
    }
}
