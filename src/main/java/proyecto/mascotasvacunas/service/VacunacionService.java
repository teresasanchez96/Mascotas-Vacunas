package proyecto.mascotasvacunas.service;

import proyecto.mascotasvacunas.entity.Vacunacion;
import proyecto.mascotasvacunas.repository.VacunacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Esta clase contiene la lógica relacionada con las vacunaciones.
 */
@Service
public class VacunacionService {

    private final VacunacionRepository vacunacionRepository;

    public VacunacionService(VacunacionRepository vacunacionRepository) {
        this.vacunacionRepository = vacunacionRepository;
    }

    // Devuelve todas las vacunaciones.
    public List<Vacunacion> findAll() {
        return vacunacionRepository.findAll();
    }

    // Busca una vacunación por su ID.
    public Vacunacion findById(Long id) {
        return vacunacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vacunación no encontrada"));
    }

    // Guarda una vacunación.
    public Vacunacion save(Vacunacion vacunacion) {
        return vacunacionRepository.save(vacunacion);
    }

    // Actualiza una vacunación.
    public Vacunacion update(Long id, Vacunacion vacunacionDetails) {

        Vacunacion vacunacion = findById(id);

        vacunacion.setFechaPrevista(vacunacionDetails.getFechaPrevista());
        vacunacion.setPuesta(vacunacionDetails.getPuesta());
        vacunacion.setFechaAdministracion(
                vacunacionDetails.getFechaAdministracion()
        );
        vacunacion.setMascota(vacunacionDetails.getMascota());
        vacunacion.setVacuna(vacunacionDetails.getVacuna());

        return vacunacionRepository.save(vacunacion);
    }

    // Elimina una vacunación.
    public void delete(Long id) {

        Vacunacion vacunacion = findById(id);

        vacunacionRepository.delete(vacunacion);
    }
}