package br.edu.ifpb.pweb2.estagiotrack.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

    public String message() default "Este valor já existe";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
