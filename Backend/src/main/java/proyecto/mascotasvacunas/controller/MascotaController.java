package proyecto.mascotasvacunas.controller;

import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.service.FotoMascotaService;
import proyecto.mascotasvacunas.service.MascotaService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

/*
 * Esta clase controla las operaciones relacionadas con las mascotas.
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;
    private final FotoMascotaService fotoMascotaService;

    public MascotaController(
            MascotaService mascotaService,
            FotoMascotaService fotoMascotaService) {
        this.mascotaService = mascotaService;
        this.fotoMascotaService = fotoMascotaService;
    }

    // Obtiene todas las mascotas del usuario que ha iniciado sesión.
    @GetMapping
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        try {
            return ResponseEntity.ok(
                    mascotaService.findAll()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Busca una mascota por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(
            @PathVariable Long id) {
        try {
            Mascota mascota = mascotaService.findById(id);
            return ResponseEntity.ok(mascota);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Guarda una nueva mascota para el usuario que ha iniciado sesión.
    @PostMapping
    public ResponseEntity<Mascota> saveMascota(
            @RequestBody Mascota mascota) {
        try {
            return ResponseEntity.ok(mascotaService.save(mascota));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Modifica los datos de una mascota existente.
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(
            @PathVariable Long id,
            @RequestBody Mascota mascotaDetails) {
        try {
            Mascota updatedMascota =
                    mascotaService.update(id, mascotaDetails);

            return ResponseEntity.ok(updatedMascota);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina una mascota.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(
            @PathVariable Long id) {
        try {
            mascotaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Sube o actualiza la fotografía de una mascota.
    @PostMapping(
            value = "/{id}/foto",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Mascota> subirFoto(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {

        // Comprueba que la mascota pertenece al usuario autenticado.
        mascotaService.findById(id);

        try {
            String nombreFoto = fotoMascotaService.guardarFoto(archivo);

            Mascota mascotaActualizada =
                    mascotaService.actualizarFotoMascota(id, nombreFoto);

            return ResponseEntity.ok(mascotaActualizada);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se ha podido guardar la fotografía",
                    e);
        }
    }

    // Obtiene la fotografía de una mascota.
    @GetMapping("/{id}/foto")
    public ResponseEntity<Resource> obtenerFoto(
            @PathVariable Long id) {

        // Comprueba que la mascota pertenece al usuario autenticado.
        Mascota mascota = mascotaService.findById(id);

        if (mascota.getFotoMascota() == null
                || mascota.getFotoMascota().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La mascota no tiene fotografía");
        }

        try {
            Path directorioFotos = Paths.get("uploads", "mascotas")
                    .toAbsolutePath()
                    .normalize();

            Path ruta = directorioFotos
                    .resolve(mascota.getFotoMascota())
                    .normalize();

            if (!ruta.startsWith(directorioFotos)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ruta de fotografía no válida");
            }

            Resource recurso = new UrlResource(ruta.toUri());

            if (!Files.isRegularFile(ruta)
                    || !recurso.exists()
                    || !recurso.isReadable()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encuentra la fotografía");
            }

            String tipoContenido = Files.probeContentType(ruta);

            MediaType tipoMedia = tipoContenido != null
                    && tipoContenido.startsWith("image/")
                    ? MediaType.parseMediaType(tipoContenido)
                    : MediaType.APPLICATION_OCTET_STREAM;

            return ResponseEntity.ok()
                    .contentType(tipoMedia)
                    .body(recurso);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se ha podido leer la fotografía",
                    e);
        }
    }
}
