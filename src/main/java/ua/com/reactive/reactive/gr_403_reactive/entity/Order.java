package ua.com.reactive.reactive.gr_403_reactive.entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private Long clientId;
    private Long administratorId;   // хто підтвердив (null, поки не підтверджено)
    private List<OrderItem> items;
    private String status;
}
