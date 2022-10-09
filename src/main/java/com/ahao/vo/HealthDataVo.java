package com.ahao.vo;

import com.ahao.pojo.HealthClock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HealthDataVo extends HealthClock {
    private Integer page;
    private Integer limit;
}
