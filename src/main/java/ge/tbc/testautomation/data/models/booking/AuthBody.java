package ge.tbc.testautomation.data.models.booking;

import lombok.Getter;

@Getter
public class AuthBody {
    private String username;
    private String password;

    public AuthBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
