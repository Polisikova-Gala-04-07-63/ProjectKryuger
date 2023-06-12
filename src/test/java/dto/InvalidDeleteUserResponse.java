package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidDeleteUserResponse {
    public String code;
    public String message;
    public String instance;
    public String status;
    public String title;
    public String type;
    public String source;

}

