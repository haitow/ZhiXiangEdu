package com.chaoxi.mapper;

import com.chaoxi.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    
    // 新增
    int insert(Employee employee);
    
    // 根据ID删除
    int deleteById(Long id);
    
    // 修改
    int update(Employee employee);
    
    // 根据ID查询
    Employee selectById(Long id);
    
    // 分页查询（带条件）
    List<Employee> selectByPage(@Param("name") String name, 
                                 @Param("department") String department,
                                 @Param("status") Integer status);
    
    // 查询总数（用于分页）
    int selectCount(@Param("name") String name, 
                    @Param("department") String department,
                    @Param("status") Integer status);
}