package com.theinfinity007.user_service.dao;

import com.theinfinity007.user_service.entity.Template;
import com.theinfinity007.user_service.enums.OtpAction;

public interface TemplateDAO {
    Template get(String type, String action, String lang);
}
