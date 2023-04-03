package com.pos.kuppiya.pointofsale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseFilterIdCustomerDTO {
    private int customerId;
    private String customerAddress;
    private double customerSalary;
}
