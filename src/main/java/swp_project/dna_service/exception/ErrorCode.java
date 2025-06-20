package swp_project.dna_service.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true)
@Getter
public enum ErrorCode {

    USER_NOT_FOUND(101,"User not found" , HttpStatusCode.valueOf(404)),
    USER_ALREADY_EXISTS(102, "User already exists" , HttpStatusCode.valueOf(409)),
    PASS_ERROR_CODE(103, "Password is invalid" , HttpStatusCode.valueOf(400)),
    USERNAME_ERROR_CODE(104, "Username is invalid" , HttpStatusCode.valueOf(400)),
    ACCESS_DENIED(98, "Access denied !", HttpStatus.FORBIDDEN),
    JWT_SIGNING_ERROR(97, "JWT signing error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(105, "Unauthenticated (Username or Password is invalid)", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(106 ,"Token not access" , HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(107 , "Token is exists " , HttpStatus.UNAUTHORIZED),
    EMAIL_INVALID(108, "Email is invalid", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(109, "Role not found", HttpStatus.NOT_FOUND),
    LOGOUT_ERROR(110, "Logout error", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_ALREADY_EXISTS(111, "Token already exists", HttpStatus.CONFLICT),
    POST_NOT_FOUND(112, "Post not found", HttpStatus.NOT_FOUND),
    POST_ALREADY_EXISTS(113, "Post already exists", HttpStatus.CONFLICT),
    OWNER_OF_POST(114, "You are not the owner of the post", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(115, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXISTS(116, "Category already exists", HttpStatus.CONFLICT),
    NOTIFICATION_NOT_FOUND(117, "Notification not found", HttpStatus.NOT_FOUND),
    SERVICE_CREATION_FAILED(118, "Service creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_NOT_FOUND(119, "Service not found", HttpStatus.NOT_FOUND),
    SERVICE_UPDATE_FAILED(120, "Service update failed", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCTOR_NOT_FOUND(121, "Doctor not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(122, "Unauthorized access", HttpStatus.UNAUTHORIZED),
    DOB_INVALID(99, "Date of birth need > 15 year old", HttpStatus.BAD_REQUEST);

    int code ;
    String message ;
    HttpStatusCode statusCode ;

    ErrorCode(int code, String message , HttpStatusCode status) {
        this.code = code;
        this.message = message;
        this.statusCode = status;
    }

}
