package com.theinfinity007.user_service.integration.outbound.serviceclient.mailgun;

import com.theinfinity007.user_service.client.HttpClient;
import com.theinfinity007.user_service.config.EmailConfig;
import com.theinfinity007.user_service.exception.UserServiceException;
import com.theinfinity007.user_service.integration.outbound.serviceclient.mailgun.dto.MailgunResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class MailgunServiceImpl implements EmailProvider {

    private final HttpClient httpClient;
    private final EmailConfig emailConfig;
    private final Log logger = LogFactory.getLog(getClass());

    public String sendSimpleMessage(String receipientEmail, String otpCode) {

        Map<String, String> headers = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();
        headers.put(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(emailConfig.getUsername(), emailConfig.getApiKey(), null));
        String path = "/messages";
        String baseUrl = emailConfig.getBaseUrl();

        queryParams.put("from", emailConfig.getBrandName() + " <" + emailConfig.getLocalPart() + "@" + emailConfig.getSenderEmail() + ">");
        queryParams.put("to", receipientEmail);
        queryParams.put("subject", "Reset Password");
        queryParams.put("text", "You OTP for password reset is " + otpCode);

        URI uri = URI.create(baseUrl + path);

        try {
             MailgunResponseDTO response = httpClient.request(HttpMethod.POST, uri.toString(), null, headers, null, queryParams, MailgunResponseDTO.class).getBody();
            logger.info("Email sent successfully, id: " + response.getId());
            return "Response sent successfully";
        } catch(HttpClientErrorException | HttpServerErrorException e){
            logger.error("Mailgun Service Http Error: " + e.getStatusCode() + " Response: " + e.getResponseBodyAsString(), e);
            throw new UserServiceException(HttpStatus.FAILED_DEPENDENCY.getReasonPhrase(), "Mailgun Service Error: " + e.getMessage(), HttpStatus.FAILED_DEPENDENCY.value());
        } catch (Exception e) {
            logger.error("Error in Mailgun Service: " + e.getMessage() + " Exception Type: " + e.getClass().getName(), e);
            throw e;
        }
    }
}
