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
@RequestMapping("api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final SkillService skillService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompletableFuture<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return employeeService.createEmployee(employeeDto);
    }

    @PostMapping("/{empId}/skill")
    @ResponseStatus(HttpStatus.CREATED)
    CompletableFuture<EmployeeDto> createSkillsForEmployee(@PathVariable Long empId,
                                                           @RequestBody @Valid List<SkillDto> skillsDto) {
        return employeeService.createSkillsForEmployee(empId, skillsDto);
    }

    @PutMapping("/{empId}")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<EmployeeDto> updateEmployee(@PathVariable Long empId,
                                                  @RequestBody @Valid EmployeeDto employeeDto) {
        return employeeService.updateEmployee(employeeDto, empId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<List<EmployeeDto>> getAllEmployees(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "100") Integer pageSize) {
        return employeeService.getAllEmployees(pageNo, pageSize);
    }

    @GetMapping("/{empId}/skill")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<List<SkillDto>> getAllSkillsByEmployeeId(@PathVariable Long empId) {
        return skillService.getAllSkillsByEmployeeId(empId);
    }

    @GetMapping("/{empId}/skill/{skillId}")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<SkillDto> getSingleSkillByEmployeeId(@PathVariable Long empId, @PathVariable Long skillId) {
        return skillService.getSingleSkillForEmployee(empId, skillId);
    }
    @PutMapping("/{empId}/skill/{skillId}")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<EmployeeDto> updateSkillForEmployee(@PathVariable Long empId,
                                                          @PathVariable Long skillId,
                                                       @RequestBody @Valid SkillDto skillDto
                                                       ) {
        return employeeService.updateSkillForEmployee(empId, skillId, skillDto);
    }

    @GetMapping("/{empId}")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<EmployeeDto> getEmployeeById(@PathVariable Long empId) {
        return employeeService.getEmployeeById(empId);
    }

    @DeleteMapping("/{empId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEmployee(@PathVariable Long empId) {
        employeeService.deleteEmployee(empId);
    }

    @DeleteMapping("/{empId}/skill/{skillId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSkillFromEmployee(@PathVariable Long empId, @PathVariable Long skillId) {
        employeeService.deleteSkillFromEmployee(skillId, empId);
    }

}
