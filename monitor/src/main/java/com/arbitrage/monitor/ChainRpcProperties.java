package com.arbitrage.monitor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "monitor.chain")
public class ChainRpcProperties {
  private String rpcUrl;

  public String getRpcUrl() {
    return rpcUrl;
  }

  public void setRpcUrl(String rpcUrl) {
    this.rpcUrl = rpcUrl;
  }

}