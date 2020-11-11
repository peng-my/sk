package org.jckj.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JcKjBootAutoConfig {

	/**
	 * 解决前端js处理大数字丢失精度问题，将Long和BigInteger转换成string
	 *
	 * @return
	 */
	/*@Bean("jackson2ObjectMapperBuilderCustomizer")
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		Jackson2ObjectMapperBuilderCustomizer customizer = new Jackson2ObjectMapperBuilderCustomizer() {
			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
				jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance)
					.serializerByType(Long.TYPE, ToStringSerializer.instance);
			}
		};
		return customizer;
	}*/

	@Bean
	@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper getObjectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper om = builder.build();
		return om;
	}

}
