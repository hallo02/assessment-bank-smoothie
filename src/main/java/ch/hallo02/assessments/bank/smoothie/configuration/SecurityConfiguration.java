package ch.hallo02.assessments.bank.smoothie.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

import java.net.URI;


@Configuration
public class SecurityConfiguration {

    private final AppProperties appProperties;

    public SecurityConfiguration(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {

        var serverHttpSecurity = http
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/**").permitAll()
                        .pathMatchers("/ui/**").permitAll()
                        .anyExchange().denyAll()
                        .and().formLogin()
                        .authenticationSuccessHandler(getRedirectServerAuthenticationSuccessHandler())
                        .and().logout().logoutSuccessHandler(getRedirectServerLogoutSuccessHandler())
                        .and().csrf().disable()

                );
        return serverHttpSecurity.build();
    }

    private RedirectServerAuthenticationSuccessHandler getRedirectServerAuthenticationSuccessHandler() {
        return new RedirectServerAuthenticationSuccessHandler(
                this.appProperties.getLoginSuccessHandlerUrl()
        );
    }

    private RedirectServerLogoutSuccessHandler getRedirectServerLogoutSuccessHandler() {
        RedirectServerLogoutSuccessHandler redirectServerLogoutSuccessHandler = null;
        try {
            redirectServerLogoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
            redirectServerLogoutSuccessHandler.setLogoutSuccessUrl(new URI(this.appProperties.getLoginSuccessHandlerUrl()));

        } catch (Exception e) {
            System.err.println(e);
        }
        return redirectServerLogoutSuccessHandler;
    }

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurerComposite() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                appProperties.getAllowedOrigins().toArray(new String[0])
                        )
                        .allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }
}