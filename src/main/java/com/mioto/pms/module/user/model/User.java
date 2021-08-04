package com.mioto.pms.module.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @auther lzc
* date 2021-04-01 11:13:53
*/
@Data
@ApiModel(value = "用户")
public class User implements Serializable {
    private static final long serialVersionUID= User.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private int id;
    /**
    * 账号
    */
    @ApiModelProperty(value = "账号")
    private String username;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;


    /**
    * 密码
    */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "密码")
    private String password;

    /**
    * 邮箱
    */
    @ApiModelProperty(value = "手机号")
    private String phone;



    private Integer status;

}
