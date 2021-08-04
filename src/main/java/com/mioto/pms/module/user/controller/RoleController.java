package com.mioto.pms.module.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.user.model.Role;
import com.mioto.pms.module.user.service.RoleService;
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
* date 2021-04-01 16:06:39
*/
@RestController
@RequestMapping("role")
@Api(tags = "角色")
public class RoleController {
    @Resource
    private RoleService roleService ;

    /**
    * 查询角色
    */
    @GetMapping
    @ApiOperation(value="查询角色")
    public ResultData list (Role role){
        return ResultData.success(roleService.findList(role));
    }

    /**
    * 分页查询角色
    */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询角色")
    public ResultData pager (Role role, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<Role> list = roleService.findList(role);
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询角色
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询角色")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(roleService.findByColumn("id",id));
    }

    /**
    * 新增角色
    */
    @PostMapping
    @ApiOperation(value="新增角色")
    public ResultData add (Role role){
        return Optional.of(roleService.insertIgnoreNull(role)).filter(count -> count > 0)
        .map(count -> ResultData.success(role)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增角色
    */
    @PutMapping
    @ApiOperation(value="更新新增角色")
    public ResultData update (Role role){
        return Optional.of(roleService.updateIgnoreNull(role)).filter(count -> count > 0)
        .map(count -> ResultData.success(role)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除角色
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除角色")
    public ResultData delete (@PathVariable("id")int id){
        return Optional.of(roleService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除角色
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除角色")
    public ResultData batchDelete (int... ids){
        return Optional.of(roleService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}
