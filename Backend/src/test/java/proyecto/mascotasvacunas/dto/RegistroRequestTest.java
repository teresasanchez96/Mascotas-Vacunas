package proyecto.mascotasvacunas.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistroRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void registroConDatosCorrectosNoTieneErrores() {
        RegistroRequest registro = new RegistroRequest(
                "Teresa",
                "teresa@ejemplo.com",
                "Clave123!"
        );

        assertTrue(validator.validate(registro).isEmpty());
    }

    @Test
    void registroConEmailIncorrectoTieneErrores() {
        RegistroRequest registro = new RegistroRequest(
                "Teresa",
                "correoIncorrecto",
                "Clave123!"
        );

        assertFalse(validator.validate(registro).isEmpty());
    }

    @Test
    void registroConPasswordDebilTieneErrores() {
        RegistroRequest registro = new RegistroRequest(
                "Teresa",
                "teresa@ejemplo.com",
                "clave123"
        );

        assertFalse(validator.validate(registro).isEmpty());
    }
}
