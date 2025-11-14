package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "plan_pago")
public class PlanPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan_pago")
    private Long idPlanPago;
    
    @Column(name = "id_proyecto")
    private Long idProyecto;
    
    @Column(name = "total_deuda", precision = 12, scale = 2)
    private BigDecimal totalDeuda;
    
    @Column(name = "total_pagado", precision = 12, scale = 2)
    private BigDecimal totalPagado;
    
    @Column(name = "numero_deuda")
    private Integer numeroDeuda;
    
    @Column(name = "numero_pagos")
    private Integer numeroPagos;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    private Proyecto proyecto;
    
    @OneToMany(mappedBy = "planPago", cascade = CascadeType.ALL)
    private List<Pago> pagos;
    
    // Constructor vacío
    public PlanPago() {}
    
    // Constructor con parámetros
    public PlanPago(Long idProyecto, BigDecimal totalDeuda, BigDecimal totalPagado, Integer numeroDeuda, 
                  Integer numeroPagos, String estado) {
        this.idProyecto = idProyecto;
        this.totalDeuda = totalDeuda;
        this.totalPagado = totalPagado;
        this.numeroDeuda = numeroDeuda;
        this.numeroPagos = numeroPagos;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Long getIdPlanPago() {
        return idPlanPago;
    }
    
    public void setIdPlanPago(Long idPlanPago) {
        this.idPlanPago = idPlanPago;
    }
    
    public Long getIdProyecto() {
        return idProyecto;
    }
    
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
    
    public BigDecimal getTotalDeuda() {
        return totalDeuda;
    }
    
    public void setTotalDeuda(BigDecimal totalDeuda) {
        this.totalDeuda = totalDeuda;
    }
    
    public BigDecimal getTotalPagado() {
        return totalPagado;
    }
    
    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }
    
    public Integer getNumeroDeuda() {
        return numeroDeuda;
    }
    
    public void setNumeroDeuda(Integer numeroDeuda) {
        this.numeroDeuda = numeroDeuda;
    }
    
    public Integer getNumeroPagos() {
        return numeroPagos;
    }
    
    public void setNumeroPagos(Integer numeroPagos) {
        this.numeroPagos = numeroPagos;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public List<Pago> getPagos() {
        return pagos;
    }
    
    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
    
    @Override
    public String toString() {
        return "PlanPago{" +
                "idPlanPago=" + idPlanPago +
                ", idProyecto='" + idProyecto + '\'' +
                ", totalDeuda=" + totalDeuda +
                ", totalPagado=" + totalPagado +
                ", numeroDeuda=" + numeroDeuda +
                ", numeroPagos=" + numeroPagos +
                ", estado='" + estado + '\'' +
                '}';
    }
}