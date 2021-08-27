package com.mioto.pms.module.price.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
* @auther lzc
* date 2021-04-16 14:51:24
*/
@RestController
@RequestMapping("price")
@Api(tags = "费用策略")
public class PriceController {
    @Resource
    private PriceService priceService ;

    /**
    * 查询费用策略
    */
    @GetMapping
    @ApiOperation(value="查询费用策略")
    public ResultData list (Price price){
        return ResultData.success(priceService.findList(price));
    }

    /**
    * 分页查询费用策略
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询费用策略")
    public ResultData pager (Price price, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<Price> list = priceService.findList(price);
        PageInfo<Price> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询费用策略
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询费用策略")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(priceService.findByColumn("id",id));
    }

    /**
    * 新增费用策略
    */
    @PostMapping
    @ApiOperation(value="新增费用策略")
    public ResultData add (HttpServletRequest request, Price price){
        return Optional.of(priceService.insertIgnoreNull(price)).filter(count -> count > 0)
        .map(count -> ResultData.success(price)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增费用策略
    */
    @PutMapping
    @ApiOperation(value="更新新增费用策略")
    public ResultData update (HttpServletRequest request,Price price){
        return Optional.of(priceService.updateIgnoreNull(price)).filter(count -> count > 0)
        .map(count -> ResultData.success(price)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除费用策略
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除费用策略")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(priceService.deleteById(id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除费用策略
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除费用策略")
    public ResultData batchDelete (String... ids){
        return Optional.of(priceService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}
