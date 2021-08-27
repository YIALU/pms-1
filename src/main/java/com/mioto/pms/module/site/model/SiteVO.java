package com.mioto.pms.module.site.model;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-08-24 18:59
 */
@Getter
@Setter
@ApiModel(value = "区域详情对象")
public class SiteVO extends Site{
    @ApiModelProperty(value = "省id")
    private String provinceId;
    @ApiModelProperty(value = "市id")
    private String cityId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String level;

    public void setLevel(String level){
        if (StrUtil.isNotEmpty(level)){
            String[] array = level.split("/");
            if (array.length == 5) {
                provinceId = array[2];
                cityId = array[3];
            }
        }
    }
}
