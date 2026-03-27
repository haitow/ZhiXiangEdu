package com.chaoxi.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("classes")
public class Classes implements Serializable {
  private static final long serialVersionUID = 1L;
  /** 主键id */
  private Long id;
//  老师id
  private Long teacherId;
  /** 课程名称 */
  private String name;
  /** 老师名字 */
  private String tname;
  /** 标签名称 */
  private String tags;
  /** 状态 */
  private Integer status;
  /** 课程描述 */
  private String description;
  /** 教室名称 */
  private String roomName;
  /** 价格 */
  private BigDecimal price;
  /** 课程创建时间 */
  private LocalDateTime startTime;
  /**  课程结束时间 */
  private LocalDateTime endTime;
  /** 课程最大人数 */
  private Integer pmax;
  /** 课程已选人数 */
  private Integer pnum;
  /** 课程图片 */
  private String imgs;
  /** 创建时间 */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  /** 更新时间 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  private Long createUser;
  /** 修改人 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;
  /** 是否停课 */
  private Integer isDeleted;
}
