package com.mioto.pms.module.device.service;

import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.model.DeviceDTO;
import com.mioto.pms.module.room.model.RoomDeviceRelation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author lizhicai
 * @description
 * @date 2021/3/20
 */
public interface IDeviceService {
    /**
     * 根据条件查询列表
     */
    List<Device> find(Device device);

    int insert(Device device) throws IOException;

    List<DeviceDTO> findList(Device device,String siteId);

    /**
     * 修改对象,忽略空值
     */
    int updateIgnoreNull(Device device) throws IOException;

    /**
     * 根据列名和对应的值删除对象
     */
    int deleteByColumn(String column, String value);

    /**
     * 根据主键列表批量删除
     */
    int batchDelete(String[] ids);

    Device findById(Integer id);

    List<Device> findByIds(String[] arg);

    int insertAll(List<Device> devices) ;

    int addRoomDeviceRelation(String roomId,List<RoomDeviceRelation> roomDeviceRelations);
    /**
     * 删除房屋包含的所有设备资产
     * @param roomId
     * @return
     */
    int clearRoomRelation(String roomId);

    void zipQrCode(Device device, String siteId, HttpServletResponse response);
}
