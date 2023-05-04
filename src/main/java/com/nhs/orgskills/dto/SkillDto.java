package com.nhs.orgskills.dto;

import com.nhs.orgskills.validation.SkillLevelType;
import com.nhs.orgskills.validation.ValidateSkillLevelType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {
    private Long id;
    @NotBlank
    private String name;

    // custom validation
    @ValidateSkillLevelType
    private String level = String.valueOf(SkillLevelType.AWARENESS);

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level.toUpperCase();
    }
}
