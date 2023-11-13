package lk.ijse.hrms.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString


public class AttendanceTM {
    private String emp_id;
    private String name;
    private String designation;
    private String date;
    private String status;
    private Button btn;

}
