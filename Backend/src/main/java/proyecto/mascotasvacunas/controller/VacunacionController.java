package proyecto.mascotasvacunas.controller;

import proyecto.mascotasvacunas.entity.Vacunacion;
import proyecto.mascotasvacunas.service.VacunacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Esta clase controla las operaciones relacionadas con las vacunaciones.
 */
@RestController
@RequestMapping("/api/vacunaciones")
public class VacunacionController {

    private final VacunacionService vacunacionService;

    public VacunacionController(VacunacionService vacunacionService) {
        this.vacunacionService = vacunacionService;
    }

    // Obtiene todas las vacunaciones del usuario
    @GetMapping
    public List<Vacunacion> getAllVacunaciones() {
        return vacunacionService.findAll();
    }

    // Obtiene las vacunaciones de una mascota
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
            Vacunacion vacunacion =
                    vacunacionService.findById(id);

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
}