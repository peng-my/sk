/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.jckj.modules.business.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencentyun.TLSSigAPIv2;
import lombok.AllArgsConstructor;
import org.jckj.common.cache.ParamCache;
import org.jckj.common.utils.BaseEntityUtils;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.jckj.core.redis.cache.JcKjRedis;
import org.jckj.core.secure.JcKjUser;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.utils.BeanUtil;
import org.jckj.core.tool.utils.DigestUtil;
import org.jckj.core.tool.utils.StringUtil;
import org.jckj.modules.business.meeting.entity.Meeting;
import org.jckj.modules.business.meeting.mapper.MeetingMapper;
import org.jckj.modules.business.meeting.service.IMeetingCreateService;
import org.jckj.modules.business.order.entity.Management;
import org.jckj.modules.business.order.mapper.ManagementMapper;
import org.jckj.modules.business.translation.entity.Records;
import org.jckj.modules.business.translation.mapper.RecordsMapper;
import org.jckj.modules.business.user.dto.UserExtDTO;
import org.jckj.modules.business.user.entity.GiftRoll;
import org.jckj.modules.business.user.entity.RegistrationGift;
import org.jckj.modules.business.user.entity.Subuser;
import org.jckj.modules.business.user.entity.UserExt;
import org.jckj.modules.business.user.mapper.*;
import org.jckj.modules.business.user.registerenum.RegisterEnum;
import org.jckj.modules.business.user.service.IUserExtService;
import org.jckj.modules.business.user.vo.UserAdminExtVO;
import org.jckj.modules.business.user.vo.UserExtVO;
import org.jckj.modules.business.user.vo.UserManageExtVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户扩展 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
@AllArgsConstructor
public class UserExtServiceImpl extends BaseServiceImpl<UserExtMapper, UserExt> implements IUserExtService {
    private final SubuserMapper subuserMapper;
    private final RecordsMapper recordsMapper;
    private final ManagementMapper managementMapper;
    private final BankCardMapper bankCardMapper;
    private final JcKjRedis jcKjRedis;
    private final RegistrationGiftMapper registrationGiftMapper;
    private final GiftRollMapper giftRollMapper;
    private final IMeetingCreateService meetingCreateService;
    private final MeetingMapper meetingMapper;
    private static final String getUserRoId="get.userRoleId";
    private static final String getAdminRoId="get.adminRoleId";

    @Override
    public IPage<UserExtVO> selectUserPage(IPage<UserExtVO> page, UserExtVO userExtVO) {
        return page.setRecords(baseMapper.selectBankCardPage(page, userExtVO));
    }

