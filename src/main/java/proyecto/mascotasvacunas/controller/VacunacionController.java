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

    // Devuelve todas las vacunaciones.
    @GetMapping
    public List<Vacunacion> findAll() {
        return vacunacionService.findAll();
    }

    // Busca una vacunación por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Vacunacion> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vacunacionService.findById(id));
    }

    // Guarda una vacunación.
    @PostMapping
    public ResponseEntity<Vacunacion> save(@RequestBody Vacunacion vacunacion) {
        return ResponseEntity.ok(vacunacionService.save(vacunacion));
    }

    // Actualiza una vacunación.
    @PutMapping("/{id}")
    public ResponseEntity<Vacunacion> update(
            @PathVariable Long id,
            @RequestBody Vacunacion vacunacion) {

        return ResponseEntity.ok(
                vacunacionService.update(id, vacunacion)
        );
    }

    // Elimina una vacunación.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vacunacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}