package com.arbitrage.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ChainRpcClientService {
  private final RestClient restClient;

  private final Logger log = LoggerFactory.getLogger(ChainRpcClientService.class);

  private static final String LATEST_BLOCK_NUMBER_METHOD = "eth_blockNumber";
  private static final String JSON_RPC_VERSION = "2.0";
  private static final int REQUEST_ID = 1;

  public ChainRpcClientService(RestClient restClient) {
    this.restClient = restClient;
  }

  public Long getLatestBlockNumber() {
    JsonRpcRequest request = new JsonRpcRequest(
        JSON_RPC_VERSION,
        LATEST_BLOCK_NUMBER_METHOD,
        new Object[]{},
        REQUEST_ID
    );

    JsonRpcResponse<String> response = restClient.post()
        .uri("/")
        .body(request)
        .retrieve()
        .body(new ParameterizedTypeReference<JsonRpcResponse<String>>() {});

    if (response == null) {
      throw new IllegalStateException("Empty RPC response");
    }

    if (response.error() != null) {
      throw new RuntimeException("Error from JSON-RPC: "
          + response.error().errorMessage());
    }

    Long blockNumber = Long.decode(response.result());

    log.info("Latest block number retrieved: {}", blockNumber);

    return blockNumber;
  }
}
