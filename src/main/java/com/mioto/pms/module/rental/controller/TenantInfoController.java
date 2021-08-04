package com.mioto.pms.module.rental.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import com.mioto.pms.module.rental.model.TenantInfo;
import com.mioto.pms.module.rental.service.ITenantInfoService;
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
@RequestMapping("tenantInfo")
@Api(tags = "租户信息管理")
public class TenantInfoController {
    @Resource
    private ITenantInfoService tenantInfoService;

    /**
     * 列表
     */
    @GetMapping
    @ApiOperation(value="查询租户信息列表")
    public ResultData list (HttpServletRequest request,TenantInfo tenantInfo){
        return ResultData.success(tenantInfoService.findList(tenantInfo));
    }

    /**
     * 分页查询列表
     */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询租户信息")
    public ResultData pager (HttpServletRequest request, TenantInfo tenantInfo,BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<TenantInfo> list = tenantInfoService.findList(tenantInfo);
        PageInfo<TenantInfo> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
     * 查询
     */
    @GetMapping("/{column}/{value}")
    @ApiOperation(value="通过字段名查询租户信息",response = TenantInfo.class)
    public ResultData findByColumn (@PathVariable("column")String column,@PathVariable("value")Object value){
        return ResultData.success(tenantInfoService.findByColumn(StrUtil.toUnderlineCase(column),value));
    }

    /**
     * 新增
     */
    @PostMapping
    @ApiOperation(value="新增租户信息")
    public ResultData add (TenantInfo tenantInfo){
        return Optional.of(tenantInfoService.insertIgnoreNull(tenantInfo))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(tenantInfo))
                .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
     * 更新
     */
    @PutMapping
    @ApiOperation(value="更新租户信息")
    public ResultData update (TenantInfo tenantInfo){
        return Optional.of(tenantInfoService.updateIgnoreNull(tenantInfo))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(tenantInfo))
                .orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除租户信息")
    public ResultData delete (@PathVariable("id")Integer id){
        return Optional.of(tenantInfoService.deleteByColumn("id",id))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除租户信息")
    public ResultData batchDelete (Integer... ids){
        return Optional.of(tenantInfoService.batchDelete(ids))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}