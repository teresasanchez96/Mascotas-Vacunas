package proyecto.mascotasvacunas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

/*
 * Esta clase representa una vacunación realizada o prevista para una mascota.
 * Contiene la información de la vacunación y su relación con la mascota y la vacuna.
 */
@Entity
@Table(name = "vacunaciones")
public class Vacunacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fechaPrevista;

    @Column(nullable = false)
    private Boolean vacunaAdministrada;

    @Column
    private LocalDate fechaAdministracion;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    @JsonIgnore
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "vacuna_id", nullable = false)
    @JsonIgnore
    private Vacuna vacuna;

    public Vacunacion() {
    }

    public Vacunacion(LocalDate fechaPrevista, Boolean vacunaAdministrada,
                      LocalDate fechaAdministracion, Mascota mascota,
                      Vacuna vacuna) {
        this.fechaPrevista = fechaPrevista;
        this.vacunaAdministrada = vacunaAdministrada;
        this.fechaAdministracion = fechaAdministracion;
        this.mascota = mascota;
        this.vacuna = vacuna;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPrevista() {
        return fechaPrevista;
    }

    public void setFechaPrevista(LocalDate fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    public Boolean getVacunaAdministrada() {
        return vacunaAdministrada;
    }

    public void setVacunaAdministrada(Boolean vacunaAdministrada) {
        this.vacunaAdministrada = vacunaAdministrada;
    }

    public LocalDate getFechaAdministracion() {
        return fechaAdministracion;
    }

    public void setFechaAdministracion(LocalDate fechaAdministracion) {
        this.fechaAdministracion = fechaAdministracion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }

    @Override
    public String toString() {
        return "Vacunacion{" +
                "id=" + id +
                ", fechaPrevista=" + fechaPrevista +
                ", vacunaAdministrada=" + vacunaAdministrada +
                ", fechaAdministracion=" + fechaAdministracion +
                '}';
    }
}