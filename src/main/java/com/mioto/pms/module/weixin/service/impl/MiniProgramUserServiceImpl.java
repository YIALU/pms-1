package com.mioto.pms.module.weixin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.rental.model.TenantInfo;
import com.mioto.pms.module.rental.service.ITenantInfoService;
import com.mioto.pms.module.user.model.UserVO;
import com.mioto.pms.module.user.service.UserService;
import com.mioto.pms.module.weixin.MiniProgramUserType;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.weixin.dao.MiniProgramUserDao;
import com.mioto.pms.module.weixin.model.MiniProgramUser;
import com.mioto.pms.module.weixin.service.IMiniProgramUserService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
}