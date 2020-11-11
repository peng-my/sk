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
package org.jckj.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jckj.modules.system.entity.DictBizExt;
import org.jckj.modules.system.vo.DictBizExtVO;

import java.util.List;
import java.util.Map;

/**
 * Mapper 接口
 *
 * @author Nick
 */
public interface DictBizExtMapper extends BaseMapper<DictBizExt> {

	List<DictBizExtVO> dictionaryList(String code);

	List<DictBizExtVO> lazyList(Long parentId, Map<String, Object> param);

	List<DictBizExtVO> dictionaryListByKey(String code, String key);
}
