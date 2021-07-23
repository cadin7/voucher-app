package com.example.voucher.service;

import com.example.voucher.exceptions.VoucherValidationException;
import com.example.voucher.model.api.Voucher;
import com.example.voucher.model.mapper.VoucherMapper;
import com.example.voucher.repository.VoucherRepository;
import com.example.voucher.service.validator.VoucherValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static java.time.LocalDateTime.now;
import static java.util.List.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
class VoucherServiceTest {

    private VoucherService voucherService;
    private VoucherMapper mapper;

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        mapper = new VoucherMapper();
        VoucherValidator voucherValidator = new VoucherValidator();
        voucherService = new VoucherService(voucherRepository, voucherValidator);
    }

    @AfterEach
    void cleanup() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("WHEN voucher ID is empty THEN the result is empty")
    void emptyIdVoucher() {
        assertThat(voucherService.redeemVoucher("")).isEmpty();
    }

    @Test
    @DisplayName("WHEN add voucher THEN the result is the voucher")
    void addVoucher() {
        final var vouchers =
                of(Voucher.builder()
                                .percentage(10)
                                .validUntil(now().plusYears(2))
                                .numberOfRedemptions(3)
                                .build(),
                        Voucher.builder()
                                .percentage(10)
                                .validUntil(now().plusYears(2))
                                .numberOfRedemptions(3)
                                .build());
        final var voucherEntities = voucherService.addVoucher(mapper.toEntity(vouchers));
        for (int i = 0; i < vouchers.size(); i++) {
            assertEquals(voucherEntities.get(i).getPercentage(), vouchers.get(i).getPercentage());
            assertEquals(voucherEntities.get(i).getNumberOfRedemptions(), vouchers.get(i).getNumberOfRedemptions());
            assertEquals(voucherEntities.get(i).getValidUntil(), vouchers.get(i).getValidUntil());

        }
    }

    @Test
    @DisplayName("WHEN add voucher without percentage THEN throw exception")
    void addVoucherWithoutPercentage() {
        assertThrows(VoucherValidationException.class,
                () -> voucherService.addVoucher(
                        of(mapper.toEntity(
                                Voucher.builder()
                                        .validUntil(now().plusYears(2))
                                        .numberOfRedemptions(3)
                                        .build()))));
    }

    @Test
    @DisplayName("WHEN add voucher without redemption THEN throw exception")
    void addVoucherWithoutRedemption() {
        assertThrows(VoucherValidationException.class,
                () -> voucherService.addVoucher(
                        of(mapper.toEntity(
                                Voucher.builder()
                                        .percentage(10)
                                        .validUntil(now().plusYears(2))
                                        .numberOfRedemptions(0)
                                        .build()))));
    }

    @Test
    @DisplayName("WHEN add voucher with validity less than current date THEN throw exception")
    void addVoucherWithInvalidValidity() {
        assertThrows(VoucherValidationException.class,
                () -> voucherService.addVoucher(
                        of(mapper.toEntity(
                                Voucher.builder()
                                        .percentage(10)
                                        .validUntil(now().minusYears(2))
                                        .numberOfRedemptions(3)
                                        .build()))));
    }

    @Test
    @DisplayName("WHEN add voucher without validity THEN the result is a voucher with validity")
    void addVoucherWithoutValidity() {
        final var voucher = Voucher.builder()
                .percentage(10)
                .numberOfRedemptions(3)
                .build();
        final var voucherEntity = voucherService.addVoucher(of(mapper.toEntity(voucher)));
        assertThat(voucherEntity.get(0).getValidUntil()).isNotNull();
    }

    @Test
    @DisplayName("WHEN voucher X redemptions redeemed THEN the result is a voucher with X-1 redemptions")
    void redeemXRedemptionVoucher() {
        final var numberOfRedemptions = 5;
        final var id = "voucherX";
        final var voucher = Voucher.builder()
                .id(id)
                .percentage(10)
                .numberOfRedemptions(numberOfRedemptions)
                .build();
        voucherService.addVoucher(of(mapper.toEntity(voucher)));
        final var voucherEntity = voucherService.redeemVoucher(id);
        voucherEntity.ifPresent(entity -> assertEquals(numberOfRedemptions - 1, entity.getNumberOfRedemptions()));
    }

    @Test
    @DisplayName("WHEN voucher Multiple redemptions redeemed THEN the result is a voucher with Multiple redemptions")
    void redeemMultipleRedemptionVoucher() {
        final var id = "voucherMultiple";
        final var voucher = Voucher.builder()
                .id(id)
                .percentage(10)
                .build();
        voucherService.addVoucher(of(mapper.toEntity(voucher)));
        final var voucherEntity = voucherService.redeemVoucher(id);
        voucherEntity.ifPresent(entity -> {
            assertEquals(voucher.getId(), entity.getId());
            assertEquals(voucher.getNumberOfRedemptions(), entity.getNumberOfRedemptions());
        });
    }

    @Test
    @DisplayName("WHEN voucher with 1 redemptions redeemed THEN the result is voucher without redemption")
    void redeemSingleRedemptionVoucher() {
        final var id = "voucherSingle";
        final var voucher = Voucher.builder()
                .id(id)
                .percentage(10)
                .numberOfRedemptions(1)
                .build();
        final var voucherEntity = voucherService.redeemVoucher(id);
        voucherEntity.ifPresent(entity -> {
            assertEquals(voucher.getId(), entity.getId());
            assertEquals(0, entity.getNumberOfRedemptions());
        });
    }

}
