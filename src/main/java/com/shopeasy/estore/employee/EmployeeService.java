package com.shopeasy.estore.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Cacheable(cacheNames = "Employee")
    public List<Employee> getEmployeeList(){
        logger.info("getEmployeeList called for DB....");
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
