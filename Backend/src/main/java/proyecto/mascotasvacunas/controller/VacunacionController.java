package proyecto.mascotasvacunas.controller;


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
    public List<Vacunacion> getAllVacunaciones() {
        return vacunacionService.findAll();
    }

    // Obtiene las vacunaciones de una mascota.
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<Vacunacion>> getVacunacionesByMascota(
            @PathVariable Long mascotaId) {

        try {
            return ResponseEntity.ok(
                    vacunacionService.findByMascota(mascotaId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Busca una vacunación por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Vacunacion> getVacunacionById(
            @PathVariable Long id) {

        try {
            Vacunacion vacunacion = vacunacionService.findById(id);
            return ResponseEntity.ok(vacunacion);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crea una vacunación asociada a una mascota.
    @PostMapping("/mascota/{mascotaId}")
    public ResponseEntity<Vacunacion> crearVacunacion(
            @PathVariable Long mascotaId,
            @RequestBody Vacunacion vacunacion) {

        try {
            return ResponseEntity.ok(
                    vacunacionService.crear(mascotaId, vacunacion)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualiza una vacunación.
    @PutMapping("/{id}")
    public ResponseEntity<Vacunacion> updateVacunacion(
            @PathVariable Long id,
            @RequestBody Vacunacion vacunacion) {

        try {
            return ResponseEntity.ok(
                    vacunacionService.update(id, vacunacion)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina una vacunación.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacunacion(
            @PathVariable Long id) {

        try {
            vacunacionService.delete(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Sube la fotografía de la etiqueta de una vacunación.
    @PostMapping(
            value = "/{id}/foto-etiqueta",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> subirFotoEtiqueta(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {

        try {
            // Comprueba que la vacunación pertenece al usuario autenticado.
            vacunacionService.findById(id);

            String nombreFoto = fotoVacunacionService.guardarFoto(archivo);

            vacunacionService.actualizarFotoEtiqueta(id, nombreFoto);

            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();

        } catch (IOException e) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Consulta la fotografía de la etiqueta de una vacunación.
    @GetMapping("/{id}/foto-etiqueta")
    public ResponseEntity<Resource> obtenerFotoEtiqueta(
            @PathVariable Long id) {

        try {
            // Comprueba que la vacunación pertenece al usuario autenticado.
            Vacunacion vacunacion = vacunacionService.findById(id);

            if (vacunacion.getFotoEtiqueta() == null
                    || vacunacion.getFotoEtiqueta().isBlank()) {
                return ResponseEntity.notFound().build();
            }

            Path directorio = Paths.get(directorioFotos)
                    .toAbsolutePath()
                    .normalize();

            Path ruta = directorio
                    .resolve(vacunacion.getFotoEtiqueta())
                    .normalize();

            // Evita acceder a archivos fuera del directorio de fotografías.
            if (!ruta.startsWith(directorio)) {
                return ResponseEntity.badRequest().build();
            }

            Resource recurso = new UrlResource(ruta.toUri());

            if (!recurso.exists() || !recurso.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String tipoContenido = Files.probeContentType(ruta);

            if (tipoContenido == null
                    || !tipoContenido.startsWith("image/")) {
                tipoContenido = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\""
                                    + recurso.getFilename() + "\""
                    )
                    .contentType(MediaType.parseMediaType(tipoContenido))
                    .body(recurso);

        } catch (RuntimeException | IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
