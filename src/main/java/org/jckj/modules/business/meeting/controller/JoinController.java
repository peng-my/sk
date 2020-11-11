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
package org.jckj.modules.business.meeting.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.jckj.core.boot.ctrl.JcKjController;
import org.jckj.core.mp.support.Condition;
import org.jckj.core.mp.support.Query;
import org.jckj.core.tool.api.R;
import org.jckj.core.tool.utils.Func;
import org.jckj.modules.business.meeting.entity.Join;
import org.jckj.modules.business.meeting.service.IJoinService;
import org.jckj.modules.business.meeting.vo.JoinVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 加入会议 控制器
 *
 * @author BladeX
 * @since 2020-10-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("meetingjoin/join")
@Api(value = "加入会议", tags = "加入会议接口")
public class JoinController extends JcKjController {

	private final IJoinService joinService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入join")
	public R<Join> detail(Join join) {
		Join detail = joinService.getOne(Condition.getQueryWrapper(join));
		return R.data(detail);
	}

	/**
	 * 分页 加入会议
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入join")
	public R<IPage<Join>> list(Join join, Query query) {
		IPage<Join> pages = joinService.page(Condition.getPage(query), Condition.getQueryWrapper(join));
		return R.data(pages);
	}

	/**
	 * 自定义分页 加入会议
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入join")
	public R<IPage<JoinVO>> page(JoinVO join, Query query) {
		IPage<JoinVO> pages = joinService.selectJoinPage(Condition.getPage(query), join);
		return R.data(pages);
	}

	/**
	 * 新增 加入会议
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入join")
	public R save(@Valid @RequestBody Join join) {
		return R.status(joinService.save(join));
	}

	/**
	 * 修改 加入会议
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入join")
	public R update(@Valid @RequestBody Join join) {
		return R.status(joinService.updateById(join));
	}

	/**
	 * 新增或修改 加入会议
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入join")
	public R submit(@Valid @RequestBody Join join) {
		return R.status(joinService.saveOrUpdate(join));
	}


	/**
	 * 删除 加入会议
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(joinService.deleteLogic(Func.toLongList(ids)));
	}

//	/**
//	 * 详情
//	 */
//	@GetMapping("home")
//	public ResponseEntity<?> home() {
//		HttpHeaders header = new HttpHeaders();
//		header.add("Content-Type", "text/event-stream");
//		String string = new Date().toString();
//		System.err.println(string);
//		return ResponseEntity.ok().headers(header).body("data: " + string+ "\n\n");
//	}

}
