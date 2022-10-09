package com.ahao.vo;

import com.ahao.pojo.NucleicAci;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NucleicAciVo extends NucleicAci {

    private Integer page;
    private Integer limit;

}
