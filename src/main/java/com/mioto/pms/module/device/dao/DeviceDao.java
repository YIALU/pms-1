package com.mioto.pms.module.device.dao;

import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.model.DeviceDTO;
import com.mioto.pms.module.room.model.RoomDeviceRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author lizhicai
 * @description
 * @date 2021/3/20
 */
@Repository
public interface DeviceDao {
   /**
    * 根据条件查询列表
    * @return
    */
   List<Device> find(Device device);

   /**
    * 新增设备
    * @param device
    * @return
    */
   int add(Device device);

   List<DeviceDTO> findList(@Param("device") Device device,@Param("siteId")String siteId);


   List<Device> findByIds(String[] ids);
   /**
    * 修改对象,忽略空值
    * @return
    */
   int updateIgnoreNull(Device device);

   /**
    * 根据列名和对应的值删除对象
    * @param column
    * @param value
    * @return
    */
   int deleteByColumn(@Param("column") String column, @Param("value") String value);

   /**
    * 根据主键列表批量删除
    * @param ids
    * @return
    */
   int batchDelete(String[] ids);

   Device findById(Integer id);

    int insertAll(List<Device> devices);

    int addRoomDeviceRelation(@Param("roomId") String roomId, @Param("list")List<RoomDeviceRelation> roomDeviceRelations);

    List<Device> findByRoomId(String roomId);

    int clearRoomRelation(String roomId);

    List<Device> findByFocus(Set<String> focus);

    Device findByLineAndfocus(@Param("line")int line, @Param("focus")String focus);

    int updateOnOffStatusByDeviceIdAndFocus(@Param("deviceId")String deviceId, @Param("focus")String focus, @Param("onOffStatus")int onOffStatus);
}
