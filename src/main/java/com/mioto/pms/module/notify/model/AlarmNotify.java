package com.mioto.pms.module.notify.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 设备报警通知实体
 * @author qinxj
 * @date 2021-07-14 14:43:19
 */
@Data
@ApiModel(value = "设备报警通知")
public class AlarmNotify implements Serializable{

    private static final long serialVersionUID=AlarmNotify.class.getName().hashCode();
    

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "报警邮箱")
    private String email;

    @ApiModelProperty(value = "报警电话")
    private String phone;
    
}