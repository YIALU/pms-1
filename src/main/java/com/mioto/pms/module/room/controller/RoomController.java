package com.mioto.pms.module.room.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mioto.pms.anno.MeterReadingAnno;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.component.export.ExcelExportFactory;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.meter.MeterReadType;
import com.mioto.pms.module.room.model.RoomDetailDTO;
import com.mioto.pms.module.room.model.RoomListVO;
import com.mioto.pms.module.room.model.WxFreeRoomVO;
import com.mioto.pms.module.room.service.RoomService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
* @auther lzc
* date 2021-04-28 15:49:17
*/
@RestController
@RequestMapping("room")
@Api(tags = "开户配表")
public class RoomController {
    @Resource
    private RoomService roomService ;


    @GetMapping
    @ApiOperation(value="查询开户配表")
    public ResultData list (Integer siteId,String deviceId){
        return ResultData.success(roomService.findList(siteId,deviceId));
    }


    @GetMapping("/pager")
    @ApiOperation(value="分页查询开户配表",response = RoomListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name="siteId",value = "区域id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="deviceId",value = "水电表id",dataType="string", paramType = "query")
    })
    public ResultData pager (Integer siteId,String deviceId, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<RoomListVO> list = roomService.findList(siteId,deviceId);
        PageInfo<RoomListVO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    @GetMapping("/export")
    @ApiOperation(value="导出开户配表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="siteId",value = "区域id",dataType="Integer", paramType = "query"),
            @ApiImplicitParam(name="deviceId",value = "水电表id",dataType="string", paramType = "query")
    })
    public void export (Integer siteId, String deviceId, HttpServletResponse response){
        List<RoomListVO> list = roomService.findList(siteId,deviceId);
        ExcelExportFactory.create(ExcelExportFactory.EXPORT_ACCOUNT).writeExcel(list,response);
    }

    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询开户配表")
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(roomService.findDetailById(id));
    }


    @GetMapping("/dynamic/costTypes")
    @ApiOperation(value="通过房屋id查询按使用量计费的收费类型列表")
    public ResultData findDynamicCostTypes (String roomId){
        return ResultData.success(roomService.findDynamicCostTypes(roomId));
    }

    /**
    * 新增开户配表
    */
    @PostMapping
    @ApiOperation(value="新增开户配表")
    public ResultData add (@RequestBody RoomDetailDTO roomDetailDTO){
        return Optional.of(roomService.insertIgnoreNull(roomDetailDTO)).filter(count -> count > 0)
        .map(count -> ResultData.success(roomDetailDTO)).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    @MeterReadingAnno(type = MeterReadType.UPDATE)
    @PutMapping
    @ApiOperation(value="更新开户配表",notes="修改房屋基础信息")
    public ResultData update (@RequestBody RoomDetailDTO room){
        return Optional.of(roomService.updateIgnoreNull(room)).filter(count -> count > 0)
        .map(count -> ResultData.success(room)).orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
    * 删除开户配表
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除开户配表")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(roomService.deleteByColumn("id",id)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
    * 批量删除开户配表
    */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除开户配表")
    public ResultData batchDelete (String... ids){
        return Optional.of(roomService.batchDelete(ids)).filter(count -> count > 0)
        .map(ResultData::success).orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    @ApiOperation(value = "小程序获取空闲房源",response = WxFreeRoomVO.class)
    @GetMapping("/free")
    public ResultData getFreeRooms(BasePager basePager,String address){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        PageInfo<WxFreeRoomVO> pageInfo = new PageInfo<>(roomService.findFreeRooms(address));
        Map<String, Object> result = new HashMap<>(2);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }
}
