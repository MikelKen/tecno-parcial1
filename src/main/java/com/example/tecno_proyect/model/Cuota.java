package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Cuota")
public class Cuota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuota")
    private Long idCuota;
    
    @Column(name = "tipo_metro", length = 50)
    private String tipoMetro;
    
    @Column(name = "costo_metro", precision = 10, scale = 2)
    private BigDecimal costoMetro;
    
    @Column(name = "cantidad_metro", precision = 10, scale = 2)
    private BigDecimal cantidadMetro;
    
    @Column(name = "costo_muebles", precision = 10, scale = 2)
    private BigDecimal costoMuebles;
    
    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    @Column(name = "numero_muebles")
    private Integer numeroMuebles;
    
    @Column(name = "comentarios", length = 500)
    private String comentarios;
    
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
    
    @OneToMany(mappedBy = "cuota", cascade = CascadeType.ALL)
    private List<Diseno> disenos;
    
    // Constructor vacío
    public Cuota() {}
    
    // Constructor con parámetros
    public Cuota(String tipoMetro, BigDecimal costoMetro, BigDecimal cantidadMetro, 
                BigDecimal costoMuebles, BigDecimal total, String estado, 
                Integer numeroMuebles, String comentarios, Long idProyecto, Long usuarioId) {
        this.tipoMetro = tipoMetro;
        this.costoMetro = costoMetro;
        this.cantidadMetro = cantidadMetro;
        this.costoMuebles = costoMuebles;
        this.total = total;
        this.estado = estado;
        this.numeroMuebles = numeroMuebles;
        this.comentarios = comentarios;
        this.idProyecto = idProyecto;
        this.usuarioId = usuarioId;
    }
    
    // Getters y Setters
    public Long getIdCuota() {
        return idCuota;
    }
    
    public void setIdCuota(Long idCuota) {
        this.idCuota = idCuota;
    }
    
    public String getTipoMetro() {
        return tipoMetro;
    }
    
    public void setTipoMetro(String tipoMetro) {
        this.tipoMetro = tipoMetro;
    }
    
    public BigDecimal getCostoMetro() {
        return costoMetro;
    }
    
    public void setCostoMetro(BigDecimal costoMetro) {
        this.costoMetro = costoMetro;
    }
    
    public BigDecimal getCantidadMetro() {
        return cantidadMetro;
    }
    
    public void setCantidadMetro(BigDecimal cantidadMetro) {
        this.cantidadMetro = cantidadMetro;
    }
    
    public BigDecimal getCostoMuebles() {
        return costoMuebles;
    }
    
    public void setCostoMuebles(BigDecimal costoMuebles) {
        this.costoMuebles = costoMuebles;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Integer getNumeroMuebles() {
        return numeroMuebles;
    }
    
    public void setNumeroMuebles(Integer numeroMuebles) {
        this.numeroMuebles = numeroMuebles;
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
    
    public List<Diseno> getDisenos() {
        return disenos;
    }
    
    public void setDisenos(List<Diseno> disenos) {
        this.disenos = disenos;
    }
    
    @Override
    public String toString() {
        return "Quote{" +
                "idCuota=" + idCuota +
                ", tipoMetro='" + tipoMetro + '\'' +
                ", costoMetro=" + costoMetro +
                ", cantidadMetro=" + cantidadMetro +
                ", costoMuebles=" + costoMuebles +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                ", numeroMuebles=" + numeroMuebles +
                ", comentarios='" + comentarios + '\'' +
                ", idProyecto='" + idProyecto + '\'' +
                ", usuarioId=" + usuarioId +
                '}';
    }
}