package com.mioto.pms.module.cost.controller;

import com.mioto.pms.anno.PayStatusChange;
import com.mioto.pms.module.cost.PayTypeEnum;
import com.mioto.pms.module.cost.model.PayListDTO;
import com.mioto.pms.module.cost.model.PayListVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;
import com.mioto.pms.module.cost.service.IPayInfoService;
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
 * @date 2021-07-23 15:12:53
 */
@RestController
@RequestMapping("payInfo")
@Api(tags = "支付信息管理")
public class PayInfoController {
    @Resource
    private IPayInfoService payInfoService;

    @GetMapping("/pager")
    @ApiOperation(value="分页查询缴费纪录",response = PayListVO.class)
    public ResultData pager (BasePager basePager, PayListDTO payInfo){
        PageHelper.startPage(basePager.getPage(), basePager.getRows(), basePager.getSortBy());
        List<PayListVO> list = payInfoService.findByPager(payInfo);
        PageInfo<PayListVO> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>(4);
        result.put("count", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        return ResultData.success(result);
    }

    @PayStatusChange(type = PayTypeEnum.MINI_CASH)
    @PostMapping("/cash/miniProgram")
    @ApiOperation(value="小程序现金缴费")
    @ApiImplicitParam(name="billNumbers",value = "子账单编号集合",dataType="string", paramType = "query",allowMultiple = true,required=true)
    public ResultData miniProgramCashPay (String... billNumbers){
        return Optional.of(payInfoService.miniProgramCashPay(billNumbers))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

    @PayStatusChange(type = PayTypeEnum.WEB_CASH)
    @PostMapping("/cash/web")
    @ApiOperation(value="web现金缴费")
    @ApiImplicitParams({
            @ApiImplicitParam(name="costType",value = "缴费类型id 如果是全部 - 传空字符串",dataType="string", paramType = "query",required=true),
            @ApiImplicitParam(name="billNumbers",value = "主账单编号集合",dataType="string", paramType = "query",allowMultiple = true,required=true)
    })
    public ResultData webCashPay (String costType,String... billNumbers){
        return Optional.of(payInfoService.webCashPay(costType,billNumbers))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }

}