package com.ahao.vo;

import com.ahao.pojo.College;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CollegeVo extends College {

    private Integer page;
    private Integer limit;
}
