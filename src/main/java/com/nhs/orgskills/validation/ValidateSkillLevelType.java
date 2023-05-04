package com.nhs.orgskills.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SkillLevelTypeValidator.class)
public @interface ValidateSkillLevelType {
    public String message() default "Skill Level should be one of Expert, Practitioner, Working, Awareness";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
