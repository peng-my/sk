
package org.jckj.common.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jckj.core.launch.props.JcKjProperties;
import org.jckj.core.launch.server.ServerInfo;
import org.jckj.core.log.constant.EventConstant;
import org.jckj.core.log.event.ApiLogEvent;
import org.jckj.core.log.model.LogApi;
import org.jckj.core.log.utils.LogAbstractUtil;
import org.jckj.modules.system.service.ILogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final JcKjProperties jcKjProperties;


	@Async
	@Order
	@EventListener(ApiLogEvent.class)
	public void saveApiLog(ApiLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogApi logApi = (LogApi) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logApi, jcKjProperties, serverInfo);
		logService.saveApiLog(logApi);
	}

}
