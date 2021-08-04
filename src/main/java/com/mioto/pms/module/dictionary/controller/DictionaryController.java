package com.mioto.pms.module.dictionary.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.dictionary.model.Dictionary;
import com.mioto.pms.module.dictionary.service.DictionaryService;
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
* date 2021-04-06 15:25:37
*/
@RestController
@RequestMapping("dictionary")
@Api(tags = "数据字典")
public class DictionaryController {
    @Resource
    private DictionaryService dictionaryService ;

    /**
    * 查询数据字典
    */
    @GetMapping
    @ApiOperation(value="查询数据字典")
    public ResultData list (Dictionary dictionary){
        return ResultData.success(dictionaryService.findList(dictionary));
    }

    /**
    * 分页查询数据字典
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询数据字典")
    public ResultData pager (Dictionary dictionary, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<Dictionary> list = dictionaryService.findList(dictionary);
        PageInfo<Dictionary> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询数据字典
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询数据字典")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(dictionaryService.findByColumn("id",id));
    }

    /**
    * 新增数据字典
    */
    @PostMapping
    @ApiOperation(value="新增数据字典")
    public ResultData add (Dictionary dictionary){
        return Optional.of(dictionaryService.insertIgnoreNull(dictionary)).filter(count -> count > 0)
        .map(count -> ResultData.success(dictionary)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增数据字典
    */
    @PutMapping
    @ApiOperation(value="更新新增数据字典")
    public ResultData update (Dictionary dictionary){
        return Optional.of(dictionaryService.updateIgnoreNull(dictionary)).filter(count -> count > 0)
        .map(count -> ResultData.success(dictionary)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除数据字典
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除数据字典")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(dictionaryService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除数据字典
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除数据字典")
    public ResultData batchDelete (String... ids){
        return Optional.of(dictionaryService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}
