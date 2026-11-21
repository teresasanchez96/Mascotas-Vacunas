package proyecto.mascotasvacunas.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import proyecto.mascotasvacunas.entity.Usuario;
import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.entity.Vacuna;
import proyecto.mascotasvacunas.entity.Vacunacion;

import proyecto.mascotasvacunas.repository.UsuarioRepository;
import proyecto.mascotasvacunas.repository.MascotaRepository;
import proyecto.mascotasvacunas.repository.VacunaRepository;
import proyecto.mascotasvacunas.repository.VacunacionRepository;

@Configuration
public class DatosPruebaConfig {

    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final VacunaRepository vacunaRepository;
    private final VacunacionRepository vacunacionRepository;
    private final PasswordEncoder passwordEncoder;

    public DatosPruebaConfig(
            UsuarioRepository usuarioRepository,
            MascotaRepository mascotaRepository,
            VacunaRepository vacunaRepository,
            VacunacionRepository vacunacionRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.vacunaRepository = vacunaRepository;
        this.vacunacionRepository = vacunacionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner cargarDatosPrueba() {

        return args -> {

            String email = "usuario_prueba@gmail.com";

            // 1. Recuperar el usuario existente o crearlo.
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseGet(() -> usuarioRepository.save(
                            new Usuario(
                                    "Usuario de prueba",
                                    email,
                                    passwordEncoder.encode("Clave123!")
                            )
                    ));

            System.out.println("Usuario de prueba disponible.");

            // 2. Crear las mascotas si todavía no existen.

            Mascota toby = obtenerOCrearMascota(
                    usuario, "Toby", "Perro", "Macho",
                    "Golden Retriever",
                    LocalDate.of(2021, 3, 15),
                    "Muy juguetón",
                    "toby-perro-golden-retriever.jpg"
            );

            Mascota kiara = obtenerOCrearMascota(
                    usuario, "Kiara", "Perro", "Hembra",
                    "Chihuahua",
                    LocalDate.of(2022, 6, 10),
                    "Muy cariñosa",
                    "kiara-perro-chihuahua.jpg"
            );

            Mascota nala = obtenerOCrearMascota(
                    usuario, "Nala", "Gato", "Hembra",
                    "Europeo común",
                    LocalDate.of(2023, 2, 20),
                    "Muy curiosa",
                    "nala-gato-comun.jpg"
            );

            Mascota coco = obtenerOCrearMascota(
                    usuario, "Coco", "Conejo", "Macho",
                    "Belier",
                    LocalDate.of(2022, 9, 5),
                    "Le gusta comer heno",
                    "coco-conejo-belier.jpg"
            );

            Mascota simba = obtenerOCrearMascota(
                    usuario, "Simba", "Hurón", "Macho",
                    "Sable",
                    LocalDate.of(2022, 11, 12),
                    "Muy inquieto y juguetón",
                    "simba-huron-sable.jpg"
            );

            System.out.println("Comprobación de las cinco mascotas finalizada.");

            // 3. Crear las vacunas si todavía no existen.

            Vacuna rabiaPerrosGatos =
                    obtenerOCrearVacuna(usuario, "Rabia perros/gatos");

            Vacuna leucemia =
                    obtenerOCrearVacuna(usuario, "Leucemia felina");

            Vacuna mixomatosis =
                    obtenerOCrearVacuna(usuario, "Mixomatosis conejos");

            Vacuna enfermedadHemorragica =
                    obtenerOCrearVacuna(usuario, "Enfermedad Hemorrágica Vírica");

            Vacuna moquilloCanino =
                    obtenerOCrearVacuna(usuario, "Moquillo canino");

            Vacuna moquilloHurones =
                    obtenerOCrearVacuna(usuario, "Moquillo hurones");

            Vacuna rabiaHurones =
                    obtenerOCrearVacuna(usuario, "Rabia hurones");

            System.out.println("Comprobación de las siete vacunas finalizada.");

            // 4. Crear las vacunaciones si todavía no existen.

            // Vacunaciones administradas.

            crearVacunacionSiNoExiste(
                    usuario,
                    toby, rabiaPerrosGatos,
                    LocalDate.of(2022, 10, 1),
                    true,
                    LocalDate.of(2022, 10, 1),
                    "rabia-perros-gatos.png"
            );

            crearVacunacionSiNoExiste(
                    usuario,
                    kiara, moquilloCanino,
                    LocalDate.of(2023, 8, 5),
                    true,
                    LocalDate.of(2023, 8, 5),
                    "moquillo-canino.png"
            );

            crearVacunacionSiNoExiste(
                    usuario,
                    coco, mixomatosis,
                    LocalDate.of(2024, 5, 10),
                    true,
                    LocalDate.of(2024, 5, 10),
                    "mixomatosis-conejos.png"
            );

            crearVacunacionSiNoExiste(
                    usuario,
                    simba, rabiaHurones,
                    LocalDate.of(2023, 6, 15),
                    true,
                    LocalDate.of(2023, 6, 15),
                    "rabia-hurones.png"
            );

            // Vacunaciones pendientes.

            crearVacunacionSiNoExiste(
                    usuario,
                    nala, leucemia,
                    LocalDate.of(2026, 12, 1),
                    false,
                    null,
                    null
            );

            crearVacunacionSiNoExiste(
                    usuario,
                    coco, enfermedadHemorragica,
                    LocalDate.of(2026, 12, 15),
                    false,
                    null,
                    null
            );

            crearVacunacionSiNoExiste(
                    usuario,
                    simba, moquilloHurones,
                    LocalDate.of(2027, 1, 10),
                    false,
                    null,
                    null
            );

            System.out.println("Comprobación de las siete vacunaciones finalizada.");
            System.out.println("Carga de datos de prueba finalizada.");
        };
    }

    // Crear una mascota únicamente si no existe.
    private Mascota obtenerOCrearMascota(
            Usuario usuario,
            String nombre,
            String especie,
            String genero,
            String raza,
            LocalDate fechaNacimiento,
            String observaciones,
            String nombreImagen) throws IOException {

        Mascota mascota = mascotaRepository.findByUsuario(usuario).stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);

        if (mascota == null) {

            mascota = new Mascota(
                    nombre,
                    especie,
                    genero,
                    raza,
                    fechaNacimiento,
                    observaciones,
                    usuario
            );

            mascota.setFotoMascota(copiarImagen(
                    "datos-prueba/mascotas/" + nombreImagen,
                    "uploads/mascotas/" + nombreImagen
            ));

            return mascotaRepository.save(mascota);
        }

        // Si ya existe, conservar sus datos.
        // Solo añadir la fotografía si todavía no tiene ninguna.
        if (mascota.getFotoMascota() == null
                || mascota.getFotoMascota().isBlank()) {

            mascota.setFotoMascota(copiarImagen(
                    "datos-prueba/mascotas/" + nombreImagen,
                    "uploads/mascotas/" + nombreImagen
            ));

            mascotaRepository.save(mascota);
        }

        return mascota;
    }

