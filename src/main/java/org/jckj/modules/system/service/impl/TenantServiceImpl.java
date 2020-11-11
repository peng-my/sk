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
package org.jckj.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.jckj.modules.system.entity.Tenant;
import org.jckj.modules.system.mapper.TenantMapper;
import org.jckj.modules.system.service.ITenantService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements ITenantService {


    /**
     * 根据租户编号获取实体
     *
     * @param tenantId
     * @return
     */
    @Override
    public Tenant getByTenantId(String tenantId) {
        return getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantId, tenantId));
    }
}
