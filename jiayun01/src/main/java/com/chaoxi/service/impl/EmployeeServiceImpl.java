package com.chaoxi.service.impl;

import com.chaoxi.mapper.EmployeeMapper;
import com.chaoxi.pojo.Employee;
import com.chaoxi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public boolean addEmployee(Employee employee) {
        employee.setStatus(1);
        return employeeMapper.insert(employee) > 0;
    }

    @Override
    @Transactional
    public boolean deleteEmployee(Long id) {
        return employeeMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean updateEmployee(Employee employee) {
        return employeeMapper.update(employee) > 0;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public List<Employee> getEmployeePage(String name, String department, Integer status, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return employeeMapper.selectByPage(name, department, status, offset, pageSize);
    }

    @Override
    public int getCount(String name, String department, Integer status) {
        return employeeMapper.selectCount(name, department, status);
    }
}