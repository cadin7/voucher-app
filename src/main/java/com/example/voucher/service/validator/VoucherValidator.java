package com.example.voucher.service.validator;

import com.example.voucher.exceptions.VoucherValidationException;
import com.example.voucher.model.entity.VoucherEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
public class VoucherValidator {

    public void validateVoucher(VoucherEntity voucherEntity) {
        validate(voucherEntity, false)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    public void validateNewVoucher(VoucherEntity voucherEntity) {
        validate(voucherEntity, true)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    private Optional<VoucherValidationException> validate(VoucherEntity voucherEntity, boolean isNewVoucher) {
        if (isNewVoucher && voucherEntity.getPercentage() < 1 || voucherEntity.getPercentage() > 100) {
            return of(new VoucherValidationException("Voucher with " + voucherEntity.getPercentage() + "% is " +
                    "invalid!"));
        } else if (voucherEntity.getValidUntil() != null && voucherEntity.getValidUntil().isBefore(now())) {
            return of(new VoucherValidationException("Validity expired for voucher with ID: " + voucherEntity.getId()));
        } else if (voucherEntity.getNumberOfRedemptions() != null && voucherEntity.getNumberOfRedemptions() < 1) {
            return of(new VoucherValidationException("Not enough redemptions for voucher with ID: " + voucherEntity.getId()));
        } else {
            return empty();
        }
    }
}
