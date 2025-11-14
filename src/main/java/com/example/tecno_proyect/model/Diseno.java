package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "diseno")
public class Diseno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diseno")
    private Long idDiseno;
    
    @Column(name = "id_cuota")
    private Long idCuota;
    
    @Column(name = "url_render", length = 500)
    private String urlRender;
    
    @Column(name = "plano_laminar", length = 500)
    private String  planoLaminar;// laminatedPlane;
    
    @Column(name = "aprobado")
    private Boolean aprobado;
    
    @Column(name = "fecha_aprobacion")
    private LocalDate fechaAprobacion;
    
    @Column(name = "commentarios", length = 500)
    private String comentarios;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_cuota", insertable = false, updatable = false)
    private Cuota cuota;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;
    
    // Constructor vacío
    public Diseno() {}
    
    // Constructor con parámetros
    public Diseno(Long idCuota, String urlRender, String planoLaminar, Boolean aprobado, 
                 LocalDate fechaAprobacion, String comentarios, Long usuarioId) {
        this.idCuota = idCuota;
        this.urlRender = urlRender;
        this.planoLaminar = planoLaminar;
        this.aprobado = aprobado;
        this.fechaAprobacion = fechaAprobacion;
        this.comentarios = comentarios;
        this.usuarioId = usuarioId;
    }
    
    // Getters y Setters
    public Long getIdDiseno() {
        return idDiseno;
    }
    
    public void setIdDiseno(Long idDiseno) {
        this.idDiseno = idDiseno;
    }
    
    public Long getIdCuota() {
        return idCuota;
    }
    
    public void setIdCuota(Long idCuota) {
        this.idCuota = idCuota;
    }
    
    public String getUrlRender() {
        return urlRender;
    }
    
    public void setUrlRender(String urlRender) {
        this.urlRender = urlRender;
    }
    
    public String getPlanoLaminar() {
        return planoLaminar;
    }
    
    public void setPlanoLaminar(String planoLaminar) {
        this.planoLaminar = planoLaminar;
    }
    
    public Boolean getAprobado() {
        return aprobado;
    }
    
    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }
    
    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }
    
    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }
    
    public String getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public Cuota getCuota() {
        return cuota;
    }
    
    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() {
        return "Design{" +
                "idDesign=" + idDiseno +
                ", idQuote=" + idCuota +
                ", urlRender='" + urlRender + '\'' +
                ", laminatedPlane='" + planoLaminar + '\'' +
                ", approved=" + aprobado +
                ", approvedDate=" + fechaAprobacion +
                ", comments='" + comentarios + '\'' +
                ", userId='" + usuarioId + '\'' +
                '}';
    }
}