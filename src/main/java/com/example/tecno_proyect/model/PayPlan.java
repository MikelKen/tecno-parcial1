package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pay_plan")
public class PayPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pay_plan")
    private Long idPayPlan;
    
    @Column(name = "id_project", length = 100)
    private String idProject;
    
    @Column(name = "total_debt", precision = 12, scale = 2)
    private BigDecimal totalDebt;
    
    @Column(name = "total_payed", precision = 12, scale = 2)
    private BigDecimal totalPayed;
    
    @Column(name = "number_debt")
    private Integer numberDebt;
    
    @Column(name = "number_pays")
    private Integer numberPays;
    
    @Column(name = "state", length = 50)
    private String state;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_project", insertable = false, updatable = false)
    private Project project;
    
    @OneToMany(mappedBy = "payPlan", cascade = CascadeType.ALL)
    private List<Pays> pays;
    
    // Constructor vacío
    public PayPlan() {}
    
    // Constructor con parámetros
    public PayPlan(String idProject, BigDecimal totalDebt, BigDecimal totalPayed, Integer numberDebt, 
                  Integer numberPays, String state) {
        this.idProject = idProject;
        this.totalDebt = totalDebt;
        this.totalPayed = totalPayed;
        this.numberDebt = numberDebt;
        this.numberPays = numberPays;
        this.state = state;
    }
    
    // Getters y Setters
    public Long getIdPayPlan() {
        return idPayPlan;
    }
    
    public void setIdPayPlan(Long idPayPlan) {
        this.idPayPlan = idPayPlan;
    }
    
    public String getIdProject() {
        return idProject;
    }
    
    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }
    
    public BigDecimal getTotalDebt() {
        return totalDebt;
    }
    
    public void setTotalDebt(BigDecimal totalDebt) {
        this.totalDebt = totalDebt;
    }
    
    public BigDecimal getTotalPayed() {
        return totalPayed;
    }
    
    public void setTotalPayed(BigDecimal totalPayed) {
        this.totalPayed = totalPayed;
    }
    
    public Integer getNumberDebt() {
        return numberDebt;
    }
    
    public void setNumberDebt(Integer numberDebt) {
        this.numberDebt = numberDebt;
    }
    
    public Integer getNumberPays() {
        return numberPays;
    }
    
    public void setNumberPays(Integer numberPays) {
        this.numberPays = numberPays;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public List<Pays> getPays() {
        return pays;
    }
    
    public void setPays(List<Pays> pays) {
        this.pays = pays;
    }
    
    @Override
    public String toString() {
        return "PayPlan{" +
                "idPayPlan=" + idPayPlan +
                ", idProject='" + idProject + '\'' +
                ", totalDebt=" + totalDebt +
                ", totalPayed=" + totalPayed +
                ", numberDebt=" + numberDebt +
                ", numberPays=" + numberPays +
                ", state='" + state + '\'' +
                '}';
    }
}