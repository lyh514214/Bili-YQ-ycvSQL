package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role_menu")
public class RoleMenu {

    private Integer rid;
    private Integer mid;
}
