package proyecto.mascotasvacunas.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                "clave-de-prueba-unitaria-para-jwt-123456789"
        );
    }

    @Test
    void generarTokenYExtraerEmailDevuelveElEmailOriginal() {
        String email = "prueba@ejemplo.com";

        String token = jwtService.generarToken(email);

        assertNotNull(token);
        assertEquals(email, jwtService.extraerEmail(token));
    }

    @Test
    void tokenRecienGeneradoEsValido() {
        String token = jwtService.generarToken("prueba@ejemplo.com");

        assertTrue(jwtService.esTokenValido(token));
    }

    @Test
    void tokenModificadoNoEsValido() {
        String token = jwtService.generarToken("prueba@ejemplo.com");
        String tokenModificado = token.substring(0, token.length() - 2) + "xx";

        assertFalse(jwtService.esTokenValido(tokenModificado));
    }
}
