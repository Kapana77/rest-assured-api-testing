package ge.tbc.testautomation.data.models.booking;

public class AuthBody {
    private String username;
    private String password;

    public AuthBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
