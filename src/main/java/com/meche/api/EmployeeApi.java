package com.meche.api;

import com.meche.model.Employee;
import com.meche.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("/api/v1/hair/employee")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class EmployeeApi {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(employee, OK);
    }


    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee existingEmployee = employeeService.getEmployee(employee.getId());
        if (existingEmployee == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        Employee updatedEmployee = employeeService.update(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        return new ResponseEntity<>(savedEmployee,CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>>  getAllEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(),OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        Employee existingEmployee = employeeService.getEmployee(id);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
