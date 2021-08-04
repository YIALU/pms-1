package com.mioto.pms.module.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.user.model.Function;
import com.mioto.pms.module.user.service.FunctionService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
* @auther lzc
* date 2021-04-01 16:24:10
*/
@RestController
@RequestMapping("function")
@Api(tags = "权限")
public class FunctionController {
    @Resource
    private FunctionService functionService ;

    @GetMapping
    @ApiOperation(value="根据角色id查询权限树")
    public ResultData tree (Integer roleId){
        return ResultData.success(functionService.findTree(roleId));
    }

    @PostMapping
    @ApiOperation(value="修改角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleId",value = "角色id",dataType="Integer", paramType = "query",required=true),
            @ApiImplicitParam(name="functionIds",value = "已选中的菜单id",dataType="Integer",allowMultiple = true, paramType = "query")
    })
    public ResultData updatePermission (Integer roleId,Integer... functionIds){
        return Optional.of(functionService.updatePermission(roleId,functionIds)).filter(count -> count > 0)
                .map(count -> ResultData.success()).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }
}
