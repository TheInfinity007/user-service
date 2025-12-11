package com.theinfinity007.user_service.config.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

public class TrimStringDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config,
                                                  BeanDescription beanDesc, JsonDeserializer<?> deserializer) {

        if (beanDesc.getBeanClass() == String.class) {
            // Tell jackson to use the below deserializer for strings
            return new GlobalStringTrimDeserializer();
        }

        return deserializer;
    }

}
