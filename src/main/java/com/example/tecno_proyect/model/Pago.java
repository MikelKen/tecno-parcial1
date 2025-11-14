package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pago")
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fecha")
    private LocalDate fecha;
    
    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    @Column(name = "id_cliente")
    private Long idCliente;
    
    @Column(name = "id_plan_pago")
    private Long idPlanPago;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_plan_pago", insertable = false, updatable = false)
    private PlanPago planPago;
    
    // Constructor vacío
    public Pago() {}
    
    // Constructor con parámetros
    public Pago(LocalDate fecha, BigDecimal total, String estado, Long idCliente, Long idPlanPago) {
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.idCliente = idCliente;
        this.idPlanPago = idPlanPago;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
    
    public Long getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    
    public Long getIdPlanPago() {
        return idPlanPago;
    }
    
    public void setIdPlanPago(Long idPlanPago) {
        this.idPlanPago = idPlanPago;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public PlanPago getPlanPago() {
        return planPago;
    }
    
    public void setPlanPago(PlanPago planPago) {
        this.planPago = planPago;
    }
    
    @Override
    public String toString() {
        return "Pays{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", idPlanPago=" + idPlanPago +
                '}';
    }
}