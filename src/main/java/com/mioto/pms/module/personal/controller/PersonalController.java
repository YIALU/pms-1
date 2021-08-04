package com.mioto.pms.module.personal.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.personal.model.Personal;
import com.mioto.pms.module.personal.model.PersonalDTO;
import com.mioto.pms.module.personal.service.PersonalService;
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
* date 2021-04-08 18:00:58
*/
@RestController
@RequestMapping("personal")
@Api(tags = "人员管理")
public class PersonalController {
    @Resource
    private PersonalService personalService ;

    /**
    * 查询人员管理
    */
    @GetMapping
    @ApiOperation(value="查询人员管理")
    public ResultData list (Personal personal){
        return ResultData.success(personalService.findList(personal));
    }

    /**
    * 分页查询人员管理
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询人员管理")
    public ResultData pager (Personal personal, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<PersonalDTO> list = personalService.findList(personal);
        PageInfo<PersonalDTO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询人员管理
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询人员管理")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(personalService.findByColumn("id",id));
    }

    /**
    * 新增人员管理
    */
    @PostMapping
    @ApiOperation(value="新增人员管理")
    public ResultData add (Personal personal){
        return Optional.of(personalService.insertIgnoreNull(personal)).filter(count -> count > 0)
        .map(count -> ResultData.success(personal)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增人员管理
    */
    @PutMapping
    @ApiOperation(value="更新新增人员管理")
    public ResultData update (Personal personal){
        return Optional.of(personalService.updateIgnoreNull(personal)).filter(count -> count > 0)
        .map(count -> ResultData.success(personal)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除人员管理
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除人员管理")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(personalService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除人员管理
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除人员管理")
    public ResultData batchDelete (String... ids){
        return Optional.of(personalService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    @GetMapping("/owner")
    @ApiOperation(value = "查询业主")
    public ResultData findOwner(){
        List<Personal> personals= personalService.findOwmer();
        return ResultData.success(personals);
    }

}
