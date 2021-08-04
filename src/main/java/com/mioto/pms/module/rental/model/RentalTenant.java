package com.mioto.pms.module.rental.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

/**
 * 出租与租户关联信息实体
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
@Data
@NoArgsConstructor
@ApiModel(value = "出租与租户关联信息")
public class RentalTenant implements Serializable{

    private static final long serialVersionUID=RentalTenant.class.getName().hashCode();
    
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 出租信息id
     */
    @ApiModelProperty(value = "出租信息id")
    private String rentalId;
    /**
     * 租户
     */
    @ApiModelProperty(value = "租户")
    private Integer tenantId;

    public RentalTenant(String rentalId,Integer tenantId){
        this.rentalId = rentalId;
        this.tenantId = tenantId;
    }
    
}