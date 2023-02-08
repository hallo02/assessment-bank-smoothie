package ch.hallo02.assessments.bank.smoothie.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("app")
@Component
public class AppProperties {

    static class User {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private String loginSuccessHandlerUrl;
    private List<String> allowedOrigins;

    private User user;

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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
