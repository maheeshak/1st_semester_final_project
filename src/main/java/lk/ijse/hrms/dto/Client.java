package lk.ijse.hrms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Client {
    private String client_id;
    private String name;
    private String address;
    private String contact;
    private String email;


}
