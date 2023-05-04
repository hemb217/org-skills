package com.nhs.orgskills.service.impl;

import com.nhs.orgskills.dto.EmployeeDto;
import com.nhs.orgskills.dto.SkillDto;
import com.nhs.orgskills.entity.Employee;
import com.nhs.orgskills.entity.Skill;
import com.nhs.orgskills.exception.ResourceNotFoundException;
import com.nhs.orgskills.repository.EmployeeRepository;
import com.nhs.orgskills.repository.SkillRepository;
import com.nhs.orgskills.service.EmployeeService;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository empRepository;
    private final SkillRepository skillRepository;

    @Async
    public CompletableFuture<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
        Employee empToCreate = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = empRepository.save(empToCreate);
        return CompletableFuture.completedFuture(modelMapper.map(savedEmployee, EmployeeDto.class));
    }

    @Async
    public CompletableFuture<List<EmployeeDto>> getAllEmployees(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Employee> pagedResult = empRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            List<Employee> allEmployees = pagedResult.getContent();
            List<EmployeeDto> allEmployeesDto = allEmployees.stream().map(emp -> modelMapper.map(emp,  EmployeeDto.class)).toList();
            return CompletableFuture.completedFuture(allEmployeesDto);
        } else {
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
    }

    @Async
    public CompletableFuture<EmployeeDto> updateEmployee(EmployeeDto employeeDto, Long employeeId) {
        if(!empRepository.existsById(employeeId)) throw new ResourceNotFoundException(Employee.class.getSimpleName(), "id",employeeId);
        Employee empToCreate = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = empRepository.save(empToCreate);
        return CompletableFuture.completedFuture(modelMapper.map(savedEmployee, EmployeeDto.class));
    }

    @Async
    public CompletableFuture<EmployeeDto> getEmployeeById(Long employeeId) {
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class.getSimpleName(), "id", employeeId));
        return CompletableFuture.completedFuture(modelMapper.map(employee, EmployeeDto.class));
    }

    public void deleteEmployee(Long employeeId) {
        if(!empRepository.existsById(employeeId)) throw new ResourceNotFoundException(Employee.class.getSimpleName(), "id",employeeId);
        empRepository.deleteById(employeeId);
    }

    @Async
    public CompletableFuture<List<EmployeeDto>> getAllEmployeesBySkillId(Long skillId) {
        if(!skillRepository.existsById(skillId)) throw new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId);
        List<Employee> allEmployees = empRepository.findEmployeesBySkillsId(skillId);
        List<EmployeeDto> allEmployeesDto = allEmployees.stream().map(emp -> modelMapper.map(emp,  EmployeeDto.class)).toList();
        return CompletableFuture.completedFuture(allEmployeesDto);
    }

    @Async
    public CompletableFuture<EmployeeDto> createSkillsForEmployee(Long employeeId, List<SkillDto> skills) {
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class.getSimpleName(), "id", employeeId));
        List<Skill> newSkillsToAdd = skills.stream().map(skillDto -> modelMapper.map(skillDto,  Skill.class)).toList();
        employee.getSkills().addAll(newSkillsToAdd);

        Employee savedEmployee = empRepository.save(employee);
        return CompletableFuture.completedFuture(modelMapper.map(savedEmployee, EmployeeDto.class));
    }

    @Async
    public CompletableFuture<EmployeeDto> updateSkillForEmployee(Long employeeId, Long skillId, SkillDto skillDto) {
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class.getSimpleName(), "id", employeeId));
        Optional<Skill> employeeWithSkill = employee.getSkills().stream()
                .filter(skill -> Objects.equals(skill.getId(), skillId)).findFirst();
        if(employeeWithSkill.isEmpty()) throw new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId);

        Skill singleSkill = employee.getSkills().stream()
                .filter(skill -> Objects.equals(skill.getId(), skillDto.getId())).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillDto.getId()));

        employee.getSkills().set(employee.getSkills().indexOf(singleSkill), modelMapper.map(skillDto, Skill.class));

        Employee savedEmployee = empRepository.save(employee);
        return CompletableFuture.completedFuture(modelMapper.map(savedEmployee, EmployeeDto.class));
    }

    public void deleteSkillFromEmployee(Long skillId, Long employeeId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException(Skill.class.getSimpleName(), "id", skillId));
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class.getSimpleName(), "id", employeeId));
        employee.getSkills().remove(skill);
        empRepository.save(employee);
    }
}
