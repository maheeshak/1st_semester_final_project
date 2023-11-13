package lk.ijse.hrms.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class LegalCaseTM {
    private String emp_id;
    private String leg_case;
    private String date;
    private Button preview;
    private Button remove;
}
