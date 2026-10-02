package proyecto.mascotasvacunas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

/*
 * Esta clase representa una mascota de la aplicación.
 * Contiene sus datos y la relación con su usuario
 */
@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private String genero;

    @Column
    private String raza;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    public Mascota() {
    }

    public Mascota(String nombre, String especie, String genero, String raza,
                   LocalDate fechaNacimiento, String observaciones, Usuario usuario) {
        this.nombre = nombre;
        this.especie = especie;
        this.genero = genero;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.observaciones = observaciones;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", genero='" + genero + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}