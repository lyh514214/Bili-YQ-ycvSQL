package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("health_clock")
public class HealthClock {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String sex;
    private Integer age;
    private String phone;
    private String morningTemp;
    private String afternoonTemp;
    private String nightTemp;
    private String feverAndCough;
    private String recentHome;
    private String riskZone;
    private String recentZone;
    private String healthStatus;

}
