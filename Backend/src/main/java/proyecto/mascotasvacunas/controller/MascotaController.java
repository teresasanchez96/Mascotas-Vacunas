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
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        try {
            Mascota mascota = mascotaService.findById(id);
            return ResponseEntity.ok(mascota);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Guarda una nueva mascota para el usuario que ha iniciado sesión.
    @PostMapping
    public ResponseEntity<Mascota> saveMascota(@RequestBody Mascota mascota) {
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
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        try {
            mascotaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}