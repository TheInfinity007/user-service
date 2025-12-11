package com.theinfinity007.user_service.config.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonStringTrimModule {

    @Bean
    public Module trimStringModule() {
        SimpleModule module = new SimpleModule();

        module.setDeserializerModifier(new TrimStringDeserializerModifier());
        return module;
    }
}
