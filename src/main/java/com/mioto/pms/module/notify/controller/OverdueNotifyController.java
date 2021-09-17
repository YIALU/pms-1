package com.mioto.pms.module.notify.controller;

import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.notify.model.OverdueNotify;
import com.mioto.pms.module.notify.service.IOverdueNotifyService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 *
 * @author qinxj
 * @date 2021-07-14 14:43:18
 */
@RestController
@RequestMapping("overdueNotify")
@Api(tags = "催收通知管理")
public class OverdueNotifyController {
    @Resource
    private IOverdueNotifyService overdueNotifyService;

    @GetMapping
    @ApiOperation(value="查询催收通知",response = OverdueNotify.class)
    public ResultData get (){
        return ResultData.success(overdueNotifyService.find());
    }


    @PutMapping
    @ApiOperation(value="更新催收通知")
    public ResultData update (OverdueNotify overdueNotify){
        return Optional.of(overdueNotifyService.updateIgnoreNull(overdueNotify))
                .filter(count -> count > 0)
                .map(count -> ResultData.success(overdueNotify))
                .orElseThrow(() -> new BasicException(SystemTip.UPDATE_FAIL));
    }
}