package com.mioto.pms.module.weixin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.anno.PayStatusChange;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.cost.PayTypeEnum;
import com.mioto.pms.module.cost.service.IPayInfoService;
import com.mioto.pms.module.weixin.WxApiConstant;
import com.mioto.pms.module.weixin.model.MiniProgramUser;
import com.mioto.pms.module.weixin.model.WxUserDTO;
import com.mioto.pms.module.weixin.service.IMiniProgramUserService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.security.utils.JwtTokenUtil;
import com.mioto.pms.utils.BaseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author admin
 * @date 2021-07-15 9:42
 */
@RestController
@RequestMapping("wx")
@Api(tags = "微信相关接口")
@Slf4j
public class WxController {

    @Resource
    private IMiniProgramUserService miniProgramUserService;

    @Resource
    private IPayInfoService payInfoService;

    @ApiOperation(value = "微信小程序登录")
    @PostMapping("login")
    public ResultData login(WxUserDTO userDTO){
        Map<String,Object> map = CollUtil.newHashMap(4);
        map.put("appid",userDTO.getAppid());
        map.put("secret",userDTO.getSecret());
        map.put("js_code",userDTO.getJsCode());
        map.put("grant_type",WxApiConstant.LOGIN_GRANT_TYPE);
        HttpResponse response = HttpUtil.createPost(WxApiConstant.LOGIN_API).form(map).execute();
        return ResultData.success(JSONUtil.parseObj(response.body()));
    }

    @ApiOperation(value = "绑定手机号")
    @PostMapping("bind")
    public ResultData bindPhone(String phone,String openId,String nickName,String verCode){
        MiniProgramUser miniProgramUser = miniProgramUserService.bindPhone(phone,openId,nickName);
        //生成token返回
        JSONObject result = JSONUtil.createObj()
                .set("token", JwtTokenUtil.createAccessToken(miniProgramUser,miniProgramUser.getUserType()))
                .set("logonUser",miniProgramUser);
        return ResultData.success(result);
    }

    @ApiOperation(value = "获取token")
    @GetMapping("token")
    public ResultData getToken(String openId){
        MiniProgramUser miniProgramUser = miniProgramUserService.findByColumn("open_id",openId);
        if (ObjectUtil.isNotEmpty(miniProgramUser)){
            JSONObject result = JSONUtil.createObj()
                    .set("token", JwtTokenUtil.createAccessToken(miniProgramUser,miniProgramUser.getUserType()))
                    .set("logonUser",miniProgramUser);
            return ResultData.success(result);
        }
        return ResultData.result(SystemTip.MINI_USER_NOT_EXIST);
    }


    @ApiOperation(value = "获取微信登录参数")
    @GetMapping("info")
    public ResultData getWxInfo(){
        JSONObject jsonObject = JSONUtil.createObj()
                .putOpt("secret","6b40f66c9da35b9822fe5316b7a6d2bd")
                .putOpt("appid","wxc537d89180b81db3");
        return ResultData.success(jsonObject);
    }


    @ApiOperation(value = "获取签名")
    @GetMapping("sign")
    public ResultData getSign(String method,String mchid,String serialNo, String url, String body){
        return ResultData.success(BaseUtil.getRequestSign(mchid,serialNo,method,url,body));
    }


    @ApiOperation(value = "获取支付签名")
    @GetMapping("/pay/sign")
    public ResultData getPaySign(String appId, String body){
        return ResultData.success(BaseUtil.getPaySign(appId,body));
    }

    @PayStatusChange(type = PayTypeEnum.MINI)
    @ApiOperation(value = "微信支付成功通知")
    @PostMapping("/pay/notify")
    @ApiImplicitParam(name="billNumber",value = "子账单编号",example = "202107211828006743505-1-3-4",dataType="string", paramType = "query",required=true)
    public ResultData notify(String billNumber){
        return Optional.of(payInfoService.miniProgramPay(billNumber))
                .filter(count -> count > 0)
                .map(count -> ResultData.success())
                .orElseThrow(() -> new BasicException(SystemTip.INSERT_FAIL));
    }
}
