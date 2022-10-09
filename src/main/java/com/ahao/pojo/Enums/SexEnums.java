package com.ahao.pojo.Enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum SexEnums {

    男(1),
    女(0)
    ;
    @EnumValue
    private Integer sexNum;

    SexEnums(Integer sexNum) {
        this.sexNum = sexNum;
    }
}
