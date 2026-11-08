package com.arbitrage.monitor;

import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Service
public class ChainRpcClientService {
  private final RestClient restClient;
  private final ABIEncodingService abiEncodingService;

  private final Logger log = LoggerFactory.getLogger(ChainRpcClientService.class);

  private static final String LATEST_BLOCK_NUMBER_METHOD = "eth_blockNumber";
  private static final String ETH_CALL_METHOD = "eth_call";
  private static final String JSON_RPC_VERSION = "2.0";
  private static final int REQUEST_ID = 1;
  private static final String AAVE_ORACLE_SMART_CONTRACT_ADDRESS = "0x54586bE62E3c3580375aE3723C145253060Ca0C2";
  private static final String AAVE_ORACLE_GET_ASSET_PRICE_SIGNATURE = "getAssetPrice(address)";

  public ChainRpcClientService(RestClient restClient, ABIEncodingService abiEncodingService) {
    this.restClient = restClient;
    this.abiEncodingService = abiEncodingService;
  }

  public Long getLatestBlockNumber(String xApiKeyEncoded) {

    String xApiKeyDecoded = getBase64Decoded(xApiKeyEncoded);

    JsonRpcRequest request = new JsonRpcRequest(
        JSON_RPC_VERSION,
        LATEST_BLOCK_NUMBER_METHOD,
        new Object[]{},
        REQUEST_ID
    );

    JsonRpcResponse<String> response = restClient.post()
        .uri("/"+xApiKeyDecoded)
        .body(request)
        .retrieve()
        .body(new ParameterizedTypeReference<JsonRpcResponse<String>>() {});

    if (response == null) {
      throw new IllegalStateException("Empty RPC response");
    }

    if (response.error() != null) {
      throw new RuntimeException("Error from JSON-RPC: "
          + response.error().message());
    }

    Long blockNumber = Long.decode(response.result());

    log.info("Latest block number retrieved: {}", blockNumber);

    return blockNumber;
  }

  public String getAssetPrice(String xApiKeyEncoded, String address) {
    String xApiKeyDecoded = getBase64Decoded(xApiKeyEncoded);
    String abiEncodedFunctionSignature =
        abiEncodingService.encodeFunctionSelector(AAVE_ORACLE_GET_ASSET_PRICE_SIGNATURE);
    String abiEncodedAddress =
        abiEncodingService.encodeAddressArgument(address);
    JsonRpcRequest request = new JsonRpcRequest(
        JSON_RPC_VERSION,
        ETH_CALL_METHOD,
        new Object[]{
            new JsonRpcRequest.CallParams(
                AAVE_ORACLE_SMART_CONTRACT_ADDRESS,
                abiEncodedFunctionSignature + abiEncodedAddress
            ),
            "latest"
        },
        REQUEST_ID
    );

    JsonRpcResponse<String> response =  restClient.post()
        .uri("/" + xApiKeyDecoded)
        .body(request)
        .retrieve()
        .body(new ParameterizedTypeReference<JsonRpcResponse<String>>() {
        });

    log.info("Asset price retrieved for address {}: {}", address, response);

    assert response != null;
    return response.result();
  }


  private String getBase64Decoded(String xApiKeyEncoded) {
    if (!StringUtils.hasText(xApiKeyEncoded)) {
      throw new IllegalArgumentException("x-api-key header is missing or empty");
    }
    return new String(Base64.getDecoder().decode(xApiKeyEncoded));
  }

}
