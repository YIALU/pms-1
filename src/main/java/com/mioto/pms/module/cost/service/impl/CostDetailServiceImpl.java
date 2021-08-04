package com.mioto.pms.module.cost.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.cost.model.CostDetailDTO;
import com.mioto.pms.module.cost.model.EditCostDetailDTO;
import com.mioto.pms.module.cost.service.ICostInfoService;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.cost.dao.CostDetailDao;
import com.mioto.pms.module.cost.model.CostDetail;
import com.mioto.pms.module.cost.service.ICostDetailService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-16 15:05:10
 */
@Service("costDetailService")
public class CostDetailServiceImpl implements ICostDetailService{

    @Resource
    private CostDetailDao costDetailDao;
    @Resource
    private ICostInfoService costInfoService;

    @Override
    public List<CostDetail> findList(CostDetail costDetail) {
        return costDetailDao.findList(costDetail);
    }

    @Override
    public int insert(CostDetail costDetail) {
        return costDetailDao.insert(costDetail);
    }

    @Override
    public int insertIgnoreNull(CostDetail costDetail) {
        return costDetailDao.insertIgnoreNull(costDetail);
    }

    @Override
    public int update(CostDetail costDetail) {
        return costDetailDao.update(costDetail);
    }

    @Override
    public int updateIgnoreNull(CostDetail costDetail) {
        return costDetailDao.updateIgnoreNull(costDetail);
    }

    @Override
    public CostDetail findByColumn(String column, Object value) {
        return costDetailDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return costDetailDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return costDetailDao.batchDelete(ids);
    }

    @Override
    public List<CostDetail> findByRentalId(String rentalId) {
        return costDetailDao.findByRentalId(rentalId);
    }

    @Override
    public List<CostDetail> findUnpaidFee(String costType, String[] billNumbers) {
        return costDetailDao.findUnpaidFee(costType,billNumbers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchEdit(EditCostDetailDTO editCostDetailDTO) {
        editCostDetailDTO.getCostDetailDTOList().stream().forEach(costDetailDTO -> {
            if (costDetailDTO.getEditType() == 1){
                CostDetail costDetail = builder(costDetailDTO,editCostDetailDTO.getId());
                int count = costDetailDao.findCountByCostId(editCostDetailDTO.getId());
                costDetail.setBillChildNumber(editCostDetailDTO.getBillNumber() + StrUtil.DASHED + ++count);
                costDetailDao.insertIgnoreNull(costDetail);
            }else if (costDetailDTO.getEditType() == 2){
                CostDetail costDetail = builder(costDetailDTO,editCostDetailDTO.getId());
                costDetail.setId(costDetailDTO.getId());
                costDetailDao.updateIgnoreNull(costDetail);
            }else if (costDetailDTO.getEditType() == 3){
                costDetailDao.deleteByColumn("id",costDetailDTO.getId());
            }else {
                throw new RuntimeException("修改类型参数传递错误，必须是1、2、3");
            }
        });
        //修改主账单金额
        costInfoService.updateAmount(editCostDetailDTO.getId());
        return 1;
    }

    private CostDetail builder(CostDetailDTO costDetailDTO, String costId){
        CostDetail costDetail = new CostDetail();
        costDetail.setType(BILL_TYPE_NON_GEN);
        costDetail.setCostStartTime(costDetailDTO.getStartDate());
        costDetail.setCostEndTime(costDetailDTO.getEndDate());
        costDetail.setCostStartData(costDetailDTO.getStartData());
        costDetail.setCostEndData(costDetailDTO.getEndData());
        costDetail.setAmount(costDetailDTO.getAmount());
        costDetail.setCostType(costDetailDTO.getCostTypeId());
        costDetail.setCostInfoId(costId);
        return costDetail;
    }
}