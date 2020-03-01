package com.jdbcservlets.data.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Account implements Persistable<Long> {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Currency currency;
    private Long clientId;

}
