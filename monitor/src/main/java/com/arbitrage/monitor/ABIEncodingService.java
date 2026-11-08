package com.arbitrage.monitor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HexFormat;
import org.springframework.stereotype.Component;

@Component
public class ABIEncodingService {

  private final KeccakService keccakService;
  private static final String FUNCTION_SELECTOR_PREFIX = "0x";

  public ABIEncodingService(KeccakService keccakService) {
    this.keccakService = keccakService;
  }

  public String encodeFunctionSelector(String signature) {

    byte[] signatureBytes = signature.getBytes(StandardCharsets.UTF_8);

    byte[] hash = keccakService.keccak256Hash(signatureBytes);

    byte[] selectorBytes = Arrays.copyOfRange(hash, 0, 4);

    String selectorHex = HexFormat.of().formatHex(selectorBytes);

    return FUNCTION_SELECTOR_PREFIX+selectorHex;
  }

  public String encodeAddressArgument(String address) {
    if (address.startsWith("0x")) {
      address = address.substring(2);
    }
    return "0".repeat(24) + address;
  }

}
