package com.mioto.pms.result;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mioto-qinxj
 * @description 返回结果
 * @date 2020/3/24
 */
@Getter
@Setter
@Builder
@ApiModel(value = "返回结果")
public class ResultData {
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private String code;
    /**
     * 返回说明
     */
    @ApiModelProperty(value = "返回说明")
    private String desc;
    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private Object data;

    public static ResultData success() {
        return success(StrUtil.EMPTY);
    }

    public static ResultData success(Object data) {
        return ResultData.builder().code(SystemTip.OK.code()).desc(SystemTip.OK.desc()).data(data).build();
    }

    public static ResultData result(SystemTip systemTip) {
        return result(systemTip, StrUtil.EMPTY);
    }

    public static ResultData result(SystemTip systemTip, Object data) {
        return ResultData.builder().code(systemTip.code()).desc(systemTip.desc()).data(data).build();
    }
}
