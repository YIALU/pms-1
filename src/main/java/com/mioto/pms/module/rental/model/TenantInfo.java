package com.mioto.pms.module.rental.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 租户信息实体
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
@Data
@ApiModel(value = "租户信息")
public class TenantInfo implements Serializable{

    private static final long serialVersionUID=TenantInfo.class.getName().hashCode();
    
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 性别 0-女 1-男
     */
    @ApiModelProperty(value = "性别 0-女 1-男")
    private Integer sex;
    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯")
    private String nativePlace;
    /**
     * 证件
     */
    @ApiModelProperty(value = "证件")
    private String idCard;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;
    
}