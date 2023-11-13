package lk.ijse.hrms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString



public class Attendance {
    private String emp_id;
    private String name;
    private String designation;
    private String date;
    private String status;

}
