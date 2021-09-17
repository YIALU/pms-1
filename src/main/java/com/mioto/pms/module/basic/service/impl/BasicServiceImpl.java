package com.mioto.pms.module.basic.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mioto.pms.cache.VerCodeCache;
import com.mioto.pms.module.basic.dao.BasicDao;
import com.mioto.pms.module.basic.model.Scheduler;
import com.mioto.pms.module.basic.service.IBasicService;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.utils.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2021-08-27 14:53
 */
@Service
@Slf4j
public class BasicServiceImpl implements IBasicService {
    @Value("${sms.regionId}")
    private String regionId;
    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.secret}")
    private String secret;
    @Value("${sms.signName}")
    private String signName;
    @Value("${sms.templateCode.verCode}")
    private String verTemplateCode;
    @Value("${sms.templateCode.overdueNotify}")
    private String overdueNotifyTemplateCode;
    @Resource
    private VerCodeCache verCodeCache;
    @Resource
    private IDeviceService deviceService;

    @Resource
    private BasicDao basicDao;

    @Override
    public boolean sendVerCodeSms(String phone) {
        String verCode = BaseUtil.genVerCode();
        boolean isSend = send(phone,verTemplateCode,JSONUtil.createObj().set("code",verCode).set("product","物业系统").toString());
        if (isSend) {
            verCodeCache.put(phone, verCode);
            log.info("短信发送 - {} 成功,验证码 - {}", phone, verCode);
        }
        return isSend;
    }

    @Override
    public boolean sendoVerdueNotifySms(String phone) {
        return send(phone,overdueNotifyTemplateCode,JSONUtil.createObj().set("code","").set("product","这是一条催收短信").toString());
    }

    @Override
    public boolean saveScheduler(Scheduler scheduler) {
        return basicDao.saveScheduler(scheduler) > 0 ? true : false;
    }

    @Override
    public boolean removeScheduler(String schedulerId) {
        return basicDao.removeScheduler(schedulerId) > 0 ? true : false;
    }

    @Override
    public List<Scheduler> findListByType(String schedulerType) {
        List<Scheduler> list =  basicDao.findListByType(schedulerType);
        if (CollUtil.isNotEmpty(list)){
            return list;
        }

        return new ArrayList<>(0);
    }

    @Override
    public boolean sendMeterReadingCmd(String roomId) {
        List<Device> deviceList = deviceService.findByRoomId(roomId);
        int result = 0;
        if (CollUtil.isNotEmpty(deviceList)){
            for (Device device : deviceList) {
                if (StrUtil.isNotEmpty(device.getFocus()) && ChannelUtil.containsKey(device.getFocus())){
                    TcpHelper.meterReading(device.getLine(),device.getFocus());
                    result++;
                }
            }
        }
        if (result == deviceList.size()){
            return true;
        }
        return false;
    }

    private boolean send(String phoneNumbers, String templateCode, String templateParam){
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (StrUtil.equals(JSONUtil.parseObj(response.getData()).getStr("Code"),"OK")){
                return true;
            }
            log.error("短信发送失败- {}",response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
