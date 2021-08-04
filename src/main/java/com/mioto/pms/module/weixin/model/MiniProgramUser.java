package com.mioto.pms.module.weixin.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 小程序用户实体
 * @author qinxj
 * @date 2021-07-16 10:23:21
 */
@Data
@ApiModel(value = "小程序用户")
public class MiniProgramUser implements Serializable{

    private static final long serialVersionUID=MiniProgramUser.class.getName().hashCode();
    

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "微信昵称")
    private String wxNickname;

    @ApiModelProperty(value = "用户唯一标识")
    private String openId;

    @ApiModelProperty(value = "用户在开放平台的唯一标识符")
    private String unionId;

    @ApiModelProperty(value = "小程序用户id")
    private Integer userId;

    @ApiModelProperty(value = "小程序用户类型 1-租户 2-房东")
    private Integer userType;
    
}