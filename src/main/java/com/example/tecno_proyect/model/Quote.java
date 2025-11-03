package com.example.tecno_proyect.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "quote")
public class Quote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quote")
    private Long idQuote;
    
    @Column(name = "type_metro", length = 50)
    private String typeMetro;
    
    @Column(name = "cost_metro", precision = 10, scale = 2)
    private BigDecimal costMetro;
    
    @Column(name = "quantity_metro", precision = 10, scale = 2)
    private BigDecimal quantityMetro;
    
    @Column(name = "cost_furniture", precision = 10, scale = 2)
    private BigDecimal costFurniture;
    
    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;
    
    @Column(name = "state", length = 50)
    private String state;
    
    @Column(name = "furniture_number")
    private Integer furnitureNumber;
    
    @Column(name = "comments", length = 500)
    private String comments;
    
    @Column(name = "id_project")
    private Long idProject;
    
    @Column(name = "user_id")
    private Long userId;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_project", insertable = false, updatable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL)
    private List<Design> designs;
    
    // Constructor vacío
    public Quote() {}
    
    // Constructor con parámetros
    public Quote(String typeMetro, BigDecimal costMetro, BigDecimal quantityMetro, 
                BigDecimal costFurniture, BigDecimal total, String state, 
                Integer furnitureNumber, String comments, Long idProject, Long userId) {
        this.typeMetro = typeMetro;
        this.costMetro = costMetro;
        this.quantityMetro = quantityMetro;
        this.costFurniture = costFurniture;
        this.total = total;
        this.state = state;
        this.furnitureNumber = furnitureNumber;
        this.comments = comments;
        this.idProject = idProject;
        this.userId = userId;
    }
    
    // Getters y Setters
    public Long getIdQuote() {
        return idQuote;
    }
    
    public void setIdQuote(Long idQuote) {
        this.idQuote = idQuote;
    }
    
    public String getTypeMetro() {
        return typeMetro;
    }
    
    public void setTypeMetro(String typeMetro) {
        this.typeMetro = typeMetro;
    }
    
    public BigDecimal getCostMetro() {
        return costMetro;
    }
    
    public void setCostMetro(BigDecimal costMetro) {
        this.costMetro = costMetro;
    }
    
    public BigDecimal getQuantityMetro() {
        return quantityMetro;
    }
    
    public void setQuantityMetro(BigDecimal quantityMetro) {
        this.quantityMetro = quantityMetro;
    }
    
    public BigDecimal getCostFurniture() {
        return costFurniture;
    }
    
    public void setCostFurniture(BigDecimal costFurniture) {
        this.costFurniture = costFurniture;
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
    
    public Integer getFurnitureNumber() {
        return furnitureNumber;
    }
    
    public void setFurnitureNumber(Integer furnitureNumber) {
        this.furnitureNumber = furnitureNumber;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Long getIdProject() {
        return idProject;
    }
    
    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<Design> getDesigns() {
        return designs;
    }
    
    public void setDesigns(List<Design> designs) {
        this.designs = designs;
    }
    
    @Override
    public String toString() {
        return "Quote{" +
                "idQuote=" + idQuote +
                ", typeMetro='" + typeMetro + '\'' +
                ", costMetro=" + costMetro +
                ", quantityMetro=" + quantityMetro +
                ", costFurniture=" + costFurniture +
                ", total=" + total +
                ", state='" + state + '\'' +
                ", furnitureNumber=" + furnitureNumber +
                ", comments='" + comments + '\'' +
                ", idProject='" + idProject + '\'' +
                ", userId=" + userId +
                '}';
    }
}