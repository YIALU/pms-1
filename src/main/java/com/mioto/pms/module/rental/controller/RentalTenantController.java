package com.mioto.pms.module.rental.controller;

import org.springframework.web.bind.annotation.*;
import com.mioto.pms.module.rental.model.RentalTenant;
import com.mioto.pms.module.rental.service.IRentalTenantService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
@RestController
@RequestMapping("rentalTenant")
@Api(tags = "出租与租户关联信息管理")
public class RentalTenantController {
    @Resource
    private IRentalTenantService rentalTenantService;

    /**
     * 列表
     */
    @GetMapping
    @ApiOperation(value="查询出租与租户关联信息列表")
    public ResultData list (HttpServletRequest request,RentalTenant rentalTenant){
        return ResultData.success(rentalTenantService.findList(rentalTenant));
    }

    /**
     * 分页查询列表
     */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询出租与租户关联信息")
    public ResultData pager (HttpServletRequest request, RentalTenant rentalTenant,BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<RentalTenant> list = rentalTenantService.findList(rentalTenant);
        PageInfo<RentalTenant> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
     * 查询
     */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询出租与租户关联信息")
    public ResultData findById (@PathVariable("id")Integer id){
        return ResultData.success(rentalTenantService.findByColumn("id",id));
    }

    /**
     * 新增
     */
    @PostMapping
    @ApiOperation(value="新增出租与租户关联信息")
    public ResultData add (RentalTenant rentalTenant){
        return Optional.of(rentalTenantService.insertIgnoreNull(rentalTenant))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(rentalTenant))
                .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
     * 更新
     */
    @PutMapping
    @ApiOperation(value="更新出租与租户关联信息")
    public ResultData update (RentalTenant rentalTenant){
        return Optional.of(rentalTenantService.updateIgnoreNull(rentalTenant))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(rentalTenant))
                .orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除出租与租户关联信息")
    public ResultData delete (@PathVariable("id")Integer id){
        return Optional.of(rentalTenantService.deleteByColumn("id",id))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除出租与租户关联信息")
    public ResultData batchDelete (Integer... ids){
        return Optional.of(rentalTenantService.batchDelete(ids))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}