    // Crear una vacuna únicamente si no existe.
    private Vacuna obtenerOCrearVacuna(
            Usuario usuario,
            String nombreVacuna) {

        return vacunaRepository.findByUsuario(usuario).stream()
                .filter(v -> v.getNombreVacuna().equalsIgnoreCase(nombreVacuna))
                .findFirst()
                .orElseGet(() -> vacunaRepository.save(
                        new Vacuna(nombreVacuna, usuario)
                ));
    }

    // Crear una vacunación únicamente si no existe para esa mascota y vacuna.
    private void crearVacunacionSiNoExiste(
            Usuario usuario,
            Mascota mascota,
            Vacuna vacuna,
            LocalDate fechaPrevista,
            Boolean vacunaAdministrada,
            LocalDate fechaAdministracion,
            String nombreEtiqueta) throws IOException {

        boolean existe = vacunacionRepository.findByMascotaUsuario(usuario).stream()
                .anyMatch(v ->
                        Objects.equals(v.getMascota().getId(), mascota.getId())
                                && Objects.equals(v.getVacuna().getId(), vacuna.getId())
                );

        if (existe) {
            return;
        }

        String rutaImagen = null;

        if (nombreEtiqueta != null) {
            rutaImagen = copiarImagen(
                    "datos-prueba/vacunaciones/" + nombreEtiqueta,
                    "uploads/vacunaciones/" + nombreEtiqueta
            );
        }

        Vacunacion vacunacion = new Vacunacion(
                fechaPrevista,
                vacunaAdministrada,
                fechaAdministracion,
                mascota,
                vacuna
        );

        vacunacion.setFotoEtiqueta(rutaImagen);

        vacunacionRepository.save(vacunacion);
    }

    // Copiar las imágenes al directorio de la aplicación.
    private String copiarImagen(
            String origen,
            String destino) throws IOException {

        Path rutaOrigen = Path.of(origen);
        Path rutaDestino = Path.of(destino);

        Files.createDirectories(rutaDestino.getParent());

        Files.copy(
                rutaOrigen,
                rutaDestino,
                StandardCopyOption.REPLACE_EXISTING
        );

        return rutaDestino.getFileName().toString();
    }
}
