package com.mioto.pms.module.cost.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.anno.PayStatusChange;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.cost.PayTypeEnum;
import com.mioto.pms.module.cost.model.*;
import com.mioto.pms.module.cost.service.ICostDetailService;
import com.mioto.pms.module.cost.service.ICostInfoService;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.cost.dao.PayInfoDao;
import com.mioto.pms.module.cost.service.IPayInfoService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-23 15:12:53
 */
@Service("payInfoService")
public class PayInfoServiceImpl implements IPayInfoService{

    @Resource
    private PayInfoDao payInfoDao;
    @Resource
    private ICostDetailService costDetailService;

    @Override
    public List<PayInfo> findList(PayInfo payInfo) {
        return payInfoDao.findList(payInfo);
    }

    @Override
    public int insert(PayInfo payInfo) {
        return payInfoDao.insert(payInfo);
    }

    @Override
    public int insertIgnoreNull(PayInfo payInfo) {
        return payInfoDao.insertIgnoreNull(payInfo);
    }

    @Override
    public int update(PayInfo payInfo) {
        return payInfoDao.update(payInfo);
    }

    @Override
    public int updateIgnoreNull(PayInfo payInfo) {
        return payInfoDao.updateIgnoreNull(payInfo);
    }

    @Override
    public PayInfo findByColumn(String column, Object value) {
        return payInfoDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return payInfoDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return payInfoDao.batchDelete(ids);
    }

    @Override
    public int webCashPay(String costType, String[] billNumbers) {
        if (ArrayUtil.isNotEmpty(billNumbers)) {
            Date now = new Date();
            List<CostDetail> costDetailList = costDetailService.findUnpaidFee(costType,billNumbers);
            List<PayInfo> payInfoList = new ArrayList<>(costDetailList.size());
            if (CollUtil.isNotEmpty(costDetailList)){
                PayInfo payInfo;
                for (CostDetail costDetail : costDetailList) {
                    payInfo = new PayInfo();
                    payInfo.setPayTime(now);
                    payInfo.setPayAmount(costDetail.getAmount());
                    payInfo.setPayType(PAY_TYPE_CASH);
                    payInfo.setBillNumber(costDetail.getBillChildNumber());
                    payInfoList.add(payInfo);
                }
                return payInfoDao.pay(payInfoList);
            }
            throw new BasicException(SystemTip.BILL_NUMBER_NOT_EXIST);
        }
        throw new BasicException(SystemTip.BILL_NUMBER_NOT_EMPTY);
    }

    @Override
    public int miniProgramCashPay(String[] billNumbers) {
        if (ArrayUtil.isNotEmpty(billNumbers)){
            List<PayInfo> payInfoList = new ArrayList<>(billNumbers.length);
            PayInfo payInfo;
            Date now =  new Date();
            for (String billNumber : billNumbers) {
                payInfo = new PayInfo();
                CostDetail costDetail = costDetailService.findByColumn("bill_child_number",billNumber);
                if (ObjectUtil.isNotEmpty(costDetail)){
                    payInfo.setPayAmount(costDetail.getAmount());
                }else {
                    throw new BasicException(SystemTip.BILL_NUMBER_NOT_EXIST);
                }
                payInfo.setBillNumber(billNumber);
                payInfo.setPayTime(now);
                payInfo.setPayType(PAY_TYPE_CASH);
                payInfoList.add(payInfo);
            }
            return payInfoDao.pay(payInfoList);
        }
        throw new BasicException(SystemTip.BILL_NUMBER_NOT_EMPTY);
    }

    @Override
    public List<PayListVO> findByPager(PayListDTO payListDTO) {
        if (BaseUtil.getLoginUserRoleId() == 2){
            payListDTO.setUserId(BaseUtil.getLoginUser().getId());
        }
        return payInfoDao.findByPager(payListDTO);
    }
}