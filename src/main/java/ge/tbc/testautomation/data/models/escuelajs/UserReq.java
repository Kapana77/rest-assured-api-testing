package ge.tbc.testautomation.data.models.escuelajs;

public class UserReq {
    private String email;
    private String name;
    private String password;
    private String role;
    private String avatar;

    public UserReq(String email, String name, String password, String role, String avatar) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
