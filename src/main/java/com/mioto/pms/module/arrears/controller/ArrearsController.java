package com.mioto.pms.module.arrears.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.arrears.model.Arrears;
import com.mioto.pms.module.arrears.service.ArrearsService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
* @auther lzc
* date 2021-04-28 15:31:26
*/
@RestController
@RequestMapping("arrears")
@Api(tags = "欠费策略")
public class ArrearsController {
    @Resource
    private ArrearsService arrearsService ;

    /**
    * 查询欠费策略
    */
    @GetMapping
    @ApiOperation(value="查询欠费策略")
    public ResultData list (Arrears arrears){
        return ResultData.success(arrearsService.findList(arrears));
    }

    /**
    * 分页查询欠费策略
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询欠费策略")
    public ResultData pager (Arrears arrears, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<Arrears> list = arrearsService.findList(arrears);
        PageInfo<Arrears> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询欠费策略
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询欠费策略")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(arrearsService.findByColumn("id",id));
    }

    /**
    * 新增欠费策略
    */
    @PostMapping
    @ApiOperation(value="新增欠费策略")
    public ResultData add (Arrears arrears){
        return Optional.of(arrearsService.insertIgnoreNull(arrears)).filter(count -> count > 0)
        .map(count -> ResultData.success(arrears)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增欠费策略
    */
    @PutMapping
    @ApiOperation(value="更新新增欠费策略")
    public ResultData update (Arrears arrears){
        return Optional.of(arrearsService.updateIgnoreNull(arrears)).filter(count -> count > 0)
        .map(count -> ResultData.success(arrears)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除欠费策略
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除欠费策略")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(arrearsService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除欠费策略
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除欠费策略")
    public ResultData batchDelete (String... ids){
        return Optional.of(arrearsService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}
