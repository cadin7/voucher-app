package com.example.voucher.model.mapper;

import com.example.voucher.model.api.Voucher;
import com.example.voucher.model.entity.VoucherEntity;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class VoucherMapper implements Mapper<Voucher, VoucherEntity> {

    @Override
    public Voucher toApi(VoucherEntity source) {
        if (source == null) {
            return null;
        }

        var target = new Voucher();
        target.setId(source.getId());
        target.setPercentage(source.getPercentage());
        target.setNumberOfRedemptions(source.getNumberOfRedemptions());
        target.setValidUntil(source.getValidUntil());

        return target;
    }

    @Override
    public VoucherEntity toEntity(Voucher source) {
        if (source == null) {
            return null;
        }

        var target = new VoucherEntity();
        target.setId(source.getId());
        target.setPercentage(source.getPercentage());
        target.setNumberOfRedemptions(source.getNumberOfRedemptions());
        target.setValidUntil(source.getValidUntil() == null ? now().plusYears(99) : source.getValidUntil());

        return target;
    }
}
