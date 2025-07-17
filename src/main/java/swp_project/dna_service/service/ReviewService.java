package swp_project.dna_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.ReviewRequest;
import swp_project.dna_service.dto.response.ReviewResponse;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.entity.Review;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.ReviewMapper;
import swp_project.dna_service.repository.OrderRepository;
import swp_project.dna_service.repository.ReviewRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {

    ReviewRepository reviewRepository;
    UserRepository userRepository;
    ReviewMapper reviewMapper;
    OrderRepository orderRepository;

    public ReviewResponse createReview(ReviewRequest request , String orderId) {
        String userId = extractUserIdFromJwt();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        Review review = reviewMapper.toReview(request);
        review.setOrders(order);
        review.setUser(user);
        review.setCreatedAt(new Date());
        review.setUpdatedAt(new Date());
        
        Review savedReview = reviewRepository.save(review);

        ReviewResponse response = reviewMapper.toReviewResponse(savedReview);

        response.setUserId(userId);
        response.setOrdersId(order.getId());

        return response;
    }

    public ReviewResponse getReviewById(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        ReviewResponse response = reviewMapper.toReviewResponse(review);
        response.setUserId(review.getUser().getId());
        response.setOrdersId(review.getOrders().getId());

        return response;
    }


    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> {
                    ReviewResponse response = reviewMapper.toReviewResponse(review);
                    response.setUserId(review.getUser().getId());
                    response.setOrdersId(review.getOrders().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }


    public List<ReviewResponse> getReviewsByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(review -> {
                    ReviewResponse response = reviewMapper.toReviewResponse(review);
                    response.setUserId(review.getUser().getId());
                    response.setOrdersId(review.getOrders().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }


    public List<ReviewResponse> getReviewsByOrderId(String orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        List<Review> reviews = reviewRepository.findByOrders_Id(orderId);
        return reviews.stream()
                .map(review -> {
                    ReviewResponse response = reviewMapper.toReviewResponse(review);
                    response.setUserId(review.getUser().getId());
                    response.setOrdersId(review.getOrders().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }



    public ReviewResponse updateReview(String id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        
        String userId = extractUserIdFromJwt();
        if (!review.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        
        reviewMapper.updateReview(review, request);
        review.setUpdatedAt(new Date());
        
        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(updatedReview);
    }

    public void deleteReview(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        
        String userId = extractUserIdFromJwt();
        if (!review.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        
        reviewRepository.delete(review);
    }

    
    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}