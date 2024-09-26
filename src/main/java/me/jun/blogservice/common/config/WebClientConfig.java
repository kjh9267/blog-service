package me.jun.blogservice.common.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Profile("default")
@Configuration
@LoadBalancerClient(
        name = "MEMBER-SERVICE",
        configuration = MemberServiceDiscoveryClientConfig.class
)
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder writerWebClientBuilder() {
        return WebClient.builder();
    }
}
