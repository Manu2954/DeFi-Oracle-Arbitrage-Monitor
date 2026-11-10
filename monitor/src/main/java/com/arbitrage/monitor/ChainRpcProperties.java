package com.arbitrage.monitor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "monitor.chain")
public class ChainRpcProperties {
  private String rpcUrl;

  private static final String ALCHEMY_ETH_API = "https://eth-mainnet.g.alchemy.com/v2/alch_5Buxpu1xi5oca0jGHJnk4";

  public String getRpcUrl() {
    if(rpcUrl == null || rpcUrl.isEmpty() || rpcUrl.equals("${RPC_URL}")) {
      return ALCHEMY_ETH_API;
    }
    return rpcUrl;
  }

  public void setRpcUrl(String rpcUrl) {
    this.rpcUrl = rpcUrl;
  }

}