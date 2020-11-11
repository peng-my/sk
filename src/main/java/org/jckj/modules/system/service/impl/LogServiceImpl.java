package org.jckj.modules.system.service.impl;

import lombok.AllArgsConstructor;
import org.jckj.core.log.model.LogApi;
import org.jckj.modules.system.service.ILogApiService;
import org.jckj.modules.system.service.ILogService;
import org.springframework.stereotype.Service;

/**
 * Created by Blade.
 *
 * @author zhuangqian
 */
@Service
@AllArgsConstructor
public class LogServiceImpl implements ILogService {


	private final ILogApiService apiService;




	@Override
	public Boolean saveApiLog(LogApi log) {
		return apiService.save(log);
	}



}
