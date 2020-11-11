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
import com.alipay.api.AlipayApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;

import org.jckj.core.mp.support.Condition;
import org.jckj.core.mp.support.Query;
import org.jckj.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jckj.modules.business.pay.entity.Pay;
import org.jckj.modules.business.pay.vo.PayVO;
import org.jckj.modules.business.pay.service.IPayService;
import org.jckj.core.boot.ctrl.JcKjController;

import java.io.IOException;

/**
 * 支付表 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("pay/pay")
@Api(value = "支付表", tags = "支付表接口")
public class PayController extends JcKjController {

	private final IPayService payService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入pay")
	public R<Pay> detail(Pay pay) {
		Pay detail = payService.getOne(Condition.getQueryWrapper(pay));
		return R.data(detail);
	}

	/**
	 * 分页 支付表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入pay")
	public R<IPage<Pay>> list(Pay pay, Query query) {
		IPage<Pay> pages = payService.page(Condition.getPage(query), Condition.getQueryWrapper(pay));
		return R.data(pages);
	}

	/**
	 * 自定义分页 支付表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入pay")
	public R<IPage<PayVO>> page(PayVO pay, Query query) {
		IPage<PayVO> pages = payService.selectPayPage(Condition.getPage(query), pay);
		return R.data(pages);
	}

	/**
	 * 调用 支付
	 */
	@PostMapping("/launch")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "发起支付", notes = "传入订单编号:orderCode,支付类型:payType")
	public void launch(@ApiParam(value = "订单编号", required = true) @RequestParam String orderCode,
					   @ApiParam(value = "支付类型", required = true) @RequestParam String payType,
					   HttpServletResponse response) throws IOException, AlipayApiException {
		payService.launchPay(orderCode,payType,response);

	}

//	/**
//	 * 新增 支付表
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入pay")
//	public R save(@Valid @RequestBody Pay pay) {
//		return R.status(payService.save(pay));
//	}
//
//	/**
//	 * 修改 支付表
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入pay")
//	public R update(@Valid @RequestBody Pay pay) {
//		return R.status(payService.updateById(pay));
//	}
//
//	/**
//	 * 新增或修改 支付表
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入pay")
//	public R submit(@Valid @RequestBody Pay pay) {
//		return R.status(payService.saveOrUpdate(pay));
//	}
//
//
//	/**
//	 * 删除 支付表
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(payService.deleteLogic(Func.toLongList(ids)));
//	}


}
