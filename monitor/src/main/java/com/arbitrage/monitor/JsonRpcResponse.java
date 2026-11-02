package com.arbitrage.monitor;

public record JsonRpcResponse<T>(
        String jsonrpc,
        T result,
        int id,
        JsonRpcError error) {

}
