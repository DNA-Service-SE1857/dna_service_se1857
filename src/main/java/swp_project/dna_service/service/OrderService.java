package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.OrderMapper;
import swp_project.dna_service.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    UserRepository userRepository;
    OrderMapper orderMapper;      


    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}
