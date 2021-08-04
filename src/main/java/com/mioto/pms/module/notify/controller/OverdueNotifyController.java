package com.mioto.pms.module.notify.controller;

import org.springframework.web.bind.annotation.*;
import com.mioto.pms.module.notify.model.OverdueNotify;
import com.mioto.pms.module.notify.service.IOverdueNotifyService;
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
 * @date 2021-07-14 14:43:18
 */
@RestController
@RequestMapping("overdueNotify")
@Api(tags = "催收通知管理")
public class OverdueNotifyController {
    @Resource
    private IOverdueNotifyService overdueNotifyService;

    /**
     * 列表
     */
    @GetMapping
    @ApiOperation(value="查询催收通知",response = OverdueNotify.class)
    public ResultData get (){
        return ResultData.success(overdueNotifyService.find());
    }

    /**
     * 分页查询列表
     *//*
    @GetMapping("/pager")
    @ApiOperation(value="分页查询催收通知",response = OverdueNotify.class)
    public ResultData pager (HttpServletRequest request, OverdueNotify overdueNotify,BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<OverdueNotify> list = overdueNotifyService.findList(overdueNotify);
        PageInfo<OverdueNotify> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    *//**
     * 查询
     *//*
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询催收通知")
    public ResultData findById (@PathVariable("id")Integer id){
        return ResultData.success(overdueNotifyService.findByColumn("id",id));
    }

    *//**
     * 新增
     *//*
    @PostMapping
    @ApiOperation(value="新增催收通知")
    public ResultData add (OverdueNotify overdueNotify){
        return Optional.of(overdueNotifyService.insertIgnoreNull(overdueNotify))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(overdueNotify))
                .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }*/

    /**
     * 更新
     */
    @PutMapping
    @ApiOperation(value="更新催收通知")
    public ResultData update (OverdueNotify overdueNotify){
        return Optional.of(overdueNotifyService.updateIgnoreNull(overdueNotify))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(overdueNotify))
                .orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

   /* *//**
     * 删除
     *//*
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除催收通知")
    public ResultData delete (@PathVariable("id")Integer id){
        return Optional.of(overdueNotifyService.deleteByColumn("id",id))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    *//**
     * 批量删除
     *//*
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除催收通知")
    public ResultData batchDelete (Integer... ids){
        return Optional.of(overdueNotifyService.batchDelete(ids))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }*/
}