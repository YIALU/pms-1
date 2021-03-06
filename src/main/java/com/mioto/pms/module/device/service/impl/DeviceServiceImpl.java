package com.mioto.pms.module.device.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.device.dao.DeviceDao;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.model.DeviceDTO;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.module.file.FileHelper;
import com.mioto.pms.module.room.model.RoomDeviceRelation;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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
        device.setUserId(BaseUtil.getLogonUserId());
        return deviceDao.find(device);
    }


    @Override
    public int insert(Device device) throws IOException {
        //生成设备二维码
        device.setUserId(BaseUtil.getLoginUser().getId());
        if (StrUtil.isNotEmpty(device.getFocus())){
            if (ObjectUtil.isNotEmpty(deviceDao.findByLineAndfocus(device.getLine(),device.getFocus()))){
                throw new BasicException(SystemTip.FOCUS_LIN_REPEAT_ERROR);
            }
        }
        int add = deviceDao.add(device);
        fileHelper.createCode(device);
        return add;
    }



    @Override
    public List<DeviceDTO> findList(Device device,String siteId) {
        device.setUserId(BaseUtil.getLogonUserId());
        List<DeviceDTO> deviceDTOList = deviceDao.findList(device,siteId);
        for (DeviceDTO deviceDTO : deviceDTOList) {
            if (ChannelUtil.containsKey(deviceDTO.getFocus())){
                deviceDTO.setStatus(1);
            }
        }
        return deviceDTOList;
    }

    /**
     * 修改对象,忽略空值
     */
    @Override
    public int updateIgnoreNull(Device device) throws IOException {
        if (StrUtil.isNotEmpty(device.getFocus()) && ObjectUtil.isNotEmpty(device.getLine())){
            if (ObjectUtil.isNotEmpty(deviceDao.findByLineAndfocus(device.getLine(),device.getFocus()))){
                throw new BasicException(SystemTip.FOCUS_LIN_REPEAT_ERROR);
            }
        }
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

    @Override
    public void zipQrCode(Device device, String siteId, HttpServletResponse response) {
        File file = fileHelper.zipQrCode(findList(device,siteId));
        fileHelper.downloadZip(response,file);
    }

    @Override
    public List<Device> findByRoomId(String roomId) {
        return deviceDao.findByRoomId(roomId);
    }

    @Override
    public List<Device> findByFocus(Set<String> focus) {
        return deviceDao.findByFocus(focus);
    }

    @Override
    public Device findByLineAndfocus(int line, String focus) {
        return deviceDao.findByLineAndfocus(line,focus);
    }

    @Override
    public int updateOnOffStatusByDeviceIdAndFocus(String deviceId, String focus, int onOffStatus) {
        return deviceDao.updateOnOffStatusByDeviceIdAndFocus(deviceId,focus,onOffStatus);
    }
}
