package swp_project.dna_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DobValidator implements ConstraintValidator<DobContraint, LocalDate> {

    private int min;
    @Override
    public void initialize(DobContraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }


    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
        if (dob == null) {
            return true;
        }

        long years = ChronoUnit.YEARS.between(dob, LocalDate.now());

        return years >= min ;
    }
}
