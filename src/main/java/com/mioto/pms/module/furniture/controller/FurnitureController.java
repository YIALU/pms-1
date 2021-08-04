package com.mioto.pms.module.furniture.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.furniture.model.Furniture;
import com.mioto.pms.module.furniture.model.FurnitureListVO;
import com.mioto.pms.module.furniture.service.FurnitureService;
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
* date 2021-04-21 16:22:29
*/
@RestController
@RequestMapping("furniture")
@Api(tags = "资产管理")
public class FurnitureController {
    @Resource
    private FurnitureService furnitureService ;

    /**
    * 查询资产管理
    */
    @GetMapping
    @ApiOperation(value="查询资产管理")
    public ResultData list (Furniture furniture){
        return ResultData.success(furnitureService.findList(furniture));
    }

    /**
    * 分页查询资产管理
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询资产管理")
    public ResultData pager (Furniture furniture, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<FurnitureListVO> list = furnitureService.findList(furniture);
        PageInfo<FurnitureListVO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询资产管理
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询资产管理")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(furnitureService.findByColumn("id",id));
    }

    @GetMapping("/names")
    @ApiOperation(value="查询资产名称列表")
    public ResultData findFurnitureNames (){
        return ResultData.success(furnitureService.findFurnitureNames());
    }
    /**
    * 新增资产管理
    */
    @PostMapping
    @ApiOperation(value="新增资产管理")
    public ResultData add (Furniture furniture){
        return Optional.of(furnitureService.insertIgnoreNull(furniture)).filter(count -> count > 0)
        .map(count -> ResultData.success(furniture)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增资产管理
    */
    @PutMapping
    @ApiOperation(value="更新新增资产管理")
    public ResultData update (Furniture furniture){
        return Optional.of(furnitureService.updateIgnoreNull(furniture)).filter(count -> count > 0)
        .map(count -> ResultData.success(furniture)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除资产管理
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除资产管理")
    public ResultData delete (@PathVariable("id")Integer id){
        return Optional.of(furnitureService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除资产管理
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除资产管理")
    public ResultData batchDelete (Integer... ids){
        return Optional.of(furnitureService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }


}
