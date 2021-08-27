package com.mioto.pms.module.room.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.module.furniture.service.FurnitureService;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.room.dao.RoomDao;
import com.mioto.pms.module.room.dao.RoomPricingStrategyDao;
import com.mioto.pms.module.room.model.*;
import com.mioto.pms.module.room.service.RoomService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @auther lzc
* date 2021-04-28 15:49:17
*/
@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomDao roomDao;
    @Resource
    private FurnitureService furnitureService;
    @Resource
    private IDeviceService deviceService;
    @Resource
    private RoomPricingStrategyDao roomPricingStrategyDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<RoomListVO> findList(Integer siteId,String deviceId) {
        return roomDao.findList(siteId,deviceId,BaseUtil.getLogonUserId());
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Room findByColumn(String column, String value) {
        return roomDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertIgnoreNull(RoomDetailDTO roomDetailDTO)  {
        String id = IdUtil.simpleUUID();
        roomDetailDTO.setId(id);
        roomDetailDTO.setCreateTime(new Date());
        roomDetailDTO.setUserId(BaseUtil.getLoginUser().getId());
        int result = roomDao.insertIgnoreNull(roomDetailDTO);
        if (result > 0) {
            //新增房屋与设备的关系
            if (CollUtil.isNotEmpty(roomDetailDTO.getDeviceRelations())) {
                deviceService.addRoomDeviceRelation(id, roomDetailDTO.getDeviceRelations());
            }
            //新增房屋与其他资产的关系
            if (ArrayUtil.isNotEmpty(roomDetailDTO.getFurnitureIds())) {
                furnitureService.addRoomRelation(roomDetailDTO.getFurnitureIds(), id);
            }
            //添加房屋收费策略列表
            if (ArrayUtil.isNotEmpty(roomDetailDTO.getPricingStrategyIds())) {
                roomPricingStrategyDao.insertRelations(id, roomDetailDTO.getPricingStrategyIds());
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateIgnoreNull(RoomDetailDTO room) {
        int result = 1;
        if (!BaseUtil.checkObjIsNullIgnoreId(room,true)){
            result = roomDao.updateIgnoreNull(room);
        }
        if (CollUtil.isNotEmpty(room.getDeviceRelations())) {
            deviceService.clearRoomRelation(room.getId());
            deviceService.addRoomDeviceRelation(room.getId(), room.getDeviceRelations());
        }
        if (ArrayUtil.isNotEmpty(room.getFurnitureIds())) {
            furnitureService.clearRoomRelation(room.getId());
            furnitureService.addRoomRelation(room.getFurnitureIds(), room.getId());
        }
        if (ArrayUtil.isNotEmpty(room.getPricingStrategyIds())) {
            roomPricingStrategyDao.delByRoomId(room.getId());
            roomPricingStrategyDao.insertRelations(room.getId(), room.getPricingStrategyIds());
        }
        return result;
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return roomDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return roomDao.batchDelete(ids);
    }

    @Override
    public RoomDetailVO findDetailById(String roomId) {
        return roomDao.findDetailById(roomId);
    }

    @Override
    public List<WxFreeRoomVO> findFreeRooms(String address) {
        return roomDao.findFreeRooms(address);
    }

    @Override
    public List<String> findDynamicCostTypes(String roomId) {
        List<String> costTypeList = roomDao.findCostTypes(roomId);
        if (CollUtil.isNotEmpty(costTypeList)) {
            List<String> list = new ArrayList<>(costTypeList.size());
            for (String s : costTypeList) {
                if (PricingStrategyEnum.getInstance(s).getType() == 1) {
                    list.add(s);
                }
            }
            return list;
        }
        return costTypeList;
    }
}
