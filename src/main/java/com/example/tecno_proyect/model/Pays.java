package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pays")
public class Pays {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(name = "state", length = 50)
    private String state;
    
    @Column(name = "id_client")
    private Long idClient;
    
    @Column(name = "id_pay_plan")
    private Long idPayPlan;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_client", insertable = false, updatable = false)
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "id_pay_plan", insertable = false, updatable = false)
    private PayPlan payPlan;
    
    // Constructor vacío
    public Pays() {}
    
    // Constructor con parámetros
    public Pays(LocalDate date, BigDecimal total, String state, Long idClient, Long idPayPlan) {
        this.date = date;
        this.total = total;
        this.state = state;
        this.idClient = idClient;
        this.idPayPlan = idPayPlan;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Long getIdClient() {
        return idClient;
    }
    
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
    
    public Long getIdPayPlan() {
        return idPayPlan;
    }
    
    public void setIdPayPlan(Long idPayPlan) {
        this.idPayPlan = idPayPlan;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public PayPlan getPayPlan() {
        return payPlan;
    }
    
    public void setPayPlan(PayPlan payPlan) {
        this.payPlan = payPlan;
    }
    
    @Override
    public String toString() {
        return "Pays{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", state='" + state + '\'' +
                ", idClient='" + idClient + '\'' +
                ", idPayPlan=" + idPayPlan +
                '}';
    }
}