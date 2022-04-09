package au.seisma.income.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties({ApiConfigProp.class, IncomeTaxRatePropConfig.class})
public class AppConfig {

    @Bean
    public WebFilter traceIdInResponseFilter(final Tracer tracer) {
        return (exchange, chain) -> {
            ofNullable(tracer.currentSpan()).ifPresent(span -> exchange.getResponse().getHeaders().add("trace-id", span.context().traceId()));
            return chain.filter(exchange);
        };
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }


}
