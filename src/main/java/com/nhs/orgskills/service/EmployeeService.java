package com.nhs.orgskills.service;

import com.nhs.orgskills.dto.EmployeeDto;
import com.nhs.orgskills.dto.SkillDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmployeeService {
    CompletableFuture<EmployeeDto> createEmployee(EmployeeDto employeeDto);
    CompletableFuture<List<EmployeeDto>> getAllEmployees(Integer pageNo, Integer pageSize);
    CompletableFuture<EmployeeDto> updateEmployee(EmployeeDto employeeDto, Long userId);
    CompletableFuture<EmployeeDto> getEmployeeById(Long employeeId);
    void deleteEmployee(Long employeeId);
    CompletableFuture<List<EmployeeDto>> getAllEmployeesBySkillId(Long skillId);
    CompletableFuture<EmployeeDto> createSkillsForEmployee(Long employeeId, List<SkillDto> skills);
    CompletableFuture<EmployeeDto> updateSkillForEmployee(Long employeeId, Long skillId, SkillDto skill);
    void deleteSkillFromEmployee(Long skillId, Long empId);
}
