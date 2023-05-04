package com.nhs.orgskills.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class SkillLevelTypeValidator implements ConstraintValidator<ValidateSkillLevelType, String> {
    @Override
    public boolean isValid(String skillLevelType, ConstraintValidatorContext constraintValidatorContext) {
        if(skillLevelType == null) return true;
        return Arrays.stream(SkillLevelType.values()).anyMatch(item -> item.name().equalsIgnoreCase(skillLevelType));
    }
}
