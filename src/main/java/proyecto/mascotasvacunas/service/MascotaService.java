package proyecto.mascotasvacunas.service;

import proyecto.mascotasvacunas.entity.Mascota;
import proyecto.mascotasvacunas.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> findAll() {
        return mascotaRepository.findAll();
    }

    // Busca una mascota por su ID.
    public Mascota findById(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mascota no encontrada"));
    }

    public Mascota save(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    // Modifica los datos de una mascota existente.
    public Mascota update(Long id, Mascota mascotaDetails) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Mascota no encontrada con id: " + id));

        mascota.setNombre(mascotaDetails.getNombre());
        mascota.setEspecie(mascotaDetails.getEspecie());
        mascota.setGenero(mascotaDetails.getGenero());
        mascota.setRaza(mascotaDetails.getRaza());
        mascota.setFechaNacimiento(mascotaDetails.getFechaNacimiento());
        mascota.setObservaciones(mascotaDetails.getObservaciones());

        return mascotaRepository.save(mascota);
    }

    // Elimina una mascota.
    public void delete(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Mascota no encontrada con id: " + id));

        mascotaRepository.delete(mascota);
    }
}