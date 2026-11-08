package com.arbitrage.monitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
  public Long getBlockNumber(@RequestHeader String xApiKeyEncoded) {
    return chainRpcClientService.getLatestBlockNumber(xApiKeyEncoded);
  }

  @GetMapping("/assetPrice/{address}")
  public String getAssetPrice(@RequestHeader String xApiKeyEncoded, @PathVariable String address) {
    return chainRpcClientService.getAssetPrice(xApiKeyEncoded, address);
  }

}
