package com.mioto.pms.module.weixin.controller;

import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.cost.service.ICostInfoService;
import com.mioto.pms.module.weixin.model.LastMeterReadVO;
import com.mioto.pms.module.weixin.model.ManualMeterReadDTO;
import com.mioto.pms.module.weixin.model.TenantListVO;
import com.mioto.pms.module.weixin.model.UnpaidFeesVO;
import com.mioto.pms.module.weixin.service.IMiniProgramUserService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author admin
 * @date 2021-08-10 16:25
 */
@RestController
@RequestMapping("/wx/landlord")
@Api(tags = "小程序房东接口")
public class WxLandlordController {

    @Resource
    private IMiniProgramUserService miniProgramUserService;
    @Resource
    private ICostInfoService costInfoService;

    @GetMapping("/unpaid/list")
    @ApiOperation(value="房屋未缴费账单明细",response = UnpaidFeesVO.class)
    public ResultData unpaidFees (String rentalId){
        return ResultData.success(miniProgramUserService.findUnpaidFees(rentalId));
    }

    @GetMapping("/tenant/list")
    @ApiOperation(value="租户列表",response = TenantListVO.class)
    public ResultData findTenantList (){
        return ResultData.success(miniProgramUserService.findTenantList());
    }


    @GetMapping("/last/data")
    @ApiOperation(value="查询上月抄表数据",response = LastMeterReadVO.class)
    public ResultData findLastData (String rentalId){
        return ResultData.success(miniProgramUserService.findLastData(rentalId));
    }

    @PostMapping("/manual")
    @ApiOperation(value="手动抄表")
    public ResultData manualMeterRead (@RequestBody ManualMeterReadDTO meterReadDTO){
        return Optional.of(costInfoService.manualMeterRead(meterReadDTO)).filter(count -> count > 0)
                .map(count -> ResultData.success()).orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    @GetMapping("/personal/center")
    @ApiOperation(value="个人中心")
    public ResultData getPersonalCenterData(String date){
        return ResultData.success(miniProgramUserService.getPersonalCenterData(date));
    }
}
