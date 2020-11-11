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
package org.jckj.modules.business.pay.service;

import org.jckj.modules.business.pay.entity.Callback;
import org.jckj.modules.business.pay.payentity.AliPay;
import org.jckj.modules.business.pay.vo.CallbackVO;
import org.jckj.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 支付回调表 服务类
 *
 * @author BladeX
 * @since 2020-10-13
 */
public interface ICallbackService extends BaseService<Callback> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param callback
	 * @return
	 */
	IPage<CallbackVO> selectCallbackPage(IPage<CallbackVO> page, CallbackVO callback);
	/**
	 * 阿里支付异步回调
	 */
	boolean aliPayNotify(AliPay aliPay);
}
