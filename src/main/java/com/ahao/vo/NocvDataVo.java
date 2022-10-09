package com.ahao.vo;

import com.ahao.pojo.NocvData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NocvDataVo extends NocvData {
    private Integer page;
    private Integer limit;
}
