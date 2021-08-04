package com.mioto.pms.module.meter.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.meter.service.MeterReadingService;
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
* date 2021-04-16 16:13:24
*/
@RestController
@RequestMapping("meter")
@Api(tags = "抄表策略")
public class MeterReadingController {
    @Resource
    private MeterReadingService meterReadingService ;

    /**
    * 查询抄表策略
    */
    @GetMapping
    @ApiOperation(value="查询抄表策略")
    public ResultData list (MeterReading meterReading){
        return ResultData.success(meterReadingService.findList(meterReading));
    }

    /**
    * 分页查询抄表策略
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询抄表策略")
    public ResultData pager (MeterReading meterReading, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<MeterReading> list = meterReadingService.findList(meterReading);
        PageInfo<MeterReading> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询抄表策略
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询抄表策略")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(meterReadingService.findByColumn("id",id));
    }

    /**
    * 新增抄表策略
    */
    @PostMapping
    @ApiOperation(value="新增抄表策略")
    public ResultData add (MeterReading meterReading){
        return Optional.of(meterReadingService.insertIgnoreNull(meterReading)).filter(count -> count > 0)
        .map(count -> ResultData.success(meterReading)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增抄表策略
    */
    @PutMapping
    @ApiOperation(value="更新新增抄表策略")
    public ResultData update (MeterReading meterReading){
        return Optional.of(meterReadingService.updateIgnoreNull(meterReading)).filter(count -> count > 0)
        .map(count -> ResultData.success(meterReading)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除抄表策略
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除抄表策略")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(meterReadingService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除抄表策略
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除抄表策略")
    public ResultData batchDelete (String... ids){
        return Optional.of(meterReadingService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}
