package com.example.voucher.bootstrap;

import com.example.voucher.model.entity.VoucherEntity;
import com.example.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.time.LocalDateTime.of;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final VoucherService voucherService;

    @Override
    public void run(String... args) throws Exception {
        voucherService.addVoucher(List.of(
                VoucherEntity.builder()
                        .percentage(10)
                        .validUntil(of(2021, 7, 16, 12, 33))
                        .numberOfRedemptions(3)
                        .build(),
                VoucherEntity.builder()
                        .percentage(15)
                        .validUntil(of(2022, 2, 26, 12, 33))
                        .numberOfRedemptions(5)
                        .build(),
                VoucherEntity.builder()
                        .percentage(25)
                        .validUntil(of(2021, 11, 3, 12, 33))
                        .numberOfRedemptions(1)
                        .build()));
    }
}
