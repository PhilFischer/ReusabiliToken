package com.example.philipp.cashierapp.Features.operations;

import java.security.Signature;

/**
 * Created by Tobias on 14.02.2018.
 */

public class HumanConfirmableActionProof implements IActionProof {

    private byte[] signature_bytes;

    public HumanConfirmableActionProof(byte[] signature_bytes) {
        this.signature_bytes = signature_bytes;
    }

    public byte[] getSignatureBytes() { return signature_bytes; }

}
