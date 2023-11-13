package lk.ijse.hrms.dto.tm;


import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTM {
    private String emp_id;
    private String emp_name;
    private String emp_nic;
    private String emp_des;
    private String emp_contact;
    private Button preview;
    private Button remove;

}
