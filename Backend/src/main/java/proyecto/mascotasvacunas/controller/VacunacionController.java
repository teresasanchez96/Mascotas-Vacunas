package proyecto.mascotasvacunas.controller;


import org.springframework.web.server.ResponseStatusException;
import proyecto.mascotasvacunas.entity.Vacunacion;
import proyecto.mascotasvacunas.service.VacunacionService;
import proyecto.mascotasvacunas.service.FotoVacunacionService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * Esta clase controla las operaciones relacionadas con las vacunaciones.
 */
@RestController
@RequestMapping("/api/vacunaciones")
public class VacunacionController {

    private final VacunacionService vacunacionService;
    private final FotoVacunacionService fotoVacunacionService;

    @Value("${app.upload.vacunaciones.dir:uploads/vacunaciones}")
    private String directorioFotos;

    public VacunacionController(
            VacunacionService vacunacionService,
            FotoVacunacionService fotoVacunacionService) {
        this.vacunacionService = vacunacionService;
        this.fotoVacunacionService = fotoVacunacionService;
    }

    // Obtiene todas las vacunaciones del usuario.
    @GetMapping
    public ResponseEntity<List<Vacunacion>> getAllVacunaciones() {
        return ResponseEntity.ok(vacunacionService.findAll());
    }

    // Obtiene las vacunaciones de una mascota.
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<Vacunacion>> getVacunacionesByMascota(
            @PathVariable Long mascotaId) {
        return ResponseEntity.ok(
                vacunacionService.findByMascota(mascotaId));
    }

    // Obtiene una vacunación por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Vacunacion> getVacunacionById(
            @PathVariable Long id) {
        return ResponseEntity.ok(vacunacionService.findById(id));
    }

    // Crea una vacunación asociada a una mascota.
    @PostMapping("/mascota/{mascotaId}")
    public ResponseEntity<Vacunacion> crearVacunacion(
            @PathVariable Long mascotaId,
            @RequestBody Vacunacion vacunacion) {

        return ResponseEntity.ok(
                vacunacionService.crear(mascotaId, vacunacion));
    }

    // Actualiza una vacunación.
    @PutMapping("/{id}")
    public ResponseEntity<Vacunacion> updateVacunacion(
            @PathVariable Long id,
            @RequestBody Vacunacion vacunacionDetails) {

        return ResponseEntity.ok(
                vacunacionService.update(id, vacunacionDetails));
    }

    // Elimina una vacunación.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacunacion(
            @PathVariable Long id) {

        vacunacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Subir la fotografía de la etiqueta de una vacunación.
    @PostMapping("/{id}/foto-etiqueta")
    public ResponseEntity<Vacunacion> subirFotoEtiqueta(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {

        try {
            // Comprobar que la vacunación existe y pertenece al usuario.
            vacunacionService.findById(id);

            // Guardar la fotografía.
            String nombreFoto = fotoVacunacionService.guardarFoto(archivo);

            // Actualizar la vacunación con el nombre de la fotografía.
            Vacunacion vacunacion =
                    vacunacionService.actualizarFotoEtiqueta(id, nombreFoto);

            return ResponseEntity.ok(vacunacion);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se ha podido guardar la fotografía.", e);
        }
    }

    // Obtener la fotografía de la etiqueta de una vacunación.
    @GetMapping("/{id}/foto-etiqueta")
    public ResponseEntity<Resource> obtenerFotoEtiqueta(
            @PathVariable Long id) {

        // Comprobar que la vacunación existe y pertenece al usuario.
        Vacunacion vacunacion = vacunacionService.findById(id);

        String nombreFoto = vacunacion.getFotoEtiqueta();

        if (nombreFoto == null || nombreFoto.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La vacunación no tiene fotografía.");
        }

        try {
            Path directorio = Paths.get(directorioFotos)
                    .toAbsolutePath().normalize();

            Path rutaFoto = directorio.resolve(nombreFoto)
                    .normalize();

            // Evitar acceder a archivos fuera del directorio permitido.
            if (!rutaFoto.startsWith(directorio)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ruta de fotografía no válida.");
            }

            if (!Files.isRegularFile(rutaFoto)
                    || !Files.isReadable(rutaFoto)) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se ha encontrado la fotografía.");
            }

            Resource recurso = new UrlResource(rutaFoto.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(recurso);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se ha podido leer la fotografía.", e);
        }
    }
}