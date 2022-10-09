package com.ahao.vo;

import com.ahao.pojo.Class;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClassVo extends Class {

    private Integer page;
    private Integer limit;

}
