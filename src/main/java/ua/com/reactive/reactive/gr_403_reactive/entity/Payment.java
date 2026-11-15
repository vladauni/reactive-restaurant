package ua.com.reactive.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;
    private Long billId;
    private double amount;
    private String method;
}