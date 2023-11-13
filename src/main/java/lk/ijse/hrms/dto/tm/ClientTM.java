package lk.ijse.hrms.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ClientTM {
    private String client_id;
    private String client_name;
    private String client_address;
    private String client_contact;
    private String client_email;
    private Button client_preview;
    private Button client_remove;
}
