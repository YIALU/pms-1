package com.mioto.pms.aop;

import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.service.impl.DeviceServiceImpl;
import com.mioto.pms.module.file.service.FileService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lizhicai
 * @description 文件删除aop类
 * @date 2021/4/7
 */
@Aspect
@Component
public class DeleteFileAspect {
    @Autowired
    private DeviceServiceImpl deviceService;
    @Resource
    private FileService fileService;

    @Before("@annotation(com.mioto.pms.anno.FileClear)")
    public void deleteFileInfo(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        if(args[0] instanceof Device){
            Device device = (Device) args[0];
            fileService.deleteByColumn("id",device.getCode());
        }

        if (args[0] instanceof String){
            Device device=  deviceService.findById(Integer.parseInt(args[0].toString()));
            fileService.deleteByColumn("id",device.getCode());
            }

        if(args[0] instanceof String[]){
            String[] arg = (String[]) args[0];
         List<Device> list= deviceService.findByIds(arg);
            for(Device device:list){
                fileService.deleteByColumn("id",device.getCode());
            }

        }
    }
}
