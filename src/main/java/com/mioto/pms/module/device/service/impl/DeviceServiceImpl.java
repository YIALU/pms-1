package com.mioto.pms.module.device.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.module.device.dao.DeviceDao;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.model.DeviceDTO;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.module.file.FileHelper;
import com.mioto.pms.module.file.model.FileInfo;
import com.mioto.pms.module.file.service.FileService;
import com.mioto.pms.module.room.model.RoomDeviceRelation;
import com.mioto.pms.result.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lizhicai
 * @description
 * @date 2021/3/20
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    private DeviceDao deviceDao;

    @Resource
    private FileHelper fileHelper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<Device> find(Device device) {
        return deviceDao.find(device);
    }


    @Override
    public int insert(Device device) throws IOException {
        //生成设备二维码
        fileHelper.createCode(device);
        int add = deviceDao.add(device);
        return add;
    }



    @Override
    public List<DeviceDTO> findList(Device device) {
        return deviceDao.findList(device);
    }

    /**
     * 修改对象,忽略空值
     */
    @Override
    public int updateIgnoreNull(Device device) throws IOException {
        fileHelper.createCode(device);
        return deviceDao.updateIgnoreNull(device);
    }

    /**
     * 根据列名和对应的值删除对象
     */
    @Override
    public int deleteByColumn(String column, String value) {
        return deviceDao.deleteByColumn(column,value);
    }

    /**
     * 根据主键列表批量删除
     */
    @Override
    public int batchDelete(String[] ids) {
        return deviceDao.batchDelete(ids);
    }

    @Override
    public Device findById(Integer id) {
        return deviceDao.findById(id);
    }

    @Override
    public List<Device> findByIds(String[] arg) {
        return deviceDao.findByIds(arg);
    }

    @Override
    public int insertAll(List<Device> devices) {
        for (Device device:devices){
            //生成设备二维码
            fileHelper.createCode(device);
        }
        return deviceDao.insertAll(devices);
    }

    @Override
    public int addRoomDeviceRelation(String roomId,List<RoomDeviceRelation> roomDeviceRelations) {
        return deviceDao.addRoomDeviceRelation(roomId,roomDeviceRelations);
    }

    @Override
    public int clearRoomRelation(String roomId) {
        return deviceDao.clearRoomRelation(roomId);
    }
}
