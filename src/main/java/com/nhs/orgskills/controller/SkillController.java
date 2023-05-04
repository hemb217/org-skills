package com.nhs.orgskills.controller;

import com.nhs.orgskills.dto.EmployeeDto;
import com.nhs.orgskills.dto.SkillDto;
import com.nhs.orgskills.service.EmployeeService;
import com.nhs.orgskills.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/skill")
public class SkillController {

    private final SkillService skillService;
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompletableFuture<SkillDto> createSkill(@RequestBody @Valid SkillDto skillDto) {
        return skillService.createSkill(skillDto);
    }

    @PutMapping("/{skillId}")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<SkillDto> updateSkill(@PathVariable Long skillId, @RequestBody @Valid SkillDto skillDto) {
        return skillService.updateSkill(skillDto, skillId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<List<SkillDto>> getAllSkills(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "100") Integer pageSize) {
        return skillService.getAllSkills(pageNo, pageSize);
    }

    @GetMapping("/{skillId}/employee")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<List<EmployeeDto>> getAllEmployeesBySkillId(@PathVariable Long skillId) {
        return employeeService.getAllEmployeesBySkillId(skillId);
    }

    @GetMapping("/{skillId}")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<SkillDto> getSkillById(@PathVariable Long skillId) {
        return skillService.getSkillById(skillId);
    }

    @DeleteMapping("/{skillId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
    }

}
