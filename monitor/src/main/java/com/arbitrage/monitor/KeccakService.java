package com.arbitrage.monitor;

import org.bouncycastle.crypto.digests.KeccakDigest;
import org.springframework.stereotype.Component;

@Component
public class KeccakService {

  public byte[] keccak256Hash(byte[] input){
    KeccakDigest digest = new KeccakDigest(256);
    digest.update(input, 0, input.length);
    byte[] hash = new byte[digest.getDigestSize()];
    digest.doFinal(hash, 0);
    return hash;
  }

}
