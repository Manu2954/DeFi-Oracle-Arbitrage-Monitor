package com.arbitrage.monitor;

public record JsonRpcRequest(
        String jsonrpc,
        String method,
        Object[] params,
        int id) {

  public record CallParams(
          String to,
          String data
  ) {
  }
}
