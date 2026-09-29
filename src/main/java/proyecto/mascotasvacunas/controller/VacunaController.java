package proyecto.mascotasvacunas.controller;

import proyecto.mascotasvacunas.entity.Vacuna;
import proyecto.mascotasvacunas.service.VacunaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Esta clase controla las operaciones relacionadas con las vacunas.
 */
@RestController
@RequestMapping("/api/vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }

    // Devuelve todas las vacunas.
    @GetMapping
    public List<Vacuna> findAll() {
        return vacunaService.findAll();
    }

    // Busca una vacuna por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Vacuna> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vacunaService.findById(id));
    }

    // Guarda una vacuna.
    @PostMapping
    public ResponseEntity<Vacuna> save(@RequestBody Vacuna vacuna) {
        return ResponseEntity.ok(vacunaService.save(vacuna));
    }

    // Actualiza una vacuna.
    @PutMapping("/{id}")
    public ResponseEntity<Vacuna> update(
            @PathVariable Long id,
            @RequestBody Vacuna vacuna) {

        return ResponseEntity.ok(
                vacunaService.update(id, vacuna)
        );
    }

    // Elimina una vacuna.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vacunaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}