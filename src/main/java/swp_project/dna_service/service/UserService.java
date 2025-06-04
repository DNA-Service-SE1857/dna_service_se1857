package swp_project.dna_service.service;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.IntrospectRequest;
import swp_project.dna_service.dto.request.UserCreationRequest;
import swp_project.dna_service.dto.request.UserUpdateRequest;
import swp_project.dna_service.dto.response.IntrospectResponse;
import swp_project.dna_service.dto.response.UserResponse;
import swp_project.dna_service.entity.Role;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.UserMapper;
import swp_project.dna_service.mapper.UserMapperImpl;
import swp_project.dna_service.repository.RoleRepository;
import swp_project.dna_service.repository.UserRepository;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE , makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {

     if(userRepository.existsUserByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
     if(request.getPassword() == null || request.getPassword().length() < 3)
            throw new AppException(ErrorCode.PASS_ERROR_CODE);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var userRole = roleRepository.findById("ROLE_USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRoles(Set.of(userRole));

        user.setCreatedAt(new java.util.Date());
        user.setUpdatedAt(new java.util.Date());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (userRepository.existsUserByUsername(request.getUsername()) &&
                !user.getUsername().equals(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setCreatedAt(new java.util.Date());
        user.setUpdatedAt(new java.util.Date());

        userRepository.save(user);
        log.info("User with id {} updated successfully", id);

        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUser() {
        if (userRepository.count() == 0) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    public UserResponse getProfile() {

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findUserByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    public UserResponse createStaff(UserCreationRequest request) {
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        if (request.getPassword() == null || request.getPassword().length() < 3) {
            throw new AppException(ErrorCode.PASS_ERROR_CODE);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById("ROLE_STAFF")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));

        user.setRoles(roles);

        user.setCreatedAt(new java.util.Date());
        user.setUpdatedAt(new java.util.Date());
        userRepository.save(user);
        log.info("Staff user with username {} created successfully", request.getUsername());

        return userMapper.toUserResponse(user);

    }



}