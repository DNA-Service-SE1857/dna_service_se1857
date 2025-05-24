package swp_project.dna_service.service;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.UserRequest;
import swp_project.dna_service.dto.response.UserResponse;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.UserMapper;
import swp_project.dna_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE , makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    public UserResponse createUser(UserRequest request) {

     if(userRepository.existsUserByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
     if(request.getPassword() == null || request.getPassword().length() < 3)
            throw new AppException(ErrorCode.PASS_ERROR_CODE);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}
