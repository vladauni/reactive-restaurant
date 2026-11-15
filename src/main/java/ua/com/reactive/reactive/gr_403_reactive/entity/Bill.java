package ua.com.reactive.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private Long id;
    private Long orderId;
    private double amount;
    private boolean paid;
}