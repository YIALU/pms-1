package com.mioto.pms.module.file.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* @auther csl
* date 2021-03-22 15:20:48
*/
@Data
@ApiModel(value = "文件")
public class FileInfo implements Serializable {
    private static final long serialVersionUID= FileInfo.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private String id;
    /**
    * 文件名称
    */
    @ApiModelProperty(value = "文件名称")
    private String name;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
    * 文件路径
    */
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    /**
    * 备注
    */
    @ApiModelProperty(value = "备注")
    private String desc;


}
