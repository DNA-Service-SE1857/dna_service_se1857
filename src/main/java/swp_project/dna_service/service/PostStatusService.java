package swp_project.dna_service.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.PostRequest;
import swp_project.dna_service.dto.response.PostResponse;
import swp_project.dna_service.entity.PostStatus;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.PostMapper;
import swp_project.dna_service.repository.PostStatusRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.List;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE , makeFinal = true)
public class PostStatusService {

    PostMapper postMapper;
    PostStatusRepository postStatusRepository;
    UserRepository userRepository;


    public PostResponse createPost(PostRequest request) {
        log.info("Creating post with request: {}", request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String userId;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("User not found with ID: {}", userId);
                return new AppException(ErrorCode.USER_NOT_FOUND);
            });

        try {

            var postStatus = postMapper.toPostStatus(request);
            postStatus.setUser(user);
            postStatus.setCreatedAt(new Date());
            postStatus.setUpdatedAt(new Date());

            var savedPost = postStatusRepository.save(postStatus);
            log.info("Successfully created post with ID: {}", savedPost.getId());

            PostResponse response = postMapper.toPostResponse(savedPost);
            response.setUserId(userId);
            return response;

        } catch (Exception e) {
            log.error("Error creating post for user {}: {}", userId, e.getMessage());
            throw new ServiceException("Can't create post " + e.getMessage());
        }
    }

public PostResponse updatePost(String postId, PostRequest request) {
    log.info("Updating post with id: {} and request: {}", postId, request);
    
    // Lấy thông tin user từ token hiện tại
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUserId;
    if (authentication.getPrincipal() instanceof Jwt jwt) {
        currentUserId = jwt.getClaimAsString("userId");
        log.debug("Current user ID from JWT: {}", currentUserId);
    } else {
        throw new AppException(ErrorCode.USER_NOT_FOUND);
    }

    // Tìm bài post cần update
    var postStatus = postStatusRepository.findById(postId)
            .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));


    if (!postStatus.getUser().getId().equals(currentUserId)) {
        log.error("User {} attempted to update post {} owned by user {}", 
                currentUserId, postId, postStatus.getUser().getId());
        throw new AppException(ErrorCode.OWNER_OF_POST);
    }

    try {

        postMapper.updatePost(postStatus, request);

        postStatus.setUpdatedAt(new Date());
        var savedPost = postStatusRepository.save(postStatus);
        log.info("Successfully updated post with ID: {} by user: {}", postId, currentUserId);

        PostResponse response = postMapper.toPostResponse(savedPost);
        response.setUserId(currentUserId);

        return response;
    } catch (Exception e) {
        log.error("Error updating post {} by user {}: {}", postId, currentUserId, e.getMessage());
        throw new ServiceException("Can't update post: " + e.getMessage());
    }
}

    public List<PostResponse> getPostsByUserId(String userId) {
        log.info("Fetching posts for user with id: {}", userId);
        return postStatusRepository.findByUserId(userId).stream()
                .map(postMapper::toPostResponse)
                .peek(response -> response.setUserId(userId))
                .toList();
    }

    public List<PostResponse> getAllPosts() {
        log.info("Fetching all posts");
        return postStatusRepository.findAll().stream()
        .map(post -> {
            PostResponse response = postMapper.toPostResponse(post);
            if (post.getUser() != null) {
                response.setUserId(post.getUser().getId());
            }
            return response;
        })
        .toList();
}

    public void deletePost(String id) {
        log.info("Attempting to delete post with id: {}", id);

        if (id == null) {
            log.error("Post ID is null");
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        PostStatus postStatus = postStatusRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Post not found with ID: {}", id);
                    return new AppException(ErrorCode.POST_NOT_FOUND);
                });


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        String userId = jwt.getClaimAsString("userId");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));


        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = postStatus.getUser().getId().equals(userId);

        if (!isAdmin && !isOwner) {
            log.error("User {} is not authorized to delete post {}", userId, id);
            throw new AppException(ErrorCode.OWNER_OF_POST);
        }

        try {
            postStatusRepository.deleteById(id);
            log.info("Successfully deleted post with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting post with id {}: {}", id, e.getMessage());
            throw new ServiceException("Failed to delete post: " + e.getMessage());
        }
    }

}