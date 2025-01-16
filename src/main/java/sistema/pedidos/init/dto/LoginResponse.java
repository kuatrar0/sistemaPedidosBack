package sistema.pedidos.init.dto;

public class LoginResponse {
    private String message;
    private String token;
    private String role;
    private String userId;

    public LoginResponse(String message, String token, String role, String userId) {
        this.message = message;
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}