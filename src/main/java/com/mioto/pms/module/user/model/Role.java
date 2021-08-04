package com.mioto.pms.module.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @auther lzc
* date 2021-04-01 16:06:39
*/
@Data
@ApiModel(value = "角色")
public class Role implements Serializable {
    private static final long serialVersionUID= Role.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private int id;
    /**
    * 角色名
    */
    @ApiModelProperty(value = "角色名")
    private String name;

    /**
    * 说明
    */
    @ApiModelProperty(value = "说明")
    private String desc;


    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
