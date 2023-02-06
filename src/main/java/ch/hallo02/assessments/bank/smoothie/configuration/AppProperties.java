package ch.hallo02.assessments.bank.smoothie.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("app")
@Component
public class AppProperties {
    private String loginSuccessHandlerUrl;
    private List<String> allowedOrigins;

    public String getLoginSuccessHandlerUrl() {
        return loginSuccessHandlerUrl;
    }

    public void setLoginSuccessHandlerUrl(String loginSuccessHandlerUrl) {
        this.loginSuccessHandlerUrl = loginSuccessHandlerUrl;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
