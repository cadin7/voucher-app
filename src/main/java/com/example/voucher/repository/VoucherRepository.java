package com.example.voucher.repository;

import com.example.voucher.model.entity.VoucherEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoucherRepository extends MongoRepository<VoucherEntity, String> {
}
