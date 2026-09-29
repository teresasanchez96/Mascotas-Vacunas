package proyecto.mascotasvacunas.controller;

import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Esta clase controla las operaciones relacionadas con las mascotas.
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // Devuelve todas las mascotas.
    @GetMapping
    public List<Mascota> findAll() {
        return mascotaService.findAll();
    }

    // Busca una mascota por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.findById(id));
    }

    // Guarda una mascota.
    @PostMapping
    public ResponseEntity<Mascota> save(@RequestBody Mascota mascota) {
        return ResponseEntity.ok(mascotaService.save(mascota));
    }

    // Actualiza una mascota.
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> update(
            @PathVariable Long id,
            @RequestBody Mascota mascota) {

        return ResponseEntity.ok(
                mascotaService.update(id, mascota)
        );
    }

    // Elimina una mascota.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mascotaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}