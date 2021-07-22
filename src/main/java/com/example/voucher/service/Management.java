package com.example.voucher.service;

import com.example.voucher.model.entity.VoucherEntity;

import java.util.List;

public interface Management {

    List<VoucherEntity> addVoucher(List<VoucherEntity> vouchers);
}
