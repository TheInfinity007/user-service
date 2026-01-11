package com.theinfinity007.user_service.service.impl;

import com.theinfinity007.user_service.config.EmailConfig;
import com.theinfinity007.user_service.constant.AppConstant;
import com.theinfinity007.user_service.entity.Template;
import com.theinfinity007.user_service.enums.OtpAction;
import com.theinfinity007.user_service.integration.outbound.serviceclient.mailgun.EmailProvider;
import com.theinfinity007.user_service.service.EmailService;
import com.theinfinity007.user_service.service.TemplateService;
import com.theinfinity007.user_service.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailProvider emailProvider;
    private final TemplateService templateService;
    private final EmailConfig emailConfig;

    @Override
    public void send(String email, OtpAction action, String otpCode, String lang) {

        String type = AppConstant.TEMPLATE_TYPE_EMAIL;
        Template template = templateService.get(type, action.toString(), lang);

        Map<String, String> options = new HashMap<>();
        options.put("otp", otpCode);
        options.put("brandName", emailConfig.getBrandName());
        String html = CommonUtil.getInterpolatedString(template.getText(), options);
        String from = getFrom();

        emailProvider.send(email, template.getSubject(), html, from, null);
    }

    private String getFrom() {
        return emailConfig.getBrandName() + " <" + emailConfig.getLocalPart() + "@" + emailConfig.getSenderEmail() + ">";
    }


}
