package ua.com.reactive.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class Order {

    @Id
    private Long id;

    private Long clientId;

    private Long administratorId;   // хто підтвердив (null, поки не підтверджено)

    @Transient
    private List<OrderItem> items;  // зберігається в order_items, не в orders

    private String status;
}