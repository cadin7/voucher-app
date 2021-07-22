package com.example.voucher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("vouchers")
public class VoucherEntity {

    @Id
    private String id = ObjectId.get().toHexString();

    private int percentage;

    private LocalDateTime validUntil;

    private Integer numberOfRedemptions;
}
