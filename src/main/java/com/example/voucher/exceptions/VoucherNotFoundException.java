package com.example.voucher.exceptions;

public class VoucherNotFoundException extends RuntimeException {

    public VoucherNotFoundException(String message) {
        super(message);
    }
}
