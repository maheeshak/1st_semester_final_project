package lk.ijse.hrms.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class DisActionTM {
    private String dis_action;
    private String emp_id;
    private String date;
    private Button preview;
    private Button remove;
}
