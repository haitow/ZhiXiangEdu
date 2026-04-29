package com.chaoxi.service;

import com.chaoxi.pojo.Employee;
import java.util.List;

public interface EmployeeService {
    
    // 新增员工
    boolean addEmployee(Employee employee);
    
    // 删除员工
    boolean deleteEmployee(Long id);
    
    // 修改员工
    boolean updateEmployee(Employee employee);
    
    // 根据ID查询
    Employee getEmployeeById(Long id);
    
    // 分页查询
    List<Employee> getEmployeePage(String name, String department, Integer status, int page, int pageSize);
    
    // 查询总数
    int getCount(String name, String department, Integer status);
}
