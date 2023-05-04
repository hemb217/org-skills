package com.nhs.orgskills.service;

import com.nhs.orgskills.dto.SkillDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SkillService {
    CompletableFuture<SkillDto> createSkill(SkillDto skillDto);
    CompletableFuture<List<SkillDto>> getAllSkills(Integer pageNo, Integer pageSize);
    CompletableFuture<List<SkillDto>> getAllSkillsByEmployeeId(Long empId);
    CompletableFuture<SkillDto> getSingleSkillForEmployee(Long empId, Long skillId);
    CompletableFuture<SkillDto> updateSkill(SkillDto skillDto, Long userId);
    CompletableFuture<SkillDto> getSkillById(Long skillId);
    void deleteSkill(Long skillId);


}
