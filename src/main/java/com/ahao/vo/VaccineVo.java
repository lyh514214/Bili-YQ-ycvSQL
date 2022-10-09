package com.ahao.vo;

import com.ahao.pojo.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VaccineVo extends Vaccine {

    private Integer page;
    private Integer limit;

}
