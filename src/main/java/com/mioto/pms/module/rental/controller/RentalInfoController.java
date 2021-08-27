package com.mioto.pms.module.rental.controller;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.anno.MeterReadingAnno;
import com.mioto.pms.component.bill.CancellationBill;
import com.mioto.pms.module.meter.MeterReadType;
import com.mioto.pms.module.rental.model.*;
import org.springframework.web.bind.annotation.*;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.component.BasePager;
import com.mioto.pms.exception.BasicException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:11
 */
@RestController
@RequestMapping("rentalInfo")
@Api(tags = "房屋出租信息管理")
public class RentalInfoController {
    @Resource
    private IRentalInfoService rentalInfoService;
    @Resource
    private CancellationBill cancellationBill;

    /**
     * 分页查询列表
     */
    @GetMapping("/pager")
    @ApiOperation(value="分页查询房屋出租信息",notes = "出租管理列表查询",response = RentalListVO.class)
    public ResultData pager (RentalDTO rentalDTO, BasePager basePager){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<RentalListVO> list = rentalInfoService.findList(rentalDTO);
        PageInfo<RentalListVO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    /**
     * 查询
     */
    @GetMapping("/{id}")
    @ApiOperation(value="通过id查询房屋出租信息",response = RentalDetailVO.class)
    public ResultData findById (@PathVariable("id")String id){
        return ResultData.success(rentalInfoService.findDetailById(id));
    }

    @MeterReadingAnno(type = MeterReadType.INSERT)
    @PostMapping
    @ApiOperation(value="新增房屋出租信息",notes = "租住办理")
    public ResultData add (@RequestBody RentalDetailDTO rentalInfo){
        if (StrUtil.isNotEmpty(rentalInfo.getRoomId())) {
            return Optional.of(rentalInfoService.insertIgnoreNull(rentalInfo))
                    .filter(count -> count > 0)
                    .map(count -> ResultData.success(rentalInfo))
                    .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
        }
        throw new RuntimeException("房间id不能为空");
    }

    @MeterReadingAnno(type = MeterReadType.UPDATE)
    @PutMapping
    @ApiOperation(value="更新房屋出租信息")
    public ResultData update (@RequestBody RentalDetailDTO rentalInfo){
        return Optional.of(rentalInfoService.updateIgnoreNull(rentalInfo))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(rentalInfo))
                .orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除房屋出租信息")
    public ResultData delete (@PathVariable("id")String id){
        return Optional.of(rentalInfoService.deleteByColumn("id",id))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @ApiOperation(value="批量删除房屋出租信息")
    public ResultData batchDelete (String... ids){
        return Optional.of(rentalInfoService.batchDelete(ids))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.DELETE_FAIL));
    }

    @GetMapping("/cancellation/{rentalId}")
    @ApiOperation(value="查询退租办理信息",response = CancellationVO.class)
    public ResultData findCancellation (@PathVariable("rentalId")String rentalId){
        return ResultData.success(rentalInfoService.findCancellation(rentalId));
    }

    @GetMapping("/wx/cancellation/{rentalId}")
    @ApiOperation(value="小程序查询退租办理信息",response = WxCancellationVO.class)
    public ResultData findWxCancellation (@PathVariable("rentalId")String rentalId){
        return ResultData.success(rentalInfoService.findWxCancellation(rentalId));
    }


    @PostMapping("/cancellation")
    @ApiOperation(value="退租办理")
    public ResultData cancellation (@RequestBody CancellationDTO cancellationDTO){
        cancellationBill.create(cancellationDTO);
        return ResultData.success();
    }
}