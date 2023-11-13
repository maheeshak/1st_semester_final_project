package lk.ijse.hrms.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SalaryTM {
    private String emp_id;
    private String month;
    private Double basic;
    private Double earnings;
    private Double dedication;
    private Double net_salary;
}
