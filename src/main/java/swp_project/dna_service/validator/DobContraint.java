package swp_project.dna_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DobValidator.class)
public @interface DobContraint {

    String message() default "DOB_INVALID";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
