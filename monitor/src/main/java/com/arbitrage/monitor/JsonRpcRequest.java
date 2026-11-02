package com.arbitrage.monitor;

public record JsonRpcRequest(
        String jsonrpc,
        String method,
        Object[] params,
        int id) {
}
