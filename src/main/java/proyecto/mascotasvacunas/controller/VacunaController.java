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

    // Obtiene todas las vacunas.
    @GetMapping
    public List<Vacuna> getAllVacunas() {
        return vacunaService.findAll();
    }

    // Busca una vacuna por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Vacuna> getVacunaById(@PathVariable Long id) {
        try {
            Vacuna vacuna = vacunaService.findById(id);
            return ResponseEntity.ok(vacuna);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Guarda una vacuna.
    @PostMapping
    public ResponseEntity<Vacuna> saveVacuna(@RequestBody Vacuna vacuna) {
        return ResponseEntity.ok(vacunaService.save(vacuna));
    }

    // Actualiza una vacuna.
    @PutMapping("/{id}")
    public ResponseEntity<Vacuna> updateVacuna(
            @PathVariable Long id,
            @RequestBody Vacuna vacuna) {

        try {
            return ResponseEntity.ok(
                    vacunaService.update(id, vacuna)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina una vacuna.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacuna(@PathVariable Long id) {
        try {
            vacunaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}