package com.mioto.pms.module.notify.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 催收通知实体
 * @author qinxj
 * @date 2021-07-14 14:43:18
 */
@Data
@ApiModel(value = "催收通知")
public class OverdueNotify implements Serializable{

    private static final long serialVersionUID=OverdueNotify.class.getName().hashCode();
    

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "是否短信通知 0-否 1-是")
    private Integer immediateNotice;

    @ApiModelProperty(value = "是否逾期催收 0-否 1-是")
    private Integer overdue;

    @ApiModelProperty(value = "逾期天数")
    private Integer overdueDay;
    
}