package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("line_trend")
public class LineData {

    private Integer confirm;
    private Integer isolation;
    private Integer cure;
    private Integer dead;
    private Integer similar;

}
