package com.arbitrage.monitor;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eth")
public class EthRpcController {

  private final ChainRpcClientService chainRpcClientService;
  
  public EthRpcController(ChainRpcClientService chainRpcClientService) {
    this.chainRpcClientService = chainRpcClientService;
  }
  
  @GetMapping("/latestBlockNumber")
  public Long getBlockNumber() {
    return chainRpcClientService.getLatestBlockNumber();
  }

  @GetMapping("/assetPrice/{address}")
  public BigDecimal getAssetPrice(@PathVariable String address,
      @RequestParam(required = false) int decimals) {
    return chainRpcClientService.getAssetPrice(address, decimals);
  }

}
