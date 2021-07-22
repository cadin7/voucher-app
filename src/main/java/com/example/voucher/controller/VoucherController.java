package com.example.voucher.controller;

import com.example.voucher.exceptions.VoucherNotFoundException;
import com.example.voucher.model.api.Voucher;
import com.example.voucher.model.mapper.VoucherMapper;
import com.example.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("vouchers")
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherMapper mapper;

    @GetMapping
    List<Voucher> getAll() {
        return mapper.toApi(voucherService.getAll());
    }

    @GetMapping("{voucherId}")
    Voucher redeemVoucher(@PathVariable String voucherId) {
        return voucherService.redeemVoucher(voucherId)
                .map(mapper::toApi)
                .orElseThrow(() -> new VoucherNotFoundException("Voucher with ID " + voucherId + " is not found!"));
    }

    @PostMapping
    List<Voucher> addVoucher(@RequestBody List<Voucher> vouchers) {
        return mapper.toApi(voucherService.addVoucher(mapper.toEntity(vouchers)));
    }

}
