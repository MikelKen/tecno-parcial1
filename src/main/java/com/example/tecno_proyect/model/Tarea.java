package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "tarea")
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    
    @Column(name = "hora_final")
    private LocalTime horaFinal;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    @Column(name = "id_cronograma")
    private Long idCronograma;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_cronograma", insertable = false, updatable = false)
    private Cronograma cronograma;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    private Proyecto proyecto;
    
    // Constructor vacío
    public Tarea() {}
    
    // Constructor con parámetros
    public Tarea(LocalTime horaInicio, LocalTime horaFinal, String descripcion, 
               String estado, Long idCronograma, Long usuarioId) {
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idCronograma = idCronograma;
        this.usuarioId = usuarioId;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public LocalTime getHoraFinal() {
        return horaFinal;
    }
    
    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Long getIdCronograma() {
        return idCronograma;
    }
    
    public void setIdCronograma(Long idCronograma) {
        this.idCronograma = idCronograma;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public Cronograma getCronograma() {
        return cronograma;
    }
    
    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", horaInicio=" + horaInicio +
                ", horaFinal=" + horaFinal +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", idCronograma=" + idCronograma +
                ", usuarioId='" + usuarioId + '\'' +
                '}';
    }
}