package com.mioto.pms.module.device.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.anno.FileClear;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.component.export.ExcelExportFactory;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.model.DeviceDTO;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author lizhicai
 * @description
 * @date 2021/3/19
 */
@RestController
@RequestMapping("device")
@Api(tags = "设备属性")
public class DeviceController {
    @Autowired
    private IDeviceService deviceService;

    /**
     * 查询设备信息
     */
    @GetMapping
    @ApiOperation(value="查询设备信息")
    public ResultData list (Device device){
        return ResultData.success(deviceService.find(device));
    }


    @PostMapping
    @ApiOperation(value = "新增设备信息")
    public ResultData add(Device device) throws IOException {
        return Optional.of(deviceService.insert(device)).filter(count -> count > 0)
                .map(count -> ResultData.success(device)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

     @PostMapping("/all")
     @ApiModelProperty(value = "批量新增设备信息")
     public ResultData addAll(List<Device> devices) throws IOException {
        return Optional.of(deviceService.insertAll(devices)).filter(count -> count > 0)
                .map(count -> ResultData.success(devices)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
     }

    @GetMapping("/pager")
    @ApiOperation(value = "分页查询设备信息")
    public ResultData pager(HttpServletRequest request, Device device,String siteId, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
       List<DeviceDTO> list= deviceService.findList(device,siteId);
        PageInfo<DeviceDTO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出设备信息")
    public void export(HttpServletResponse response, Device device, String siteId){
        List<DeviceDTO> list= deviceService.findList(device,siteId);
        ExcelExportFactory.create(ExcelExportFactory.EXPORT_DEVICE).writeExcel(list,response);
    }

    @GetMapping("/qrcode/export")
    @ApiOperation(value = "导出设备二维码")
    public void qrcodeExport(HttpServletResponse response, Device device, String siteId){
        deviceService.zipQrCode(device,siteId,response);
    }

    /**
     * 更新新增设备信息
     */
    @FileClear
    @PutMapping
    @ApiOperation(value="更新设备信息")
    public ResultData update (Device device) throws IOException {
        return Optional.of(deviceService.updateIgnoreNull(device)).filter(count -> count > 0)
                .map(count -> ResultData.success(device)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
     * 删除设备信息
     */
    @FileClear
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除设备信息")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(deviceService.deleteByColumn("id",id)).filter(count -> count > 0)
                .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
     * 批量删除设备信息
     */
    @FileClear
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除设备信息")
    public ResultData batchDelete (String... ids){
        return Optional.of(deviceService.batchDelete(ids)).filter(count -> count > 0)
                .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }
}
