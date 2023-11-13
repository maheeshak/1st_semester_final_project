package lk.ijse.hrms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Order {
    private String order_id;
    private String client_id;
    private String client_name;
    private Double price;
    private String date;
}
