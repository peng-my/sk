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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jckj.common.launch.tool.node.JcKjTreeNode;
import org.jckj.core.secure.JcKjUser;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.node.ForestNodeMerger;
import org.jckj.modules.system.entity.DictBizExt;
import org.jckj.modules.system.mapper.DictBizExtMapper;
import org.jckj.modules.system.service.IDictBizExtService;
import org.jckj.modules.system.vo.DictBizExtVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class DictBizExtServiceImpl extends ServiceImpl<DictBizExtMapper, DictBizExt> implements IDictBizExtService {

	@Override
	public List getDictionaryList(String code, Integer deepLevel) {
		List result=null;
		JcKjUser user = AuthUtil.getUser();
		if (code.equals("registered_nationality")&&user==null){
			 result = ForestNodeMerger.merge(baseMapper.dictionaryList(code));
			if (deepLevel > 0) {//获取指定深度的递归字典，1表示只获取第一级，2表示只获取前两级,以此类推
				return this.newTree(deepLevel, result);
			}
		}else if (!code.equals("registered_nationality")&&user!=null){
			result = ForestNodeMerger.merge(baseMapper.dictionaryList(code));
			if (deepLevel > 0) {//获取指定深度的递归字典，1表示只获取第一级，2表示只获取前两级,以此类推
				return this.newTree(deepLevel, result);
			}
		}else {

		}
		return result;
	}

	@Override
	public List getDictByKey(String code, String key, Integer deepLevel) {
		List result = ForestNodeMerger.merge(baseMapper.dictionaryListByKey(code, key));
		if (deepLevel > 0) {//获取指定深度的递归字典，1表示只获取第一级，2表示只获取前两级,以此类推
			return this.newTree(deepLevel, result);
		}
		return result;
	}

	public List newTree(Integer deepLevel, List dataList) {
		List newTree = new ArrayList<>();
		int nodeListSize = dataList.size();
		if (nodeListSize > 0) {
			for (int i = 0; i < nodeListSize; i++) {
				int deep = 0;
				JcKjTreeNode treeNode = (JcKjTreeNode) dataList.get(i);
				newTree.add(this.cutLevel(treeNode, deepLevel, deep));
			}
		}
		return newTree;
	}

	@Override
	public List<DictBizExtVO> lazyList(JcKjUser jcKjUser, Long parentId, Map<String, Object> param) {
		return baseMapper.lazyList(parentId, param);
	}

	private JcKjTreeNode cutLevel(JcKjTreeNode khTreeNode, int deepLevel, int deep) {
		deep++;
		if (deep < deepLevel) {
			List nodeList = khTreeNode.getChildren();
			int nodeListSize = nodeList.size();
			if (nodeListSize > 0) {
				for (int i = 0; i < nodeListSize; i++) {
					JcKjTreeNode treeNode = (JcKjTreeNode) nodeList.get(i);
					this.cutLevel(treeNode, deepLevel, deep);
				}
			}
		}
		if (deep == deepLevel) {
			khTreeNode.setChildren(new ArrayList<>());
		}
		return khTreeNode;
	}


}
