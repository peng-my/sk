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
package org.jckj.modules.business.user.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.jckj.core.mp.support.Condition;
import org.jckj.core.mp.support.Query;
import org.jckj.core.tool.api.R;
import org.jckj.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jckj.modules.business.user.entity.GiftRoll;
import org.jckj.modules.business.user.vo.GiftRollVO;
import org.jckj.modules.business.user.service.IGiftRollService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 会员礼金卷及国籍 控制器
 *
 * @author BladeX
 * @since 2020-10-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("usergiftroll/giftroll")
@Api(value = "会员礼金卷及国籍", tags = "会员礼金卷及国籍接口")
public class GiftRollController extends JcKjController {

	private final IGiftRollService giftRollService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入giftRoll")
	public R<GiftRoll> detail(GiftRoll giftRoll) {
		GiftRoll detail = giftRollService.getOne(Condition.getQueryWrapper(giftRoll));
		return R.data(detail);
	}

	/**
	 * 分页 会员礼金卷及国籍
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入giftRoll")
	public R<IPage<GiftRoll>> list(GiftRoll giftRoll, Query query) {
		IPage<GiftRoll> pages = giftRollService.page(Condition.getPage(query), Condition.getQueryWrapper(giftRoll));
		return R.data(pages);
	}

	/**
	 * 自定义分页 会员礼金卷及国籍
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入giftRoll")
	public R<IPage<GiftRollVO>> page(GiftRollVO giftRoll, Query query) {
		IPage<GiftRollVO> pages = giftRollService.selectGiftRollPage(Condition.getPage(query), giftRoll);
		return R.data(pages);
	}

	/**
	 * 新增 会员礼金卷及国籍
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入giftRoll")
	public R save(@Valid @RequestBody GiftRoll giftRoll) {
		return R.status(giftRollService.save(giftRoll));
	}

	/**
	 * 修改 会员礼金卷及国籍
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入giftRoll")
	public R update(@Valid @RequestBody GiftRoll giftRoll) {
		return R.status(giftRollService.updateById(giftRoll));
	}

	/**
	 * 新增或修改 会员礼金卷及国籍
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入giftRoll")
	public R submit(@Valid @RequestBody GiftRoll giftRoll) {
		return R.status(giftRollService.saveOrUpdate(giftRoll));
	}

	
	/**
	 * 删除 会员礼金卷及国籍
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(giftRollService.deleteLogic(Func.toLongList(ids)));
	}

	
}
