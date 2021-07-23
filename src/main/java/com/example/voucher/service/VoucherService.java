package com.example.voucher.service;

import com.example.voucher.model.entity.VoucherEntity;
import com.example.voucher.repository.VoucherRepository;
import com.example.voucher.service.validator.VoucherValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService implements Redemption, Management {

    private final VoucherRepository voucherRepository;
    private final VoucherValidator validator;

    public List<VoucherEntity> getAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<VoucherEntity> redeemVoucher(String voucherId) {
        final Optional<VoucherEntity> voucher = voucherRepository.findById(voucherId);
        voucher.ifPresent(this::decrementVoucherRedemption);
        return voucher;
    }

    private void decrementVoucherRedemption(VoucherEntity voucherEntity) {
        validator.validateVoucher(voucherEntity);
        if (voucherEntity.getNumberOfRedemptions() != null) {
            voucherEntity.setNumberOfRedemptions(voucherEntity.getNumberOfRedemptions() - 1);
        }
        voucherRepository.save(voucherEntity);
    }

    @Override
    public List<VoucherEntity> addVoucher(List<VoucherEntity> vouchers) {
        return vouchers.stream()
                .map(this::addVoucher)
                .collect(Collectors.toList());
    }

    private VoucherEntity addVoucher(VoucherEntity voucherEntity) {
        validator.validateNewVoucher(voucherEntity);
        voucherEntity.setId(null);
        return voucherRepository.save(voucherEntity);
    }
}

