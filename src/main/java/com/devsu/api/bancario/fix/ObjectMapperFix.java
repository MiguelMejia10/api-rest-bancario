package com.devsu.api.bancario.fix;

import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperFix {
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> builder.postConfigurer(objectMapper -> {
			objectMapper.coercionConfigFor(LogicalType.Enum)
					.setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
		});
	}
}