package ch.hallo02.assessments.bank.smoothie.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class StaticConfiguration {


    @Bean
    public RouterFunction<ServerResponse> htmlRouter() {
        return route(GET("/ui"), request
                -> ok().bodyValue(new ClassPathResource("resources/ui/index.html")));
    }

}
