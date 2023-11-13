package lk.ijse.hrms.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ProjectTM {
    private String order_id;
    private String client_id;
    private String client_name;
    private Double price;
    private String date;
    private Button preview;
}
