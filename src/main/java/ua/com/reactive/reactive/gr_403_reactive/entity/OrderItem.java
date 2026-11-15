package ua.com.reactive.reactive.gr_403_reactive.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long menuItemId;
    private String name;
    private int quantity;
    private double price;

    public double getTotal() {
        return price * quantity;
    }
}