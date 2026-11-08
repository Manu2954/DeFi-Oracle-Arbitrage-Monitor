package com.arbitrage.monitor;

public record JsonRpcError(
        int code,
        String message) {

}
