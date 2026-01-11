package com.theinfinity007.user_service.service.impl;

import com.theinfinity007.user_service.dao.TemplateDAO;
import com.theinfinity007.user_service.entity.Template;

import com.theinfinity007.user_service.exception.TemplateNotFoundException;
import com.theinfinity007.user_service.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateDAO templateDAO;

    @Override
    public Template get(String type, String code, String lang) {
        Template template = templateDAO.get(type, code, lang);

        if (template == null) {
            throw new TemplateNotFoundException();
        }

        return template;
    }
}


