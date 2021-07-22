package com.example.voucher.service;

import com.example.voucher.model.entity.VoucherEntity;

import java.util.Optional;

public interface Redemption {

    Optional<VoucherEntity> redeemVoucher(String voucherId);
}
