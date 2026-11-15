package ua.com.reactive.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

    private Long id;
    private String name;
    private double price;
}
