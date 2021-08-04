package com.mioto.pms.module.cost.controller;

import com.mioto.pms.module.cost.model.*;
import com.mioto.pms.module.cost.service.ICostDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.mioto.pms.module.cost.service.ICostInfoService;
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
 * @date 2021-07-16 15:05:11
 */
@RestController
@RequestMapping("costInfo")
@Api(tags = "费用信息管理")
public class CostInfoController {
    @Resource
    private ICostInfoService costInfoService;

    @Resource
    private ICostDetailService costDetailService;

    @GetMapping("/test")
    @ApiOperation(value="账单生成测试")
    public ResultData test(String roomId){
       costInfoService.insertDetail(roomId,true);
       return ResultData.success();
    }

    @GetMapping("/pager")
    @ApiOperation(value="分页查询费用信息",response = CostListVO.class)
    public ResultData pager (BasePager basePager, CostListDTO costListDTO){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<CostListVO> list = costInfoService.findCostList(costListDTO);
        PageInfo<CostListVO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    @GetMapping("/batch/send")
    @ApiOperation(value="批量发送账单")
    public ResultData batchSend (String... costInfoIds){
        return Optional.of(costInfoService.batchSend(costInfoIds))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.BILL_NUMBER_SEND_FAIL));
    }

    @GetMapping("/detail")
    @ApiOperation(value="获取账单详情",response = CostDetailVO.class)
    public ResultData findDetail (String costInfoId){
        return ResultData.success(costInfoService.findDetail(costInfoId));
    }

    @PostMapping("/subBill")
    @ApiOperation(value="账单详情-编辑子账单")
    public ResultData editSubBill (@Validated @RequestBody EditCostDetailDTO costDetail){
        int result = costDetailService.batchEdit(costDetail);
        return result > 0 ? ResultData.success() :  ResultData.result(SystemTip.INSERT_FAIL);
    }
}