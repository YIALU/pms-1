package com.mioto.pms.module.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.user.model.MiniProgramUserListVO;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.user.model.UserDTO;
import com.mioto.pms.module.user.model.UserVO;
import com.mioto.pms.module.user.service.UserService;
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
* date 2021-04-01 11:13:53
*/
@RestController
@RequestMapping("user")
@Api(tags = "用户")
public class UserController {
    @Resource
    private UserService userService ;

    /**
    * 查询用户
    */
    @GetMapping
    @ApiOperation(value="查询用户")
    public ResultData list (User user){
        return ResultData.success(userService.findList(user));
    }

    @GetMapping("/pager")
    @ApiOperation(value="分页查询平台用户",response = UserVO.class)
    public ResultData pager (User user, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<UserVO> list = userService.findList(user);
        PageInfo<UserVO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    @GetMapping("/pager/miniProgram")
    @ApiOperation(value="分页查询小程序用户")
    public ResultData pagerMiniProgram ( BasePager basePager,String name,String phone){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<MiniProgramUserListVO> list = userService.findMiniProgramUserList(name,phone);
        PageInfo<MiniProgramUserListVO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
    * 通过id查询用户
    */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询用户")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(userService.findByColumn("id",id));
    }

    /**
    * 新增用户
    */
    @PostMapping
    @ApiOperation(value="新增用户")
    public ResultData add (UserDTO user){
        User username = userService.findByColumn("username", user.getUsername());
        if(username!=null){
            return ResultData.result(SystemTip.USER_NAME_REUSE);
        }
        User phone = userService.findByColumn("phone", user.getPhone());
        if(phone!=null){
            return ResultData.result(SystemTip.PHONE_REUSE);
        }
        return Optional.of(userService.insertIgnoreNull(user)).filter(count -> count > 0)
        .map(count -> ResultData.success(user)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    /**
    * 更新新增用户
    */
    @PutMapping
    @ApiOperation(value="更新用户")
    public ResultData update (UserDTO user){
        User username = userService.findByColumn("username", user.getUsername());
        if(username!=null && username.getId()!=user.getId()){
            return ResultData.result(SystemTip.USER_NAME_REUSE);
        }
        User phone = userService.findByColumn("phone", user.getPhone());
        if(phone!=null && phone.getId()!=user.getId()){
            return ResultData.result(SystemTip.PHONE_REUSE);
        }
        return Optional.of(userService.updateIgnoreNull(user)).filter(count -> count > 0)
        .map(count -> ResultData.success(user)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除用户
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除用户")
    public ResultData delete (@PathVariable("id")int id){
        return Optional.of(userService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除用户
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除用户")
    public ResultData batchDelete (int... ids){
        return Optional.of(userService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

}
