package com.mioto.pms.module.furniture.service.impl;

import com.mioto.pms.module.furniture.dao.FurnitureDao;
import com.mioto.pms.module.furniture.model.Furniture;
import com.mioto.pms.module.furniture.model.FurnitureListVO;
import com.mioto.pms.module.furniture.service.FurnitureService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @auther lzc
* date 2021-04-21 16:22:29
*/
@Service("furnitureService")
public class FurnitureServiceImpl implements FurnitureService {

    @Resource
    private FurnitureDao furnitureDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<FurnitureListVO> findList(Furniture furniture) {
        furniture.setUserId(BaseUtil.getLogonUserId());
        return furnitureDao.findList(furniture);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Furniture findByColumn(String column, String value) {
        return furnitureDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Furniture furniture) {
         furniture.setUserId(BaseUtil.getLoginUser().getId());
         return furnitureDao.insertIgnoreNull(furniture);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Furniture furniture) {
        return furnitureDao.updateIgnoreNull(furniture);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, Object value) {
        return furnitureDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(Integer[] ids) {
        return furnitureDao.batchDelete(ids);
    }

    @Override
    public int insertAll(List<Furniture> furnitures) {
        return furnitureDao.insertAll(furnitures);
    }

    @Override
    public String[] findFurnitureNames() {
        return furnitureDao.findFurnitureNames();
    }

    @Override
    public int addRoomRelation(Integer[] furnitureIds, String roomId) {
        return furnitureDao.addRoomRelation(furnitureIds,roomId);
    }

    @Override
    public int clearRoomRelation(String roomId) {
        return furnitureDao.clearRoomRelation(roomId);
    }
}
