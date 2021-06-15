package com.ptit.gateway;

import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public AccessControlFilter accessControlFilter() {
        return new AccessControlFilter();
    }

}
