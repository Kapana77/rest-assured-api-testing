package ge.tbc.testautomation.data.models.pets;

public class UploadImageResponse {
    private int code;
    private String type;
    private String message;

    public UploadImageResponse() {}

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
