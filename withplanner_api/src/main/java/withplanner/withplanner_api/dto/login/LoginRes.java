package withplanner.withplanner_api.dto.login;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Data
@NoArgsConstructor
public class LoginRes {
    private String jwtToken;

    public LoginRes(String jwtToken){
        this.jwtToken=jwtToken;
    }
}
