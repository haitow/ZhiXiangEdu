package com.chaoxi.pojo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private String email;
    private String department;
    private BigDecimal salary;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}