
package org.jckj.common.config;

import lombok.AllArgsConstructor;
import org.jckj.common.event.ApiLogListener;
import org.jckj.core.launch.props.JcKjProperties;
import org.jckj.core.launch.server.ServerInfo;
import org.jckj.modules.system.service.ILogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@AllArgsConstructor
public class JcKjLogConfiguration {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final JcKjProperties jcKjProperties;

	@Bean(name = "apiLogListener")
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, jcKjProperties);
	}



}