    /**
     * 用户中心
     *
     * @param type 1为主 2为子
     * @return
     */
    @Override
    public UserExtVO getInfo(String type) {
        JcKjUser user = AuthUtil.getUser();
        Long userId = user.getUserId();
        Integer types = Integer.valueOf(type);
        UserExtVO userExtVO = new UserExtVO();
        UserExt userExt = new UserExtVO();
        List<Records> records = new ArrayList<>();
        switch (types) {
            case 1:
                userExt = baseMapper.selectById(userId);
                BeanUtil.copyProperties(userExt, userExtVO);
                records = recordsMapper.selectList(new QueryWrapper<Records>().eq("create_user", userId).orderByDesc("update_time"));
                //拼接下拉框
                if (records.size() > 0) {
                    List<String> recordsDateList = new ArrayList<>();
                    List<String> accountList = new ArrayList<>();
                    for (Records records1 : records) {
                        records1.setMongodbKey(null);
                        records1.setMongodbKey(null);
                        records1.setConferenceRoomId(null);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                        Date createTime = records1.getCreateTime();
                        String format = sdf.format(createTime);
                        if (!recordsDateList.contains(format)) {
                            recordsDateList.add(format);
                        }
                        String account = records1.getAccount();
                        //查询list里面有没有这个元素
                        if (accountList.contains(account)) {
                            accountList.add(account);
                        }
                    }
                    userExtVO.setRecordsDateList(recordsDateList);
                    userExtVO.setAccountList(accountList);
                    userExtVO.setRecordsList(records);
                }
                List<Management> managements = managementMapper.selectList(new QueryWrapper<Management>().eq("create_user", userId).orderByDesc("update_time"));
                if (managements.size() > 0) {
                    List<String> managementDateList = new ArrayList<>();
                    for (Management management : managements) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                        Date createTime = management.getCreateTime();
                        String format = sdf.format(createTime);
                        if (!managementDateList.contains(format)) {
                            managementDateList.add(format);
                        }
                    }
                    userExtVO.setManagementDateList(managementDateList);
                    userExtVO.setManagementList(managements);
                }
                break;
            case 2:
                Subuser subuser = subuserMapper.selectOne(new QueryWrapper<Subuser>().eq("fk_son_user_id", userId));
                if (subuser != null) {
                    userExt = baseMapper.selectById(userId);
                    BeanUtil.copyProperties(userExt, userExtVO);
                    records = recordsMapper.selectList(new QueryWrapper<Records>().eq("create_user", userId).orderByDesc("update_time"));
                    if (records.size() > 0) {
                        List<String> recordsDateList = new ArrayList<>();
                        for (Records records1 : records) {
                            records1.setMongodbKey(null);
                            records1.setMongodbKey(null);
                            records1.setConferenceRoomId(null);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                            Date createTime = records1.getCreateTime();
                            String format = sdf.format(createTime);
                            if (recordsDateList.contains(format)) {
                                recordsDateList.add(format);
                            }
                        }
                        userExtVO.setRecordsDateList(recordsDateList);
                        userExtVO.setRecordsList(records);
                    }
                }
                break;
            default:
                break;
        }
        return userExtVO;
    }

    /**
     * 修改昵称
     *
     * @param name
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updataName(String name) {
        JcKjUser user = AuthUtil.getUser();
        UserExt userExt = baseMapper.selectById(user.getUserId());
        if (userExt!=null){
            userExt.setName(name);
            BaseEntityUtils.resolveEntity(userExt);
            baseMapper.updateById(userExt);
        }else {
            throw new ServiceException("账号有误,无法修改昵称!");
        }
        return true;
    }

    /**
     * 添加子账号
     *
     * @param userExt
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitOrUpdata(UserExt userExt) {
        JcKjUser user = AuthUtil.getUser();
        if (user!=null){
            String password = userExt.getPassword();
            String encrypt = DigestUtil.encrypt(password);
            userExt.setPassword(encrypt);
            userExt.setRoleId(ParamCache.getValue(getUserRoId));
            userExt.setFkUserId(user.getUserId());
            BaseEntityUtils.resolveEntity(userExt);
            baseMapper.insert(userExt);
            Subuser subuser=new Subuser();
            subuser.setFkMainUserId(user.getUserId());
            subuser.setFkSonUserId(userExt.getId());
            subuserMapper.insert(subuser);
        }else {
            throw new ServiceException("无法识别主账号");
        }

        return true;
    }

    /**
     * 子账号分页
     *
     * @param page
     * @param userExt
     */
    @Override
    public IPage<UserExt> getIpage(IPage<UserExt> page, UserExt userExt) {
        JcKjUser user = AuthUtil.getUser();
        Page<UserExt> userExtPage = new Page<>();
        if (user != null) {
            userExtPage = baseMapper.selectPage(new Page<>(page.getCurrent(), page.getSize()), new QueryWrapper<UserExt>().eq(StringUtil.isNoneBlank(userExt.getAccount()), "account", userExt.getAccount())
                    .eq("create_user", user.getUserId()));
        }
        return userExtPage;
    }

    /**
     * 修改密码
     *
     * @param password
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updataPassword(String password,String newPassword) {
        JcKjUser user = AuthUtil.getUser();
        if (user!=null){
            String encrypt = DigestUtil.encrypt(password);
            UserExt userExt = baseMapper.selectOne(new QueryWrapper<UserExt>().eq("id",user.getUserId()).eq("password",encrypt));
            if (userExt != null) {
                userExt.setPassword(DigestUtil.encrypt(password));
                BaseEntityUtils.resolveEntity(userExt);
                baseMapper.updateById(userExt);
            } else {
                throw new ServiceException("查无次账号!");
            }
        }else {
            throw new ServiceException("查无次账号!");
        }

        return true;
    }

    /**
     * 用户注册
     *
     * @param userExt
     * @param type    1 手机 2 验证码
     * @param code    验证码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(UserExtDTO userExtDTO) {
        //todo 注册 registrationGiftMapper
        Integer types = Integer.valueOf(userExtDTO.getType());
        //国籍
        String nationality = userExtDTO.getNationality();
        String valueByCode = RegisterEnum.getValueByCode(nationality);
        UserExt userExt=new UserExt();
        BeanUtil.copyProperties(userExtDTO,userExt);
        switch (types) {
            case 1:
                String phone = userExt.getPhone();
                Integer phoneCount = baseMapper.selectCount(new QueryWrapper<UserExt>().eq("phone", phone));
                if (phoneCount < 1) {
                    Object o = jcKjRedis.get(phone);
                    if (o != null) {
                        if (o.toString().equals(userExtDTO.getCode())) {
                            if (StringUtil.isNoneBlank(valueByCode)) {
                                Integer integer = Integer.valueOf(valueByCode);
                                BaseEntityUtils.resolveEntity(userExt);
                                userExt.setAccount(phone);
                                String password = userExt.getPassword();
                                String encrypt = DigestUtil.encrypt(password);
                                userExt.setPassword(encrypt);
                                userExt.setRoleId(ParamCache.getValue(getUserRoId));
                                baseMapper.insert(userExt);
                                RegistrationGift registrationGift = registrationGiftMapper.selectOne(new QueryWrapper<RegistrationGift>());
                                if (registrationGift != null) {
                                    GiftRoll giftRoll = new GiftRoll();
                                    switch (integer) {
                                        case 1:
                                            Double jpy = registrationGift.getJpy();
                                            giftRoll.setFkUserId(userExt.getId());
                                            giftRoll.setRegister(jpy);
                                            giftRoll.setNationality(nationality);
                                            BaseEntityUtils.resolveEntity(giftRoll);
                                            giftRollMapper.insert(giftRoll);
                                            break;
                                        case 2:
                                            Double cny = registrationGift.getCny();
                                            giftRoll.setFkUserId(userExt.getId());
                                            giftRoll.setRegister(cny);
                                            giftRoll.setNationality(nationality);
                                            BaseEntityUtils.resolveEntity(giftRoll);
                                            giftRollMapper.insert(giftRoll);
                                            break;
                                        case 3:
                                            Double usd = registrationGift.getUsd();
                                            giftRoll.setFkUserId(userExt.getId());
                                            giftRoll.setRegister(usd);
                                            giftRoll.setNationality(nationality);
                                            BaseEntityUtils.resolveEntity(giftRoll);
                                            giftRollMapper.insert(giftRoll);
                                            break;
                                        default:
                                            break;
                                    }
                                }else {
                                    throw new ServiceException("请填写注册需要送的礼卷");
                                }
                            }else {
                                throw new ServiceException("请选择国籍!");
                            }

                        } else {
                            throw new ServiceException("验证码有误!");
                        }
                    } else {
                        throw new ServiceException("无法获取验证码!");
                    }
                    Integer integer = meetingCreateService.addSumit();
                    if (integer!=0){
                        String format = String.format("%06d", integer);
                        Meeting meeting = new Meeting();
                        Long userExtId = userExt.getId();
                        meeting.setConferenceRoomId("sk"+format);
                        BaseEntityUtils.resolveEntity(meeting);
                        meeting.setCreateUser(userExtId);
                        meetingMapper.insert(meeting);
                    }
                } else {
                    throw new ServiceException(StringUtil.format("该手机号{}已注册!", phone));
                }
                break;
            case 2:
                String email = userExt.getEmail();
                Integer email1 = baseMapper.selectCount(new QueryWrapper<UserExt>().eq("email", email));
                if (email1 < 1) {
                    Object o1 = jcKjRedis.get(email);
                    if (o1 != null) {
                        if (o1.toString().equals(userExtDTO.getCode())) {
                            if (StringUtil.isNoneBlank(valueByCode)){
                                BaseEntityUtils.resolveEntity(userExt);
                                userExt.setAccount(email);
                                String password = userExt.getPassword();
                                String encrypt = DigestUtil.encrypt(password);
                                userExt.setPassword(encrypt);
                                userExt.setRoleId(ParamCache.getValue(getUserRoId));
                                baseMapper.insert(userExt);
                                RegistrationGift registrationGift = registrationGiftMapper.selectOne(new QueryWrapper<RegistrationGift>());
                                if (registrationGift!=null){
                                    GiftRoll giftRoll = new GiftRoll();
                                    Integer integer = Integer.valueOf(valueByCode);
                                    switch (integer){
                                        case 1:
                                            Double jpy = registrationGift.getJpy();
                                            giftRoll.setFkUserId(userExt.getId());
                                            giftRoll.setRegister(jpy);
                                            giftRoll.setNationality(nationality);
                                            BaseEntityUtils.resolveEntity(giftRoll);
                                            giftRollMapper.insert(giftRoll);
                                            break;
                                        case 2:
                                            Double cny = registrationGift.getCny();
                                            giftRoll.setFkUserId(userExt.getId());
                                            giftRoll.setRegister(cny);
                                            giftRoll.setNationality(nationality);
                                            BaseEntityUtils.resolveEntity(giftRoll);
                                            giftRollMapper.insert(giftRoll);
                                            break;
                                        case 3:
                                            Double usd = registrationGift.getUsd();
                                            giftRoll.setFkUserId(userExt.getId());
                                            giftRoll.setRegister(usd);
                                            giftRoll.setNationality(nationality);
                                            BaseEntityUtils.resolveEntity(giftRoll);
                                            giftRollMapper.insert(giftRoll);
                                            break;
                                        default:
                                            break;
                                    }
                                }else {
                                    throw new ServiceException("请填写注册需要送的礼卷");
                                }
                            }else {
                                throw new ServiceException("请选择国籍!");
                            }
                        } else {
                            throw new ServiceException("验证码有误!");
                        }
                    } else {
                        throw new ServiceException("无法获取验证码!");
                    }
                    Integer integer = meetingCreateService.addSumit();
                    if (integer!=0){
                        String format = String.format("%06d", integer);
                        Meeting meeting = new Meeting();
                        Long userExtId = userExt.getId();
                        meeting.setConferenceRoomId("sk"+format);
                        BaseEntityUtils.resolveEntity(meeting);
                        meeting.setCreateUser(userExtId);
                        meetingMapper.insert(meeting);
                    }
                } else {
                    throw new ServiceException(StringUtil.format("该邮箱{}已注册!", email));
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 重置密码
     *
     * @param userExtDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(UserExtDTO userExtDTO) {
        Integer types = Integer.valueOf(userExtDTO.getType());
        UserExt userExt=new UserExt();
        BeanUtil.copyProperties(userExtDTO,userExt);
        switch (types){
            case 1:
                String phone = userExt.getPhone();
                UserExt userExt1 = baseMapper.selectOne(new QueryWrapper<UserExt>().eq("phone", phone));
                if (userExt1!=null) {
                    Object o = jcKjRedis.get(phone);
                    if (o != null) {
                        if (o.toString().equals(userExtDTO.getCode())) {
                            String password = userExt.getPassword();
                            String encrypt = DigestUtil.encrypt(password);
                            userExt1.setPassword(encrypt);
                            BaseEntityUtils.resolveEntity(userExt1);
                            baseMapper.updateById(userExt1);
                        }else {
                            throw new ServiceException("验证码不正确!");
                        }
                    }else {
                        throw new ServiceException("无法获取验证码!");
                    }
                }else {
                    throw new ServiceException(StringUtil.format("该手机号：{},不存在",phone));
                }
                break;
            case 2:
                String email = userExt.getEmail();
                UserExt userExt2 = baseMapper.selectOne(new QueryWrapper<UserExt>().eq("email", email));
                if (userExt2!=null) {
                    Object o = jcKjRedis.get(email);
                    if (o != null) {
                        if (o.toString().equals(userExtDTO.getCode())) {
                            String password = userExt.getPassword();
                            String encrypt = DigestUtil.encrypt(password);
                            userExt.setPassword(encrypt);
                            BaseEntityUtils.resolveEntity(userExt);
                            baseMapper.updateById(userExt);
                        }else {
                            throw new ServiceException("验证码不正确!");
                        }
                    }else {
                        throw new ServiceException("无法获取验证码!");
                    }
                }else {
                    throw new ServiceException(StringUtil.format("该邮箱：{},不存在",email));
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 自定义分页用户管理
     *
     * @param page
     * @param userManageExtVO
     * @return
     */
    @Override
    public IPage<UserManageExtVO> selectUserManageExtVOPage(IPage<UserManageExtVO> page, UserManageExtVO userManageExtVO) {
        userManageExtVO.setRoleId(ParamCache.getValue(getUserRoId));
        return page.setRecords(baseMapper.selectUserManageExtVOPage(page, userManageExtVO));
    }

    /**
     * 封禁
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean hasBan(Long userId) {
        UserExtVO userExtVO = baseMapper.selectUser(userId);
        if (userExtVO!=null){
            Integer isDeleted = userExtVO.getIsDeleted();
            baseMapper.updataIsBan(isDeleted,userId);
        }else {
            throw new ServiceException("无该用户!");
        }
        return true;
    }

    /**
     * 自定义管理员分页
     *
     * @param page
     * @param account
     */
    @Override
    public IPage<UserAdminExtVO> selectAdminIpage(IPage<UserAdminExtVO> page, String account) {
        //todo 参数
        String roleId = ParamCache.getValue(getAdminRoId);
        List<UserAdminExtVO> userAdminExtVOS=new ArrayList<>();
        userAdminExtVOS = baseMapper.selectAdminIpage(page, roleId, account);
        if (userAdminExtVOS.size()>0){
            for (UserAdminExtVO userAdminExtVO:userAdminExtVOS){
                String account1 = userAdminExtVO.getAccount();
                Date date = baseMapper.selectLoginTime(account1);
                if (date!=null){
                    userAdminExtVO.setLastTime(date);
                }
            }
        }
        return page.setRecords(userAdminExtVOS);
    }

    /**
     * 保存头像
     *
     * @param url
     */
    @Override
    public boolean saveHeadPortrait(String url) {
        JcKjUser user = AuthUtil.getUser();
        if (user!=null&&StringUtil.isNoneBlank(url)){
            Long userId = user.getUserId();
            UserExt userExt = baseMapper.selectById(userId);
            if (userExt!=null){
                userExt.setAvatar(url);
                BaseEntityUtils.resolveEntity(userExt);
                baseMapper.updateById(userExt);
            }
        }
        return true;
    }

    /**
     * 获取签
     *
     * @param userId
     */
    @Override
    public String generateUserSig(String userId) {
        TLSSigAPIv2 apIv2=new TLSSigAPIv2(IMConfig.sdkAppId,IMConfig.secretKey);
        return apIv2.genSig(userId,IMConfig.expire);
//        return  "s";
    }

}
