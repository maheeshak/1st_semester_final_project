package lk.ijse.hrms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Project {
    private String proj_id;
    private String proj_name;
    private String duration;
    private String proj_manager;
    private String cost;
    private String proj_phases;
}
