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
package org.jckj.modules.business.translation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jckj.core.mp.base.BaseService;
import org.jckj.modules.business.translation.dto.RecordsDTO;
import org.jckj.modules.business.translation.entity.Records;
import org.jckj.modules.business.translation.vo.RecordsVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 翻译记录 服务类
 *
 * @author BladeX
 * @since 2020-10-13
 */
public interface IRecordsService extends BaseService<Records> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param records
	 * @return
	 */
	IPage<RecordsVO> selectRecordsPage(IPage<RecordsVO> page, RecordsVO records);

	/**
	 * 记录翻译数据
	 */
	boolean record(RecordsDTO recordsDTO);
	/**
	 * 使用明细下拉框查询
	 */
	List<Records> getQuery(String date,String account,String type);
	/**
	 * 翻译数据
	 */
	String recordData(String type,String key ,String from,String to,String data,MultipartFile file) throws Exception;
	/**
	 * 文件翻译
	 */
	String file(MultipartFile file, String key);
}
