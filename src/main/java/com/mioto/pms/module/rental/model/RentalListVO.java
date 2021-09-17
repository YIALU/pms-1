package com.mioto.pms.module.rental.model;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mioto.pms.module.site.model.SiteBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-07-06 14:23
 */
@Getter
@Setter
@ApiModel(value = "查询房屋出租列表对象")
public class RentalListVO {
    @ApiModelProperty(value = "房屋id")
    private String roomId;
    @ApiModelProperty(value = "房名")
    private String roomName;
    @ApiModelProperty(value = "房型")
    private String roomStyle;
    @ApiModelProperty(value = "房租")
    private Double rentFee;
    @ApiModelProperty(value = "租户号码")
    private String phone;
    @ApiModelProperty(value = "租户姓名")
    private String tenantName;
    @ApiModelProperty(value = "房间状态  0-空闲 1-在租 2-退租中")
    private Integer roomStatus;
    @ApiModelProperty(value = "合同时间")
    private String contractTime;
    @ApiModelProperty(value = "房屋出租信息id")
    private String rentalId;

    @ApiModelProperty(value = "房间详细地址")
    private SiteBO siteBO;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date startTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date endTime;

    /**
     * 获取合同时间，使其格式为：yyyy年MM月至yyyy年MM月
     * @return
     */
    public String getContractTime(){
        StrBuilder strBuilder = StrBuilder.create();
        if (ObjectUtil.isEmpty(startTime) && ObjectUtil.isEmpty(endTime)){

        }else {
            String datePattern = "yyyy年MM月";

            if (ObjectUtil.isNotEmpty(startTime)) {
                strBuilder.append(DateUtil.format(startTime, datePattern));
            }
            strBuilder.append("至");
            if (ObjectUtil.isNotEmpty(endTime)) {
                strBuilder.append(DateUtil.format(endTime, datePattern));
            }
        }
        return strBuilder.toString();
    }
}
