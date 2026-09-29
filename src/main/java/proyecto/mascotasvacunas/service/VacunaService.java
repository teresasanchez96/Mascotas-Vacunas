package proyecto.mascotasvacunas.service;

import proyecto.mascotasvacunas.entity.Vacuna;
import proyecto.mascotasvacunas.repository.VacunaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Esta clase contiene la lógica relacionada con las vacunas.
 */
@Service
public class VacunaService {

    private final VacunaRepository vacunaRepository;

    public VacunaService(VacunaRepository vacunaRepository) {
        this.vacunaRepository = vacunaRepository;
    }

    // Devuelve todas las vacunas.
    public List<Vacuna> findAll() {
        return vacunaRepository.findAll();
    }

    // Busca una vacuna por su ID.
    public Vacuna findById(Long id) {
        return vacunaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vacuna no encontrada"));
    }

    // Guarda una vacuna.
    public Vacuna save(Vacuna vacuna) {
        return vacunaRepository.save(vacuna);
    }

    // Actualiza una vacuna.
    public Vacuna update(Long id, Vacuna vacunaDetails) {

        Vacuna vacuna = findById(id);

        vacuna.setNombreVacuna(vacunaDetails.getNombreVacuna());

        return vacunaRepository.save(vacuna);
    }

    // Elimina una vacuna.
    public void delete(Long id) {

        Vacuna vacuna = findById(id);

        vacunaRepository.delete(vacuna);
    }
}