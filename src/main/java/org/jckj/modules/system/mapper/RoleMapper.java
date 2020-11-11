package org.jckj.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jckj.modules.system.entity.Role;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface RoleMapper extends BaseMapper<Role> {



	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleNames(Long[] ids);

	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleAliases(Long[] ids);

}
