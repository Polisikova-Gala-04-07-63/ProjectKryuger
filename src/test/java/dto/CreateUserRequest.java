package dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    private String full_name;
    private String email;
    private String password;
    private String generate_magic_link;

}
