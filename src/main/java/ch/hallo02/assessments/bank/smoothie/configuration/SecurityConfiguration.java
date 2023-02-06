package ch.hallo02.assessments.bank.smoothie.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;


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
                        .authenticationSuccessHandler(
                                new RedirectServerAuthenticationSuccessHandler(
                                        this.appProperties.getLoginSuccessHandlerUrl()
                                )
                        )
                        .and().csrf().disable()

                );
        return serverHttpSecurity.build();
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