package com.mioto.pms.module.statistics.controller;

import com.mioto.pms.module.statistics.model.PaymentProgressVO;
import com.mioto.pms.module.statistics.model.PaymentVO;
import com.mioto.pms.module.statistics.model.RoomInfoStatisticsVO;
import com.mioto.pms.module.statistics.service.IStatisticsService;
import com.mioto.pms.result.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author admin
 * @date 2021-07-29 16:46
 */
@RestController()
@RequestMapping("statistics")
@Api(tags = "统计")
public class StatisticsController {

    @Resource
    private IStatisticsService statisticsService;

    @GetMapping("roomInfo")
    @ApiOperation(value="房屋信息统计",response = RoomInfoStatisticsVO.class)
    public ResultData roomInfo(){
        return ResultData.success(statisticsService.findRoomCount());
    }

    @GetMapping("paymentProgress")
    @ApiOperation(value="缴费进度统计",response = PaymentProgressVO.class)
    public ResultData paymentProgress(int month){
        return ResultData.success(statisticsService.paymentProgressCount(month));
    }

    @GetMapping("payment")
    @ApiOperation(value="缴费统计",response = PaymentVO.class)
    @ApiImplicitParam(name="type",value = "统计类型 1-按月统计 2-按年统计",dataType="int", paramType = "query",required=true)
    public ResultData payment(int type){
        return ResultData.success(statisticsService.paymentCount(type));
    }
}
