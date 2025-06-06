package swp_project.dna_service.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.CategoryPostRequest;
import swp_project.dna_service.dto.response.CategoryPostResponse;
import swp_project.dna_service.entity.CategoryPost;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.CategoryPostMapper;
import swp_project.dna_service.repository.CategoryPostRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CategoryPostService {

    CategoryPostRepository categoryPostRepository;
    CategoryPostMapper categoryPostMapper;

    public CategoryPostResponse createCategory(CategoryPostRequest request) {
        log.info("Creating category with request: {}", request);
        
        // Check if category with same name already exists
        CategoryPost existingCategory = categoryPostRepository.findByCategoryName(request.getCategoryName());
        if (existingCategory != null) {
            log.error("Category with name {} already exists", request.getCategoryName());
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }
        
        try {
            var categoryPost = categoryPostMapper.toCategoryPost(request);
            categoryPost.setCreatedAt(new Date());
            
            if (categoryPost.getIsActive() == null) {
                categoryPost.setIsActive(true);
            }
            
            var savedCategory = categoryPostRepository.save(categoryPost);
            log.info("Successfully created category with ID: {}", savedCategory.getId());
            
            return categoryPostMapper.toCategoryPostResponse(savedCategory);
        } catch (Exception e) {
            log.error("Error creating category: {}", e.getMessage());
            throw new ServiceException("Can't create category: " + e.getMessage());
        }
    }

    public CategoryPostResponse updateCategory(String categoryId, CategoryPostRequest request) {
        log.info("Updating category with id: {} and request: {}", categoryId, request);
        
        var categoryPost = categoryPostRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Category not found with ID: {}", categoryId);
                    return new AppException(ErrorCode.CATEGORY_NOT_FOUND);
                });
                
        try {
            categoryPostMapper.updateCategoryPost(categoryPost, request);
            var savedCategory = categoryPostRepository.save(categoryPost);
            log.info("Successfully updated category with ID: {}", categoryId);
            
            return categoryPostMapper.toCategoryPostResponse(savedCategory);
        } catch (Exception e) {
            log.error("Error updating category {}: {}", categoryId, e.getMessage());
            throw new ServiceException("Can't update category: " + e.getMessage());
        }
    }

    public List<CategoryPostResponse> getAllActiveCategories() {
        log.info("Fetching all active categories");
        return categoryPostRepository.findByIsActiveTrue().stream()
                .map(categoryPostMapper::toCategoryPostResponse)
                .toList();
    }
    
    public List<CategoryPostResponse> getAllCategories() {
        log.info("Fetching all categories");
        return categoryPostRepository.findAll().stream()
                .map(categoryPostMapper::toCategoryPostResponse)
                .toList();
    }

    public CategoryPostResponse getCategoryById(String categoryId) {
        log.info("Fetching category with id: {}", categoryId);
        return categoryPostRepository.findById(categoryId)
                .map(categoryPostMapper::toCategoryPostResponse)
                .orElseThrow(() -> {
                    log.error("Category not found with ID: {}", categoryId);
                    return new AppException(ErrorCode.CATEGORY_NOT_FOUND);
                });
    }
    
    public CategoryPostResponse getCategoryByName(String categoryName) {
        log.info("Fetching category with name: {}", categoryName);
        CategoryPost categoryPost = categoryPostRepository.findByCategoryName(categoryName);
        if (categoryPost == null) {
            log.error("Category not found with name: {}", categoryName);
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return categoryPostMapper.toCategoryPostResponse(categoryPost);
    }

    public void deleteCategory(String categoryId) {
        log.info("Attempting to delete category with id: {}", categoryId);
        
        if (categoryId == null) {
            log.error("Category ID is null");
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        
        var categoryPost = categoryPostRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Category not found with ID: {}", categoryId);
                    return new AppException(ErrorCode.CATEGORY_NOT_FOUND);
                });
                
        try {
            categoryPostRepository.deleteById(categoryId);
            log.info("Successfully deleted category with id: {}", categoryId);
        } catch (Exception e) {
            log.error("Error deleting category with id {}: {}", categoryId, e.getMessage());
            throw new ServiceException("Failed to delete category: " + e.getMessage());
        }
    }
}
