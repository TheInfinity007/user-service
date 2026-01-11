package com.theinfinity007.user_service.service;

import com.theinfinity007.user_service.entity.Template;

public interface TemplateService {
    Template get(String type, String code, String lang);
}
