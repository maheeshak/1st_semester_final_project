package lk.ijse.hrms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString



public class AttendanceDetails {
    private String emp_id;
    private String name;
    private String date;
    private String time;
    private String status;
}
