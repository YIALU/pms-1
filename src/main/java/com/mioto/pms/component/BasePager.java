package com.mioto.pms.component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author mioto-qinxj
 * @date 2020/4/24
 */
@Getter
@ApiModel(value = "分页信息")
public class BasePager {
    @ApiModelProperty(value = "第几页 默认为第1页",dataType = "int")
    private int page = 1;
    @ApiModelProperty(value = "每页需要显示的条数 默认10条")
    private int rows = 10;
    @ApiModelProperty(value = "按哪个字段进行排序 例如按id倒序:id desc")
    private String sortBy = "";

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setRows(int rows) {
        this.rows = rows <= 0 ? 10 : rows;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
