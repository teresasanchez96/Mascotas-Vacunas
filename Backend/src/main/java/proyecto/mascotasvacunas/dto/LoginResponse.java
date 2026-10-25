package proyecto.mascotasvacunas.dto;

/*
 * Esta clase representa la respuesta de la API después de un inicio de sesión correcto.
 * Contiene el token JWT generado para el usuario.
 */
public class LoginResponse {

    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    // Obtiene el token JWT.
    public String getToken() {
        return token;
    }

    // Establece el token JWT.
    public void setToken(String token) {
        this.token = token;
    }
}