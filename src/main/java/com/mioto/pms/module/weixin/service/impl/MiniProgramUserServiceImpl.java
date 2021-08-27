package com.mioto.pms.module.weixin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.module.rental.model.RentalInfo;
import com.mioto.pms.module.rental.model.TenantInfo;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.module.rental.service.IRentalInitService;
import com.mioto.pms.module.rental.service.ITenantInfoService;
import com.mioto.pms.module.user.model.UserVO;
import com.mioto.pms.module.user.service.UserService;
import com.mioto.pms.module.weixin.MiniProgramUserType;
import com.mioto.pms.module.weixin.dao.MiniProgramUserDao;
import com.mioto.pms.module.weixin.model.*;
import com.mioto.pms.module.weixin.service.IMiniProgramUserService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author qinxj
 * @date 2021-07-16 10:23:21
 */
@Service("miniProgramUserService")
public class MiniProgramUserServiceImpl implements IMiniProgramUserService{

    @Resource
    private MiniProgramUserDao miniProgramUserDao;
    @Resource
    private UserService userService;
    @Resource
    private ITenantInfoService tenantInfoService;
    @Resource
    private IRentalInfoService rentalInfoService;
    @Resource
    private PriceService priceService;
    @Resource
    private IRentalInitService rentalInitService;

    @Override
    public List<MiniProgramUser> findList(MiniProgramUser miniProgramUser) {
        return miniProgramUserDao.findList(miniProgramUser);
    }

    @Override
    public int insert(MiniProgramUser miniProgramUser) {
        return miniProgramUserDao.insert(miniProgramUser);
    }

    @Override
    public int insertIgnoreNull(MiniProgramUser miniProgramUser) {
        return miniProgramUserDao.insertIgnoreNull(miniProgramUser);
    }

    @Override
    public int update(MiniProgramUser miniProgramUser) {
        return miniProgramUserDao.update(miniProgramUser);
    }

    @Override
    public int updateIgnoreNull(MiniProgramUser miniProgramUser) {
        return miniProgramUserDao.updateIgnoreNull(miniProgramUser);
    }

    @Override
    public MiniProgramUser findByColumn(String column, Object value) {
        return miniProgramUserDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return miniProgramUserDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return miniProgramUserDao.batchDelete(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MiniProgramUser bindPhone(String phone, String openId, String nickName) {
        //根据手机号码查找是租户还是房东
        UserVO userVO = userService.findByColumn("phone",phone);
        int userId ;
        int userType ;
        if (ObjectUtil.isNotEmpty(userVO) && StrUtil.contains(userVO.getRoleName(),"房东")){
            userType = MiniProgramUserType.LANDLORD;
            userId = userVO.getId();
        }else {
            TenantInfo tenantInfo = tenantInfoService.findByColumn("phone",phone);
            if (ObjectUtil.isEmpty(tenantInfo)){
                //如果没有找到对应的号码，默认为租户，新建一条租户信息
                tenantInfo = new TenantInfo();
                tenantInfo.setPhone(phone);
                tenantInfoService.insertIgnoreNull(tenantInfo);
            }
            userId = tenantInfo.getId();
            userType = MiniProgramUserType.TENANT;
        }
        MiniProgramUser miniProgramUser = new MiniProgramUser();
        miniProgramUser.setUserId(userId);
        miniProgramUser.setUserType(userType);
        miniProgramUser.setOpenId(openId);
        miniProgramUser.setWxNickname(nickName);
        miniProgramUserDao.insertIgnoreNull(miniProgramUser);
        return miniProgramUser;
    }

    @Override
    public List<TenantHomeVO> findHomeData() {
        return miniProgramUserDao.findHomeData(BaseUtil.getLoginUser().getId());
    }

    @Override
    public List<UnpaidFeesVO> findUnpaidFees(String rentalId) {
        return miniProgramUserDao.findUnpaidFees(rentalId);
    }

    @Override
    public List<ContractInfoVO> findContractInfo(String rentalId) {
        return miniProgramUserDao.findContractInfo(rentalId);
    }

    @Override
    public List<TenantBillVO> findHistoryBills(String rentalId) {
        return miniProgramUserDao.findHistoryBills(rentalId);
    }

    @Override
    public List<TenantListVO> findTenantList() {
        return miniProgramUserDao.findTenantList(BaseUtil.getLogonUserId());
    }

    @Override
    public List<LastMeterReadVO> findLastData(String rentalId) {
        List<LastMeterReadVO> list =  miniProgramUserDao.findLastData(rentalId);
        RentalInfo rentalInfo = rentalInfoService.findByColumn("id",rentalId);
        List<Price> priceList = priceService.findByRoomId(rentalInfo.getRoomId());
        if (CollUtil.isEmpty(priceList)){
            return new ArrayList<>(0);
        }
        List<LastMeterReadVO> newList = new ArrayList<>(priceList.size());
        //如果是空说明是第一次抄表，取租住办理时的初始数据
        if (CollUtil.isEmpty(list)){
            LastMeterReadVO lastMeterReadVO ;
            for (Price price : priceList) {
                if (PricingStrategyEnum.getInstance(price.getType()).getType() == 1) {
                    lastMeterReadVO = new LastMeterReadVO();
                    Double initVal = rentalInitService.findInitVal(price.getType(), rentalId);
                    lastMeterReadVO.setLastData(ObjectUtil.isEmpty(initVal) ? 0d : initVal);
                    lastMeterReadVO.setCostType(price.getType());
                    lastMeterReadVO.setName(price.getName());
                    lastMeterReadVO.setLastTime(rentalInfo.getCreateDate());
                    newList.add(lastMeterReadVO);
                }
            }
        }else {
            //如果已经有过抄表纪录，还要判断在抄表过后，房屋有没有新增过收费策略
            Map<String,LastMeterReadVO> map = list.stream().collect(Collectors.toMap(LastMeterReadVO::getCostType,LastMeterReadVO::get));
            for (Price price : priceList) {
                if (PricingStrategyEnum.getInstance(price.getType()).getType() == 1) {
                    if (map.containsKey(price.getType())){
                        newList.add(map.get(price.getType()));
                    }else {
                        LastMeterReadVO lastMeterReadVO = new LastMeterReadVO();
                        lastMeterReadVO.setCostType(price.getType());
                        lastMeterReadVO.setName(price.getName());
                        Double initVal = rentalInitService.findInitVal(price.getType(), rentalId);
                        lastMeterReadVO.setLastData(ObjectUtil.isEmpty(initVal) ? 0d : initVal);
                        lastMeterReadVO.setLastTime(rentalInfo.getCreateDate());
                        newList.add(lastMeterReadVO);
                    }
                }
            }
        }
        return newList;
    }

    @Override
    public PersonalCenterVO getPersonalCenterData(String date) {
        return miniProgramUserDao.findPersonalCenterData(date,11);
    }
}