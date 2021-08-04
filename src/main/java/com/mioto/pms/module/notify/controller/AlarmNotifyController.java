package com.mioto.pms.module.notify.controller;

import org.springframework.web.bind.annotation.*;
import com.mioto.pms.module.notify.model.AlarmNotify;
import com.mioto.pms.module.notify.service.IAlarmNotifyService;
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
 * @date 2021-07-14 14:43:19
 */
@RestController
@RequestMapping("alarmNotify")
@Api(tags = "设备报警通知管理")
public class AlarmNotifyController {
    @Resource
    private IAlarmNotifyService alarmNotifyService;

    @GetMapping
    @ApiOperation(value="查询设备报警通知",response = AlarmNotify.class)
    public ResultData get(){
        return ResultData.success(alarmNotifyService.find());
    }

 /*   *//**
     * 分页查询列表
     *//*
    @GetMapping("/pager")
    @ApiOperation(value="分页查询设备报警通知",response = AlarmNotify.class)
    public ResultData pager (HttpServletRequest request, AlarmNotify alarmNotify,BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<AlarmNotify> list = alarmNotifyService.findList(alarmNotify);
        PageInfo<AlarmNotify> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }
*/
 /*   *//**
     * 查询
     *//*
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询设备报警通知")
    public ResultData findById (@PathVariable("id")Integer id){
        return ResultData.success(alarmNotifyService.findByColumn("id",id));
    }
*/
/*    *//**
     * 新增
     *//*
    @PostMapping
    @ApiOperation(value="新增设备报警通知")
    public ResultData add (AlarmNotify alarmNotify){
        return Optional.of(alarmNotifyService.insertIgnoreNull(alarmNotify))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(alarmNotify))
                .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }*/

    @PutMapping
    @ApiOperation(value="更新设备报警通知")
    public ResultData update (AlarmNotify alarmNotify){
        return Optional.of(alarmNotifyService.updateIgnoreNull(alarmNotify))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(alarmNotify))
                .orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

  /*  *//**
     * 删除
     *//*
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除设备报警通知")
    public ResultData delete (@PathVariable("id")Integer id){
        return Optional.of(alarmNotifyService.deleteByColumn("id",id))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    *//**
     * 批量删除
     *//*
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除设备报警通知")
    public ResultData batchDelete (Integer... ids){
        return Optional.of(alarmNotifyService.batchDelete(ids))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }*/
}