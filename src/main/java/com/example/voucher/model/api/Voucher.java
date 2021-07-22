package com.example.voucher.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    private String id;

    private int percentage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime validUntil;

    private Integer numberOfRedemptions;
}
