package lk.ijse.hrms.dto.tm;


import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PlaceOrderTM {
    private String proj_id;
    private String proj_name;
    private Double price;
    private Button btnRemove;
}
