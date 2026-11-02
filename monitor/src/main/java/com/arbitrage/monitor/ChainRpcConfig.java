package com.arbitrage.monitor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(ChainRpcProperties.class)
public class ChainRpcConfig {

  @Bean
  public RestClient chainRestClient(ChainRpcProperties chainRpcProperties) {
    return RestClient.builder()
        .baseUrl(chainRpcProperties.getRpcUrl())
        .defaultHeader(
            HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )
        .build();
  }

}
