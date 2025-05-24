package swp_project.dna_service.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true)
@Getter
public enum ErrorCode {

    USER_NOT_FOUND(101,"User not found" , HttpStatusCode.valueOf(404)),
    USER_ALREADY_EXISTS(102, "User already exists" , HttpStatusCode.valueOf(409)),
    PASS_ERROR_CODE(103, "Password is invalid" , HttpStatusCode.valueOf(400)),
    ACCESS_DENIED(106, "Access denied !", HttpStatus.FORBIDDEN);

    int code ;
    String message ;
    HttpStatusCode statusCode ;

    ErrorCode(int code, String message , HttpStatusCode status) {
        this.code = code;
        this.message = message;
        this.statusCode = status;
    }

}
