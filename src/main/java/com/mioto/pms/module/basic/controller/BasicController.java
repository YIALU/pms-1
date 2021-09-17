package com.mioto.pms.module.basic.controller;

import com.mioto.pms.module.basic.service.IBasicService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author admin
 * @date 2021-08-27 14:50
 */
@RestController
@RequestMapping("basic")
@Api(tags = "通用接口")
public class BasicController {
    @Resource
    private IBasicService basicService;


    @GetMapping("/sendSms")
    @ApiOperation(value="发送登录验证码")
    public ResultData sendSms(String phone){
        return basicService.sendVerCodeSms(phone) ? ResultData.success("短信发送成功") : ResultData.result(SystemTip.SEND_SMS_CODE_FAIL);
    }
}
