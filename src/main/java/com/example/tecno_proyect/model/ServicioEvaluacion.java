package com.example.tecno_proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servicio_evaluacion")
public class ServicioEvaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "comentarios", length = 500)
    private String comentarios;
    
    @Column(name = "id_proyecto")
    private Long idProyecto;
    
    @Column(name = "calificacion_diseno")
    private Integer calificacionDiseno;
    
    @Column(name = "calificacion_fabricacion")
    private Integer calificacionFabricacion;
    
    @Column(name = "calificacion_instalacion")
    private Integer calificacionInstalacion;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    private Proyecto proyecto;
    
    // Constructor vacío
    public ServicioEvaluacion() {}
    
    // Constructor con parámetros
    public ServicioEvaluacion(String comentarios, Long idProyecto, Integer calificacionDiseno, 
                           Integer calificacionFabricacion, Integer calificacionInstalacion) {
        this.comentarios = comentarios;
        this.idProyecto = idProyecto;
        this.calificacionDiseno = calificacionDiseno;
        this.calificacionFabricacion = calificacionFabricacion;
        this.calificacionInstalacion = calificacionInstalacion;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public Long getIdProyecto() {
        return idProyecto;
    }
    
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
    
    public Integer getCalificacionDiseno() {
        return calificacionDiseno;
    }
    
    public void setCalificacionDiseno(Integer calificacionDiseno) {
        this.calificacionDiseno = calificacionDiseno;
    }
    
    public Integer getCalificacionFabricacion() {
        return calificacionFabricacion;
    }
    
    public void setCalificacionFabricacion(Integer calificacionFabricacion) {
        this.calificacionFabricacion = calificacionFabricacion;
    }
    
    public Integer getCalificacionInstalacion() {
        return calificacionInstalacion;
    }
    
    public void setCalificacionInstalacion(Integer calificacionInstalacion) {
        this.calificacionInstalacion = calificacionInstalacion;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    @Override
    public String toString() {
        return "ServiceEvaluation{" +
                "id=" + id +
                ", comments='" + comentarios + '\'' +
                ", idProject='" + idProyecto + '\'' +
                ", designQualification=" + calificacionDiseno +
                ", fabricQualification=" + calificacionFabricacion +
                ", installationQualification=" + calificacionInstalacion +
                '}';
    }
}