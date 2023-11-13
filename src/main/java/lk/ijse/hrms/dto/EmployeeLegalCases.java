package lk.ijse.hrms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeLegalCases {
    private String leg_id;
    private String emp_id;
    private String leg_desc;
    private  String leg_case;
    private String date;
}
