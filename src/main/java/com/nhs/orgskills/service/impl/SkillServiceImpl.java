package com.nhs.orgskills.service.impl;

import com.nhs.orgskills.dto.SkillDto;
import com.nhs.orgskills.entity.Employee;
import com.nhs.orgskills.entity.Skill;
import com.nhs.orgskills.exception.ResourceNotFoundException;
import com.nhs.orgskills.repository.EmployeeRepository;
import com.nhs.orgskills.repository.SkillRepository;
import com.nhs.orgskills.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillServiceImpl implements SkillService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository empRepository;
    private final SkillRepository skillRepository;

    @Async
    public CompletableFuture<SkillDto> createSkill(SkillDto skillDto) {
        Skill skillToCreate = modelMapper.map(skillDto, Skill.class);
        Skill savedSkill = skillRepository.save(skillToCreate);
        return CompletableFuture.completedFuture(modelMapper.map(savedSkill, SkillDto.class));
    }

    @Async
    public CompletableFuture<List<SkillDto>> getAllSkills(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Skill> pagedResult = skillRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            List<Skill> allSkills = pagedResult.getContent();
            List<SkillDto> allSkillsDto = allSkills.stream().map(skill -> modelMapper.map(skill,  SkillDto.class)).toList();
            return CompletableFuture.completedFuture(allSkillsDto);
        } else {
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
    }

    @Async
    public CompletableFuture<List<SkillDto>> getAllSkillsByEmployeeId(Long employeeId) {
        if(!empRepository.existsById(employeeId)) throw new ResourceNotFoundException(Employee.class.getSimpleName(), "id",employeeId);
        List<Skill> allSkills = skillRepository.findSkillsByEmployeesId(employeeId);
        List<SkillDto> allSkillsDto = allSkills.stream().map(skill -> modelMapper.map(skill,  SkillDto.class)).toList();
        return CompletableFuture.completedFuture(allSkillsDto);
    }

    @Async
    public CompletableFuture<SkillDto> getSingleSkillForEmployee(Long employeeId, Long skillId) {
        if(!skillRepository.existsById(skillId)) throw new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId);
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class.getSimpleName(), "id", employeeId));
        Skill singleSkill = employee.getSkills().stream()
                .filter(skill -> Objects.equals(skill.getId(), skillId))
                .findAny().orElseThrow(() -> new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId));
        return CompletableFuture.completedFuture(modelMapper.map(singleSkill, SkillDto.class));
    }

    @Async
    public CompletableFuture<SkillDto> updateSkill(SkillDto skillDto, Long skillId) {
        if(!skillRepository.existsById(skillId)) throw new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId);
        Skill skillToCreate = modelMapper.map(skillDto, Skill.class);
        Skill savedSkill = skillRepository.save(skillToCreate);
        return CompletableFuture.completedFuture(modelMapper.map(savedSkill, SkillDto.class));
    }

    @Async
    public CompletableFuture<SkillDto> getSkillById(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId));
        return CompletableFuture.completedFuture(modelMapper.map(skill, SkillDto.class));
    }

    public void deleteSkill(Long skillId) {
        if(!skillRepository.existsById(skillId)) throw new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId);
        skillRepository.deleteById(skillId);
    }
}
