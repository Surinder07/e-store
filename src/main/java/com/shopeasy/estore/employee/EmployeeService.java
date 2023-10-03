package com.shopeasy.estore.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @CachePut(cacheNames = "Employee")
    @CacheEvict(cacheNames = "Employee",allEntries = true)
    public List<Employee> getEmployeeList(){

        return employeeRepository.findAll();
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @CacheEvict(cacheNames = "Employee",key="#id",allEntries = true)
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
