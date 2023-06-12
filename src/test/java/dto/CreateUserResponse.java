package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {

    private String full_name;
    private String email;
    private String magic_link;
    private String created;
    private String updated;
}
