package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cronograma")
public class Cronograma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_final")
    private LocalDate fechaFinal;
    
    @Column(name = "dias_estimados")
    private Integer diasEstimados;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    @Column(name = "id_proyecto")
    private Long idProyecto;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    private Proyecto proyecto;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "cronograma", cascade = CascadeType.ALL)
    private List<Tarea> tareas;
    
    // Constructor vacío
    public Cronograma() {}
    
    // Constructor con parámetros
    public Cronograma(LocalDate fechaInicio, LocalDate fechaFinal, Integer diasEstimados, 
                   String estado, Long idProyecto, Long usuarioId) {
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.diasEstimados = diasEstimados;
        this.estado = estado;
        this.idProyecto = idProyecto;
        this.usuarioId = usuarioId;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFinal() {
        return fechaFinal;
    }
    
    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    public Integer getDiasEstimados() {
        return diasEstimados;
    }
    
    public void setDiasEstimados(Integer diasEstimados) {
        this.diasEstimados = diasEstimados;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Long getIdProyecto() {
        return idProyecto;
    }
    
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Tarea> getTareas() {
        return tareas;
    }
    
    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinal=" + fechaFinal +
                ", diasEstimados=" + diasEstimados +
                ", estado='" + estado + '\'' +
                ", idProyecto=" + idProyecto +
                ", usuarioId=" + usuarioId +
                '}';
    }
}