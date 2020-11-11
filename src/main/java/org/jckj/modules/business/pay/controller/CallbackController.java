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
package org.jckj.modules.business.pay.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.jckj.core.mp.support.Condition;
import org.jckj.core.mp.support.Query;
import org.jckj.core.tool.api.R;
import org.jckj.core.tool.utils.Func;
import org.jckj.modules.business.pay.payentity.AliPay;
import org.jckj.modules.business.pay.utils.PayUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jckj.modules.business.pay.entity.Callback;
import org.jckj.modules.business.pay.vo.CallbackVO;
import org.jckj.modules.business.pay.service.ICallbackService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 支付回调表 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("pay/callback")
@Api(value = "支付回调表", tags = "支付回调表接口")
public class CallbackController extends JcKjController {

	private final ICallbackService callbackService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入callback")
	public R<Callback> detail(Callback callback) {
		Callback detail = callbackService.getOne(Condition.getQueryWrapper(callback));
		return R.data(detail);
	}

	/**
	 * 分页 支付回调表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入callback")
	public R<IPage<Callback>> list(Callback callback, Query query) {
		IPage<Callback> pages = callbackService.page(Condition.getPage(query), Condition.getQueryWrapper(callback));
		return R.data(pages);
	}

	/**
	 * 自定义分页 支付回调表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入callback")
	public R<IPage<CallbackVO>> page(CallbackVO callback, Query query) {
		IPage<CallbackVO> pages = callbackService.selectCallbackPage(Condition.getPage(query), callback);
		return R.data(pages);
	}
	@GetMapping("/getReturnUrlInfo")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "alipay同步通知调用地址", notes = "传入空")
	public String alipayReturnUrlInfo(HttpServletRequest request) {
		PayUtils payUtils=new PayUtils();
		String synchronous = payUtils.synchronous(request);
		return synchronous;
	}
	@PostMapping("/getNotifyUrlInfo")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "alipay异步通知调用地址", notes = "传入空")
	public void aliPayNotifyUrlInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PayUtils payUtils=new PayUtils();
		AliPay notify = payUtils.notify(request, response);
		System.err.println(notify);
	}
//	/**
//	 * 新增 支付回调表
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入callback")
//	public R save(@Valid @RequestBody Callback callback) {
//		return R.status(callbackService.save(callback));
//	}
//
//	/**
//	 * 修改 支付回调表
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入callback")
//	public R update(@Valid @RequestBody Callback callback) {
//		return R.status(callbackService.updateById(callback));
//	}
//
//	/**
//	 * 新增或修改 支付回调表
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入callback")
//	public R submit(@Valid @RequestBody Callback callback) {
//		return R.status(callbackService.saveOrUpdate(callback));
//	}
//
//
//	/**
//	 * 删除 支付回调表
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(callbackService.deleteLogic(Func.toLongList(ids)));
//	}
	/**
	 * 删除 支付回调表
	 */
	@GetMapping("/cs")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R cs() {
		return R.data("测试");
	}

	/**
	 * 删除 支付回调表
	 */
	@GetMapping("/cs1")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R cs1() {
		return R.data("测试1");
	}

}
