package com.mioto.pms.module.weixin.controller;

import com.mioto.pms.module.cost.model.CostDetail;
import com.mioto.pms.module.cost.service.ICostDetailService;
import com.mioto.pms.module.weixin.model.ContractInfoVO;
import com.mioto.pms.module.weixin.model.TenantBillVO;
import com.mioto.pms.module.weixin.model.TenantHomeVO;
import com.mioto.pms.module.weixin.service.IMiniProgramUserService;
import com.mioto.pms.result.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author admin
 * @date 2021-08-10 14:20
 */
@RestController
@RequestMapping("/wx/tenant")
@Api(tags = "小程序租户接口")
public class WxTenantController {
    @Resource
    private IMiniProgramUserService miniProgramUserService;
    @Resource
    private ICostDetailService costDetailService;

    @GetMapping("/home")
    @ApiOperation(value="租户首页数据",response = TenantHomeVO.class)
    public ResultData home (){
        return ResultData.success(miniProgramUserService.findHomeData());
    }

    @GetMapping("/contract")
    @ApiOperation(value="合同信息",response = ContractInfoVO.class)
    public ResultData findContract (String rentalId){
        return ResultData.success(miniProgramUserService.findContractInfo(rentalId));
    }

    @GetMapping("/billDetails")
    @ApiOperation(value="账单明细",response = CostDetail.class)
    public ResultData findBillDetails (String costInfoId){
        return ResultData.success(costDetailService.findListByCostInfoId(costInfoId));
    }

    @GetMapping("/history/bill")
    @ApiOperation(value="历史账单列表",response = TenantBillVO.class)
    public ResultData findHistoryBills (String rentalId){
        return ResultData.success(miniProgramUserService.findHistoryBills(rentalId));
    }
}
